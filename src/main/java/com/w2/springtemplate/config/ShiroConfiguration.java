package com.w2.springtemplate.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.w2.springtemplate.framework.shiro.authc.MultipleRealmAuthentic;
import com.w2.springtemplate.framework.shiro.bcrypt.BCryptPasswordMatcher;
import com.w2.springtemplate.framework.shiro.bcrypt.BCryptPasswordService;
import com.w2.springtemplate.framework.shiro.cache.RedisCacheManager;
import com.w2.springtemplate.framework.shiro.filter.UserAccountLoginFilter;
import com.w2.springtemplate.framework.shiro.jwt.BearerAuthenticFilter;
import com.w2.springtemplate.framework.shiro.realm.BearerRealm;
import com.w2.springtemplate.framework.shiro.realm.UserAccountRealm;
import com.w2.springtemplate.framework.shiro.session.NoSessionWebSubjectFactory;
import com.w2.springtemplate.utils.crypto.BCryptPasswordEncoder;
import com.w2.springtemplate.utils.crypto.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.config.ShiroFilterConfiguration;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.LinkedHashMap;

@Slf4j
@Configuration
public class ShiroConfiguration {

    @Bean
    LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /// 密码加密设置
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordService passwordService() {
        return new BCryptPasswordService(passwordEncoder());
    }

    @Bean
    public CredentialsMatcher passwordMatcher() {
        return new BCryptPasswordMatcher(passwordService());
    }

    @Bean
    CacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    /// 关闭shiro自带session
    @Bean
    SessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    /// 关闭shiro自带session
    @Bean
    SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /// 关闭shiro自带session
    @Bean
    SubjectDAO defaultSubjectDAO() {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return defaultSubjectDAO;
    }

    @Bean
    public FilterRegistrationBean<Filter> shiroFilterRegistrationBean() {
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilterFactoryBean"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);

        return filterRegistration;
    }

    /// 自定义过滤器
    @Bean
    LinkedHashMap<String, Filter> filters() {
        LinkedHashMap<String, Filter> filters = Maps.newLinkedHashMap();
        filters.put("jwt", new BearerAuthenticFilter());
        filters.put("authc", new UserAccountLoginFilter());
        return filters;
    }

    /// 路径匹配规则
    @Bean
    LinkedHashMap<String, String> filterChainDefinitionMap() {
        LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        filterChainDefinitionMap.put("/api/w2/login", "authc");
        filterChainDefinitionMap.put("/api/doc.html", "anon");
        filterChainDefinitionMap.put("/api/**", "jwt");
//        filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/**", "anon");
        return filterChainDefinitionMap;
    }

    @Bean
    public Realm userAccountRealm() {
        UserAccountRealm userAccountRealm = new UserAccountRealm();
        userAccountRealm.setCredentialsMatcher(passwordMatcher());
        userAccountRealm.setAuthenticationTokenClass(UsernamePasswordToken.class);
        // userAccountRealm.setAuthenticationCachingEnabled(true);
        return userAccountRealm;
    }

    @Bean
    public Realm bearerRealm() {
        BearerRealm bearerRealm = new BearerRealm();
        bearerRealm.setAuthenticationTokenClass(BearerToken.class);
        return bearerRealm;
    }

    @Bean
    Authenticator multipleRealmAuthentic(Realm userAccountRealm, Realm bearerRealm) {
        MultipleRealmAuthentic multipleRealmAuthentic = new MultipleRealmAuthentic();
        multipleRealmAuthentic.setRealms(Lists.newArrayList(userAccountRealm, bearerRealm));
        return multipleRealmAuthentic;
    }


    @Bean
    public DefaultWebSecurityManager shiroSecurityManager(Realm userAccountRealm, Realm bearerRealm, Authenticator multipleRealmAuthentic) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(redisCacheManager());
        // manager.setSessionManager(sessionManager());
        // manager.setSubjectDAO(defaultSubjectDAO());
        manager.setSubjectFactory(new NoSessionWebSubjectFactory()); // 使用无状态的工厂
        manager.setAuthenticator(multipleRealmAuthentic);
        // 默认SubjectDAO会写入会话，无状态时需要通过以下代码阻止写入
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) manager.getSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO
                .getSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);

        // 加入AccountRealm
        // manager.setRealms(Lists.newArrayList(userAccountRealm()));
        // 加入AccountRealm
        manager.setRealms(Lists.newArrayList(userAccountRealm));
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,
                                                         LinkedHashMap<String, Filter> filters, LinkedHashMap<String, String> filterChainDefinitionMap) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        ShiroFilterConfiguration config = shiroFilterFactoryBean.getShiroFilterConfiguration();
        config.setFilterOncePerRequest(Boolean.TRUE);
//
        shiroFilterFactoryBean.setLoginUrl("api/login");

        shiroFilterFactoryBean.setFilters(filters);
        LinkedHashMap<String, Filter> shiroFilters = (LinkedHashMap<String, Filter>) shiroFilterFactoryBean
                .getFilters();
        log.info("shiroFilters--:{}", shiroFilters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("拦截地址:{}", shiroFilterFactoryBean.getFilterChainDefinitionMap());
        // 前后端分离模式下不需要
        shiroFilterFactoryBean.setSuccessUrl(null);
        shiroFilterFactoryBean.setUnauthorizedUrl(null);
        return shiroFilterFactoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor shiroAuthorizationAttributeSourceAdvisor(
            @Autowired SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

        return authorizationAttributeSourceAdvisor;
    }

}
