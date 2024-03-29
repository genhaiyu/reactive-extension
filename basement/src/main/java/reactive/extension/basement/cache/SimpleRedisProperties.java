package reactive.extension.basement.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Custom redis extended properties.
 * After the user has used and initialized the {@link RedisTemplate}
 * This @link RedisOperationProperties#redisTemplate(RedisConnectionFactory) It may not work.
 *
 * @author Genhai Yu
 */
@ConfigurationProperties(prefix = "spring.redis")
public class SimpleRedisProperties {

    private final ExtendedRedis extendedRedis = new ExtendedRedis();

    public ExtendedRedis getExtendedRedis() {
        return extendedRedis;
    }

    public static class ExtendedRedis {

        private int expire;
        /**
         * Default key-prefix: ExtendedRedis:xx.
         */
        private String keyPrefix;

        /**
         * Default true.
         */
        private boolean enabled;

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public String getKeyPrefix() {
            return keyPrefix;
        }

        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Assert.isTrue(extendedRedis.enabled, () -> "reactive-extension redis '" + extendedRedis.enabled + "' is off");
        Assert.hasText(extendedRedis.keyPrefix, () -> "reactive-extension redis key '" + extendedRedis.keyPrefix + "'  should be empty or null");

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // Jackson 2.10
        final PolymorphicTypeValidator polymorphicTypeValidator = BasicPolymorphicTypeValidator
                .builder()
                .allowIfBaseType(List.class)
                .allowIfSubType(Object.class)
                .allowIfSubType(Date.class)
                // .allowIfSubType(Set.class)
                .build();
        ObjectMapper mapper = JsonMapper.builder()
                .visibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .activateDefaultTyping(polymorphicTypeValidator, ObjectMapper.DefaultTyping.NON_FINAL)
                .build();
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
