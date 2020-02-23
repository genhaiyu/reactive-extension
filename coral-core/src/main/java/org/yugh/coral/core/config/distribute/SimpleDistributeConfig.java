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

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author yugenhai
 */
@Data
public class SimpleDistributeConfig {

    /**
     * 工作 ID (0~31)
     */
    @Value("${distribute.sequences.datacenter.datacenter-id:0}")
    private int datacenterId;

    /**
     * 机器 ID (0~31)
     */
    @Value("${distribute.sequences.machine.machine-id:0}")
    private int machineId;
}
