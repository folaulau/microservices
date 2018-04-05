package com.folaukaveinga.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

	// Let spring configure connection factory using the configuration in configure
	// file.
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	/*
	 @Bean public JedisConnectionFactory jedisConnectionFactory() {
	 JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
	 jedisConFactory.setHostName("localhost"); jedisConFactory.setPort(6379);
	 return jedisConFactory; }
	 */

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}
}