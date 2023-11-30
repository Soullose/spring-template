package com.w2.springtemplate.framework.vfs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ProtocolResolver;

@Configuration
public class ApacheVfsConfiguration {

	@Bean
	public ApacheVfsInitializer apacheVfsInitializer() {
		return new ApacheVfsInitializer();
	}

	@Bean
	@DependsOn(value = "apacheVfsInitializer")
	public ProtocolResolver apacheVfsProtocolResolver(ApacheVfsInitializer apacheVfsInitializer) {
		return apacheVfsInitializer.newProtocolResolver();
	}
}
