package com.w2.springtemplate.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.w2.springtemplate.framework.shiro.bcrypt.BCryptPasswordMatcher;
import com.w2.springtemplate.framework.shiro.bcrypt.BCryptPasswordService;
import com.w2.springtemplate.framework.shiro.cache.RedisCacheManager;
import com.w2.springtemplate.framework.shiro.realm.UserAccountRealm;
import com.w2.springtemplate.framework.shiro.session.NoSessionWebSubjectFactory;
import com.w2.springtemplate.utils.crypto.BCryptPasswordEncoder;
import com.w2.springtemplate.utils.crypto.PasswordEncoder;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

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

    ///关闭shiro自带session
    @Bean
    SessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    ///关闭shiro自带session
    @Bean
    SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    ///关闭shiro自带session
    @Bean
    SubjectDAO defaultSubjectDAO() {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return defaultSubjectDAO;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/api/doc.html", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");             // druid登录交给druid自己
        filterChainDefinitionMap.put("/**", "anon");
        //authc表示需要验证身份才能访问，还有一些比如anon表示不需要验证身份就能访问等。
        shiroFilterFactoryBean.setLoginUrl("/api/login");
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public Realm userAccountRealm() {
        UserAccountRealm userAccountRealm = new UserAccountRealm();
        userAccountRealm.setCredentialsMatcher(passwordMatcher());
        userAccountRealm.setAuthenticationCachingEnabled(true);
        return userAccountRealm;
    }


    @Bean
    public DefaultWebSecurityManager shiroSecurityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setSessionManager(sessionManager());
        manager.setSubjectDAO(defaultSubjectDAO());
        manager.setCacheManager(redisCacheManager());
        // 加入AccountRealm
        manager.setRealms(Lists.newArrayList(userAccountRealm()));
        return manager;
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

}
