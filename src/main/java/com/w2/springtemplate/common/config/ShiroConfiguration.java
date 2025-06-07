package com.w2.springtemplate.common.config;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.config.ShiroFilterConfiguration;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.w2.springtemplate.framework.shiro.authc.MultipleRealmAuthentic;
import com.w2.springtemplate.framework.shiro.extention.RetryPasswordCredentialsMatcher;
import com.w2.springtemplate.framework.shiro.filter.BearerAuthenticFilter;
import com.w2.springtemplate.framework.shiro.filter.UserAccountLoginFilter;
import com.w2.springtemplate.framework.shiro.realm.BearerRealm;
import com.w2.springtemplate.framework.shiro.realm.UserAccountRealm;
import com.w2.springtemplate.framework.shiro.session.NoSessionWebSubjectFactory;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;

@Configuration
public class ShiroConfiguration {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 管理shiro的生命周期
	 */
	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public PasswordService defaultPasswordService() {
		return new DefaultPasswordService();
	}

	@Bean
	public CredentialsMatcher passwordMatcher() {
		return new RetryPasswordCredentialsMatcher();
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
	FilterRegistrationBean<Filter> shiroFilterRegistrationBean() {
		FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<>();
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
		filters.put("login", new UserAccountLoginFilter());
		return filters;
	}

	/// 路径匹配规则
	@Bean
	LinkedHashMap<String, String> filterChainDefinitionMap() {
		LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
		filterChainDefinitionMap.put("/api/login", "login");
		filterChainDefinitionMap.put("/doc.html", "anon");
		filterChainDefinitionMap.put("/user/register", "anon");
		filterChainDefinitionMap.put("/api/**", "jwt");
		filterChainDefinitionMap.put("/test/**", "anon");
		filterChainDefinitionMap.put("/testEvent/**", "anon");
		filterChainDefinitionMap.put("/bim-server/**", "anon");
		filterChainDefinitionMap.put("/**", "anon");
		return filterChainDefinitionMap;
	}

	@Bean
	UserAccountRealm userAccountRealm() {
		UserAccountRealm userAccountRealm = new UserAccountRealm();
		userAccountRealm.setCredentialsMatcher(passwordMatcher());
		userAccountRealm.setAuthenticationTokenClass(UsernamePasswordToken.class);
		return userAccountRealm;
	}

	@Bean
	BearerRealm bearerRealm() {
		BearerRealm bearerRealm = new BearerRealm();
		bearerRealm.setAuthenticationTokenClass(BearerToken.class);
		return bearerRealm;
	}

	@Bean
	Authenticator multipleRealmAuthentic(BearerRealm bearerRealm,
			UserAccountRealm userAccountRealm) {
		MultipleRealmAuthentic multipleRealmAuthentic = new MultipleRealmAuthentic();
		multipleRealmAuthentic.setRealms(Arrays.asList(bearerRealm, userAccountRealm));
		multipleRealmAuthentic.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		return multipleRealmAuthentic;
	}

	@Bean
	public DefaultWebSecurityManager shiroSecurityManager(UserAccountRealm userAccountRealm, BearerRealm bearerRealm,
			Authenticator multipleRealmAuthentic) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setSubjectFactory(new NoSessionWebSubjectFactory()); // 使用无状态的工厂
		/// 默认SubjectDAO会写入会话，无状态时需要通过以下代码阻止写入
		DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) manager.getSubjectDAO();
		DefaultSessionStorageEvaluator sessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO
				.getSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(false);

		/// 加入AccountRealm
		manager.setRealms(Lists.newArrayList(userAccountRealm, bearerRealm));
		manager.setAuthenticator(multipleRealmAuthentic);
		return manager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,
			LinkedHashMap<String, Filter> filters, LinkedHashMap<String, String> filterChainDefinitionMap) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		///
		shiroFilterFactoryBean.setLoginUrl("/api/login");

		ShiroFilterConfiguration config = shiroFilterFactoryBean.getShiroFilterConfiguration();
		config.setFilterOncePerRequest(Boolean.TRUE);

		shiroFilterFactoryBean.setFilters(filters);
		LinkedHashMap<String, Filter> shiroFilters = (LinkedHashMap<String, Filter>) shiroFilterFactoryBean
				.getFilters();
		log.info("shiroFilters--:{}", shiroFilters);
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		log.info("拦截地址:{}", shiroFilterFactoryBean.getFilterChainDefinitionMap());
		/// 前后端分离模式下不需要
		shiroFilterFactoryBean.setSuccessUrl(null);
		shiroFilterFactoryBean.setUnauthorizedUrl(null);
		return shiroFilterFactoryBean;
	}

	/**
	 * 下面的代码是添加注解支持
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		/// 强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * 加入shiro注解 切点
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	AuthorizationAttributeSourceAdvisor shiroAuthorizationAttributeSourceAdvisor(
			@Autowired SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

		return authorizationAttributeSourceAdvisor;
	}

}
