package com.w2.springtemplate.framework.workflow.config.processor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "w2.flowable.processor")
public class FlowableProcessor {
    private boolean enabled;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
