package com.w2.springtemplate.framework.workflow.config;

import java.util.List;

import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.google.common.collect.Lists;
import com.w2.springtemplate.framework.workflow.config.processor.FlowableProcessor;

import lombok.extern.slf4j.Slf4j;

// @Order(value = Ordered.LOWEST_PRECEDENCE)
@Slf4j
@AutoConfigureBefore(LiquibaseAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
// @ConfigurationProperties(prefix = "w2.flowable.processor")
public class FlowableConfiguration implements InitializingBean {

	private final FlowableProcessor flowableProcessor;

	public FlowableConfiguration(FlowableProcessor flowableProcessor) {
		this.flowableProcessor = flowableProcessor;
	}

	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	@Bean
	@ConditionalOnProperty(prefix = "w2.flowable.processor", name = "enabled", havingValue = "true", matchIfMissing = false)
	public ProcessEngine processEngine() {
		List<FlowableEventListener> eventListeners = Lists.newArrayList();
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		processEngineConfiguration.setJdbcDriver(flowableProcessor.getDriverClassName());
		processEngineConfiguration.setJdbcUrl(flowableProcessor.getUrl())
				.setJdbcUsername(flowableProcessor.getUsername());
		processEngineConfiguration.setJdbcPassword(flowableProcessor.getPassword());
		processEngineConfiguration.setDatabaseType(ProcessEngineConfiguration.DATABASE_TYPE_MYSQL);
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		processEngineConfiguration.setEventListeners(eventListeners);
		return processEngineConfiguration.buildProcessEngine();
		// return
		// ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
		// .setJdbcDriver(flowableProcessor.getDriverClassName())
		// .setJdbcUrl(flowableProcessor.getUrl()).setJdbcUsername(flowableProcessor.getUsername())
		// .setJdbcPassword(flowableProcessor.getPassword())
		// .setDatabaseType(ProcessEngineConfiguration.DATABASE_TYPE_MYSQL)
		// .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
		// .buildProcessEngine();
	}

	/**
	 * 部署流程定义
	 * 
	 * @param processEngine
	 *            流程引擎
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "w2.flowable.processor", name = "enabled", havingValue = "true", matchIfMissing = false)
	public RepositoryService repositoryService(ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	/**
	 * 启动流程实例
	 * 
	 * @param processEngine
	 *            流程引擎
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "w2.flowable.processor", name = "enabled", havingValue = "true", matchIfMissing = false)
	public RuntimeService runtimeService(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	/**
	 * 流程任务
	 * 
	 * @param processEngine
	 *            流程引擎
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "w2.flowable.processor", name = "enabled", havingValue = "true", matchIfMissing = false)
	public TaskService taskService(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	/**
	 * 历史信息
	 * 
	 * @param processEngine
	 *            流程引擎
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "w2.flowable.processor", name = "enabled", havingValue = "true", matchIfMissing = false)
	public HistoryService historyService(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}

//	@Bean
	@ConditionalOnProperty(prefix = "w2.flowable.processor", name = "enabled", havingValue = "true", matchIfMissing = false)
	public void afterPropertiesSet() {
		log.debug("flowableProcessor:{}", flowableProcessor);
	}

}
