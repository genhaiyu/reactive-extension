package org.yugh.coral.core.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Subscriber Context for WebFlux
 * <p>
 * Reactive 流式透传用户信息
 *
 * @author yugenhai
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestHeader<H> {

    private List ids;
    private H contextMap;
}
