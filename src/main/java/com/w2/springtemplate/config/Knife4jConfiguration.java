package com.w2.springtemplate.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.lang.reflect.Field;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "knife4jConfigurationBean")
    public Docket knife4jConfigurationBean() {
        //指定使用Swagger2规范
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //描述字段支持Markdown语法
                        .title("接口文档")
                        .description("# spring-template RESTful APIs")
                        .termsOfServiceUrl("https://w2.api.cn/")
                        .contact(new Contact("w2", "", "xxx@xxx.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("API服务")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.w2.springtemplate.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //    @Bean(value = "knife4jDDDConfigurationBean")
//    public Docket knife4jDDDConfigurationBean() {
//        //指定使用Swagger2规范
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(new ApiInfoBuilder()
//                        //描述字段支持Markdown语法
//                        .title("接口文档")
//                        .description("# spring-template RESTful APIs")
//                        .termsOfServiceUrl("https://w2.api.cn/")
//                        .contact(new Contact("w2", "", "xxx@xxx.com"))
//                        .version("1.0")
//                        .build())
//                //分组名称
//                .groupName("DDD服务")
//                .select()
//                //这里指定Controller扫描包路径
//                .apis(RequestHandlerSelectors.basePackage("com.w2.springtemplate.interfaces.user.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                mappings.removeIf(mapping -> mapping.getPatternParser() != null);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

}