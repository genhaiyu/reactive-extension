package io.genhai.client.rsocket.config;

import io.genhai.client.rsocket.model.MusicData;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// @Configuration
public class RedisConfiguration {

   // @Bean
    public ReactiveRedisOperations<String, MusicData> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<MusicData> serializer = new Jackson2JsonRedisSerializer<>(MusicData.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, MusicData> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, MusicData> context =
                builder.value(serializer).hashKey(new StringRedisSerializer()).hashValue(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

}
