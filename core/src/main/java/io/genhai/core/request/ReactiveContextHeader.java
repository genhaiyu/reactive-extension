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
 *
 */


package io.genhai.core.request;

import java.util.Objects;

/**
 * Subscriber Context for WebFlux.
 * Reactive 流式透传用户信息.
 *
 * @author yugenhai
 */
public class ReactiveContextHeader<H, I> {

    private I ids;
    private H contextMap;

    public I getIds() {
        return ids;
    }

    public void setIds(I ids) {
        this.ids = ids;
    }

    public H getContextMap() {
        return contextMap;
    }

    public void setContextMap(H contextMap) {
        this.contextMap = contextMap;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "ids=" + ids +
                ", contextMap=" + contextMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReactiveContextHeader)) return false;
        ReactiveContextHeader<?, ?> that = (ReactiveContextHeader<?, ?>) o;
        return getIds().equals(that.getIds()) &&
                getContextMap().equals(that.getContextMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIds(), getContextMap());
    }
}
