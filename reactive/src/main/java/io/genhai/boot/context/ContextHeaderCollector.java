/*
 *
 * Copyright (c) 2014-2020, yugenhai108@gmail.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */


package io.genhai.boot.context;

import io.genhai.core.request.RequestHeaderProvider;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * When using Mono, the project identity is explicitly declared.
 *
 * @author yugenhai
 */
public class ContextHeaderCollector {

    private static final Collector<CharSequence, ?, String> COLLECTOR = Collectors.joining("," + System.lineSeparator(), "[", "]");

    /**
     * Apply to other projects
     *
     * @param prefix
     * @return
     */
    private static Mono<String> apply(String prefix) {
        return Mono.subscriberContext()
                .map(x -> x.<Map<String, String>>get(RequestHeaderProvider.CONTEXT_MAP))
                .map(x -> prefix + x.entrySet().stream()
                        .map(kv -> kv.getKey() + ": " + kv.getValue())
                        .collect(COLLECTOR));
    }
}
