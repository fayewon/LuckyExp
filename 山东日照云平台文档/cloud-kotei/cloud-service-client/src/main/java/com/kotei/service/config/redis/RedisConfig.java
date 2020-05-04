package com.kotei.service.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import redis.clients.jedis.JedisPoolConfig;

import org.springframework.cache.interceptor.KeyGenerator;
import java.lang.reflect.Method;
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	/**
	   * redis配置属性读取
	   */
	  @Value("${redis.host}")
	  private String host;
	  @Value("${redis.port}")
	  private int port;
	  @Value("${redis.database}")
	  private int database;
	  @Value("${redis.jedis.pool.max-idle}")
	  private int maxIdle;
	  @Value("${redis.jedis.pool.max-wait}")
	  private long maxWaitMillis;
	  @Value("${redis.jedis.pool.max-active}")
	  private int maxActive;
	  /**
	   * JedisPoolConfig配置
	   * @return
	   */
	  @Bean
	  public JedisPoolConfig jedisPoolConfig() {
		//log.info("初始化JedisPoolConfig");
	    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	    jedisPoolConfig.setMaxTotal(maxActive);
	    jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
	    jedisPoolConfig.setMaxIdle(maxIdle);
	    return jedisPoolConfig;
	  }
	  /**
	   * 注入RedisConnectionFactory
	   * @return
	   */
	  @Bean
	  public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
	    //log.info("初始化JedisConnectionFactory");
	    // JedisConnectionFactory配置hsot、database、password等参数
	    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
	    redisStandaloneConfiguration.setHostName(host);
	    redisStandaloneConfiguration.setPort(port);
	    redisStandaloneConfiguration.setDatabase(database);
	    // JedisConnectionFactory配置jedisPoolConfig
	    JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolConfigBuilder =
	        (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
	    jedisPoolConfigBuilder.poolConfig(jedisPoolConfig);
	    return new JedisConnectionFactory(redisStandaloneConfiguration);
	 
	  }
	  /**
	   * 采用RedisCacheManager作为缓存管理器
	   * @param connectionFactory
	   */
	  @Bean
	  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
	    RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
	    return redisCacheManager;
	  }
	  /**
	   * 生成key的策略:根据类名+方法名+所有参数的值生成唯一的一个key
	   * @return
	   */
	  @Bean
	  @Override
	  public KeyGenerator keyGenerator() {
	    return (Object target, Method method, Object... params) -> {
	      StringBuilder sb = new StringBuilder();
	      sb.append(target.getClass().getName());
	      sb.append(method.getName());
	      for (Object obj : params) {
	        sb.append(obj.toString());
	      }
	      return sb.toString();
	    };
	  }
	  /**
	   * 重新实现RedisTemplate：解决序列化问题
	   * @param redisConnectionFactory
	   * @return
	   */
	  @Bean
	  @SuppressWarnings({"rawtype", "unchecked"})
	  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
	    RedisTemplate<String, Object> template = new RedisTemplate();
	    template.setConnectionFactory(redisConnectionFactory);
	    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
	    ObjectMapper om = new ObjectMapper();
	    // 设置任何字段可见
	    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	    // 设置不是final的属性可以转换
	    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	    //log.info("objectMapper: {}", om);
	    jackson2JsonRedisSerializer.setObjectMapper(om);
	    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	    // key采用String的序列化方式
	    template.setKeySerializer(stringRedisSerializer);
	    // hash的key采用String的序列化方式
	    template.setHashKeySerializer(stringRedisSerializer);
	    // value序列化方式采用jackson序列化方式
	    template.setValueSerializer(jackson2JsonRedisSerializer);
	    // hash的value序列化方式采用jackson序列化方式
	    template.setHashValueSerializer(jackson2JsonRedisSerializer);
	    template.afterPropertiesSet();
	    template.setEnableTransactionSupport(true);
	    return template;
	  }
	  /**
	   * 重新实现StringRedisTmeplate：键值都是String的的数据
	   * @param redisConnectionFactory
	   * @return
	   */
	  @Bean
	  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
	    StringRedisTemplate template = new StringRedisTemplate();
	    Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
	    template.setConnectionFactory(redisConnectionFactory);
	    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	    // key采用String的序列化方式
	    template.setKeySerializer(stringRedisSerializer);
	    // hash的key采用String的序列化方式
	    template.setHashKeySerializer(stringRedisSerializer);
	    // value序列化方式采用jackson序列化方式
	    template.setValueSerializer(jackson2JsonRedisSerializer);
	    // hash的value序列化方式采用jackson序列化方式
	    template.setHashValueSerializer(jackson2JsonRedisSerializer);
	    return template;
	  }
}
