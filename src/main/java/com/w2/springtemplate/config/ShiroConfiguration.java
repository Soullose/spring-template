package com.w2.springtemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.w2.springtemplate.utils.crypto.BCryptPasswordEncoder;
import com.w2.springtemplate.utils.crypto.PasswordEncoder;

@Configuration
public class ShiroConfiguration {
    /// 密码加密设置
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
