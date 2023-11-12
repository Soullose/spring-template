package com.w2.springtemplate.config;

import com.google.common.collect.Lists;
import com.w2.springtemplate.framework.shiro.bcrypt.BCryptPasswordMatcher;
import com.w2.springtemplate.framework.shiro.bcrypt.BCryptPasswordService;
import com.w2.springtemplate.framework.shiro.realm.UserAccountRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
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
        return userAccountRealm;
    }

    @Bean
    public DefaultWebSecurityManager shiroSecurityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setCacheManager(shiroCacheManager());
//        manager.setSessionManager(shiroSessionManager());
        // 加入AccountRealm
        manager.setRealms(Lists.newArrayList(userAccountRealm()));
        return manager;
    }


//	DefaultPasswordService


//    HashedCredentialsMatcher
}
