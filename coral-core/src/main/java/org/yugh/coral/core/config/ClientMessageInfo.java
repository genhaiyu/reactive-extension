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

/**
 * @author yugenhai
 */
public interface ClientMessageInfo {

    String SERVLET_REQUEST_ENABLE       = "coral.servlet.request.enabled";
    String REACTIVE_REQUEST_ENABLE      = "coral.reactive.request.enabled";
    String JETTY_CONTAINER_CONFIG       = "coral.jetty.config.enabled";
    String REACTOR_CONTAINER_CONFIG     = "coral.reactor.config.enabled";
    String TOMCAT_CONTAINER_CONFIG      = "coral.tomcat.config.enabled";
    String UNDERTOW_CONTAINER_CONFIG    = "coral.undertow.config.enabled";
    String AUTHORIZATION                = "Authorization";
    // String CONTAINER_AUTO_CONFIG        = "coral.container.config.?";
}
