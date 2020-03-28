package org.yugh.coral.boot.reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.yugh.coral.core.config.RequestHeaderProvider;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author yugenhai
 */
public class ReactiveLogHeaderHelper {

    private static final Logger LOG = LoggerFactory.getLogger(ReactiveLogHeaderHelper.class);

    public static <T> Consumer<Signal<T>> logOnNext(Consumer<T> consumer) {
        return signal -> {
            if (signal.getType() != SignalType.ON_NEXT) {
                return;
            }
            Optional<Map<String, String>> contextMap = signal.getContext().getOrEmpty(RequestHeaderProvider.CONTEXT_MAP);
            if (contextMap.isPresent()) {
                consumer.accept(signal.get());
            } else {
                MDC.setContextMap(contextMap.get());
                try {
                    consumer.accept(signal.get());
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    public static <T> Consumer<Signal<T>> logOnError(Consumer<Throwable> consumer) {
        return signal -> {
            if (!signal.isOnError()) {
                return;
            }
            Optional<Map<String, String>> contextMap = signal.getContext().getOrEmpty(RequestHeaderProvider.CONTEXT_MAP);
            if (contextMap.isPresent()) {
                consumer.accept(signal.getThrowable());
            } else {
                MDC.setContextMap(contextMap.get());
                try {
                    consumer.accept(signal.getThrowable());
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    public static Function<Context, Context> setContext(String key, String value) {
        return ctx -> {
            Optional<Map<String, String>> contextMap = ctx.getOrEmpty(RequestHeaderProvider.CONTEXT_MAP);
            if (contextMap.isPresent()) {
                contextMap.get().put(key, value);
                return ctx;
            } else {
                Map<String, String> ctxMap = new HashMap<>();
                ctxMap.put(key, value);
                return ctx.put(RequestHeaderProvider.CONTEXT_MAP, ctxMap);
            }
        };
    }
}
