package com.w2.springtemplate.framework.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.w2.springtemplate.framework.shiro.cache.RedisCache;
import com.w2.springtemplate.utils.crypto.RedisUtils;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
// @Order(value = 1)
@Configuration
@EnableCaching
@EnableConfigurationProperties({ RedisProperties.class })
public class RedisConfiguration extends CachingConfigurerSupport {

	private final RedisProperties redisProperties;

	RedisConfiguration(RedisProperties redisProperties) {
		this.redisProperties = redisProperties;
	}

	@Bean
	LettuceConnectionFactory redisConnectionFactory() {
		log.info("redisProperties:{}", redisProperties);
		GenericObjectPoolConfig<StatefulRedisConnection<String, String>> genericObjectPoolConfig = new GenericObjectPoolConfig<StatefulRedisConnection<String, String>>();
		genericObjectPoolConfig.setMaxIdle(8);
		genericObjectPoolConfig.setMinIdle(2);
		genericObjectPoolConfig.setMaxTotal(10);
		// genericObjectPoolConfig.setMaxWait(Duration.ofMillis(300));
		// genericObjectPoolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(100));
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisProperties.getHost());
		redisStandaloneConfiguration.setPort(redisProperties.getPort());
		redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
		redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());

		LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
				// .commandTimeout(Duration.ofMillis(100)).shutdownTimeout(Duration.ofMillis(100))
				.poolConfig(genericObjectPoolConfig).build();

		return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
		ObjectMapper om = new ObjectMapper();
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.WRAPPER_ARRAY);
		om.registerModule(new JavaTimeModule());
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        om.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		RedisUtils.setRedisTemplate(redisTemplate);
        RedisCache.setRedisTemplate(redisTemplate);
		return redisTemplate;
	}

//	@Bean()
//	public RedisCacheManager redisCacheManager(LettuceConnectionFactory redisConnectionFactory) {
//
//		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(
//				RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8))
//				.serializeValuesWith(
//						RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
//				.disableCachingNullValues().entryTtl(Duration.ofMinutes(60));
//		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
//				.cacheDefaults(defaultCacheConfig).transactionAware().build();
//	}

	@Bean
	@ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
	@Scope(value = "prototype")
	public GenericObjectPoolConfig<StatefulRedisConnection<String, String>> redisPool() {
		return new GenericObjectPoolConfig<StatefulRedisConnection<String, String>>();
	}

	private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.WRAPPER_ARRAY);
		objectMapper.registerModule(new JavaTimeModule());
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}
}
