package com.example.demo.config;

import com.alibaba.fastjson2.*;

import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import com.example.demo.base.SpringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * 缓存配置-使用Lettuce客户端，自动注入配置的方式
 */
@Configuration
@EnableCaching //启用缓存
@ConfigurationProperties(prefix = "spring.redis") //指明配置节点
public class RedisConfig extends CachingConfigurerSupport {
    // Redis服务器地址
    @Value("${spring.redis.host}")
    private String host;
    // Redis服务器连接端口
    @Value("${spring.redis.port}")
    private Integer port;
    // Redis数据库索引（默认为0）
    @Value("${spring.redis.database}")
    private Integer database;
    // Redis服务器连接密码（默认为空）
    @Value("${spring.redis.password}")
    private String password;
    // 连接超时时间（毫秒）
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    // 连接池最大连接数（使用负值表示没有限制）
    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxTotal;
    // 连接池最大阻塞等待时间（使用负值表示没有限制）
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Integer maxWait;
    // 连接池中的最大空闲连接
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;
    // 连接池中的最小空闲连接
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;
    // 关闭超时时间
    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private Integer shutdown;

    /**
     * 自定义缓存key的生成策略。默认的生成策略是看不懂的(乱码内容) 通过Spring 的依赖注入特性进行自定义的配置注入并且此类是一个配置类可以更多程度的自定义配置
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        //以锁写入的方式创建RedisCacheWriter对象
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(SpringUtils.getBean(RedisConnectionFactory.class));
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        RedisSerializationContext.SerializationPair pair = RedisSerializationContext.SerializationPair.fromSerializer(SpringUtils.getBean(GenericFastJsonRedisSerializer.class));
        config.serializeValuesWith(pair);
        RedisCacheManager cacheManager = new RedisCacheManager(writer, config);
        return cacheManager;
    }

    /**
     * 获取缓存操作助手对象
     *
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, GenericFastJsonRedisSerializer genericFastJsonRedisSerializer) {
        //创建Redis缓存操作助手RedisTemplate对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(genericFastJsonRedisSerializer);
        return template;
    }
    /**
     * 获取缓存连接
     *
     * @return
     */
    @Bean
    public RedisConnectionFactory getConnectionFactory(LettucePoolingClientConfiguration config) {
        //单机模式
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setDatabase(database);
        configuration.setPassword(RedisPassword.of(password));
        //哨兵模式
        //RedisSentinelConfiguration configuration1 = new RedisSentinelConfiguration();
        //集群模式
        //RedisClusterConfiguration configuration2 = new RedisClusterConfiguration();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, config);
        //factory.setShareNativeConnection(false);//是否允许多个线程操作共用同一个缓存连接，默认true，false时每个操作都将开辟新的连接
        return factory;
    }

    /**
     * 获取缓存连接池
     *
     * @return
     */
    @Bean
    public LettucePoolingClientConfiguration getPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWait);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
                .poolConfig(config)
                .commandTimeout(Duration.ofMillis(timeout))
                .shutdownTimeout(Duration.ofMillis(shutdown))
                .build();
        return pool;
    }

    @Bean
    public GenericFastJsonRedisSerializer getFastJsonRedisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

}

