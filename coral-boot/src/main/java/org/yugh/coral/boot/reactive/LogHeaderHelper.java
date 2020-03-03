package org.yugh.coral.boot.reactive;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.yugh.coral.core.config.LogMessageInfo;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author yugenhai
 */
@Slf4j
public class LogHeaderHelper {

    public static <T> Consumer<Signal<T>> logOnNext(Consumer<T> consumer) {
        return signal -> {
            if (signal.getType() != SignalType.ON_NEXT) {
                return;
            }
            Optional<Map<String, String>> contextMap = signal.getContext().getOrEmpty(LogMessageInfo.CONTEXT_MAP);
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
            Optional<Map<String, String>> contextMap = signal.getContext().getOrEmpty(LogMessageInfo.CONTEXT_MAP);
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
            Optional<Map<String, String>> contextMap = ctx.getOrEmpty(LogMessageInfo.CONTEXT_MAP);
            if (contextMap.isPresent()) {
                contextMap.get().put(key, value);
                return ctx;
            } else {
                Map<String, String> ctxMap = Maps.newHashMap();
                ctxMap.put(key, value);
                return ctx.put(LogMessageInfo.CONTEXT_MAP, ctxMap);
            }
        };
    }
}
