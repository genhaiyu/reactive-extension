/*
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yugh.coral.core.config.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author yugenhai
 */
@Data
public class CoreRedisConfig {

    /**
     * expire
     */
    @Value("${spring.redis.expire:30}")
    private int expire;

    /**
     * Default key-prefix: coral:xx
     */
    @Value("${spring.redis.key-prefix:coral:unspecified}")
    private String keyPrefix;

    /**
     * Redis Switch, default true
     */
    @Value("${spring.redis.open:false}")
    private boolean open;


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        Assert.isTrue(open, () -> "redis switch '" + open + "' not initialized");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // Jackson 2.10 Redis 序列化
        PolymorphicTypeValidator polymorphicTypeValidator = BasicPolymorphicTypeValidator
                .builder()
                .allowIfBaseType(List.class)
                .allowIfSubType(Object.class)
                .allowIfSubType(Date.class)
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
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    /*
     *  final PolymorphicTypeValidator ptv =
     * BasicPolymorphicTypeValidator.builder()
     *                 .allowIfBaseType(BaseValue.class)
     *                 .build();
     *         // Note: 2.10 adds `SmileMapper` for type-safe construction
     *         ObjectMapper mapper = SmileMapper.builder()
     *                 .activateDefaultTyping(ptv, DefaultTyping.NON_FINAL)
     *                 .build();
     */
}
