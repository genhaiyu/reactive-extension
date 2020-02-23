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
package org.yugh.coral.core.config.distribute;


import org.springframework.util.Assert;
import org.yugh.coral.core.config.SimpleSnowFlakeGenerated;
import org.yugh.coral.core.config.handler.ApplicationContextHelper;

/**
 * @author yugenhai
 */
public class SnowFlakeGenerated implements SimpleSnowFlakeGenerated {

    private static final String SIMPLE_DISTRIBUTE_CONFIG_NAME = "simpleDistributeConfig";

    @Override
    public Long simpleSnowFlakeGenerated() {
        SimpleSnowFlakeGenerated generated = this::snowFlakeGenerated;
        return generated.simpleSnowFlakeGenerated();
    }


    /**
     * @return Long
     */
    private Long snowFlakeGenerated() {
        SimpleDistributeConfig simpleDistributeConfig = ApplicationContextHelper.getBean(SIMPLE_DISTRIBUTE_CONFIG_NAME);
        Assert.notNull(simpleDistributeConfig, () -> "simpleDistributeConfig '" + simpleDistributeConfig + "' failed to load");
        if (simpleDistributeConfig.getDatacenterId() > 0x00 && simpleDistributeConfig.getMachineId() > 0x00) {
            return new SnowFlake(simpleDistributeConfig.getDatacenterId(), simpleDistributeConfig.getMachineId()).nextId();
        }
        return new SnowFlake(0x00, 0x00).nextId();
    }
}
