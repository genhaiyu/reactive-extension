/*
 * Copyright (c) 2019-2029, yugenhai108@gmail.com.
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
package org.yugh.coral.boot.servlet.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yugh.coral.core.config.ClientMessageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * For Servlet
 *
 * @author yugenhai
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    @Nullable
    public void apply(@Nullable RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(attributes, () -> "FeignRequestInterceptor attributes.getRequest() '" + attributes + "' must not null");
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(ClientMessageInfo.AUTHORIZATION);
        requestTemplate.header(token);
    }
}
