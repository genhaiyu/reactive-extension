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
package org.yugh.coral.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yugh.coral.core.config.cache.CoreRedisConfig;
import org.yugh.coral.core.config.distribute.SimpleDistributeConfig;

/**
 * @author yugenhai
 */
@Configuration
public class CoreContextConfig {

    @Bean
    @ConditionalOnMissingBean(CoreRedisConfig.class)
    public CoreRedisConfig simpleRedisConfig() {
        return new CoreRedisConfig();
    }

    @Bean
    @ConditionalOnMissingBean(SimpleDistributeConfig.class)
    public SimpleDistributeConfig simpleDistributeConfig() {
        return new SimpleDistributeConfig();
    }

}
