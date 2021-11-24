package reactive.extension.basement.request;

import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class ContextHeaderCollector {

    private static final Collector<CharSequence, ?, String> COLLECTOR = Collectors.joining("," + System.lineSeparator(), "[", "]");

    /**
     * Apply to subProjects
     *
     * @param prefix reactive-extension: xx
     * @return Mono<String></>
     */
    private static Mono<String> apply(String prefix) {
        return Mono.subscriberContext()
                .map(x -> x.<Map<String, String>>get(RequestHeaderProvider.CONTEXT_MAP))
                .map(x -> prefix + x.entrySet().stream()
                        .map(kv -> kv.getKey() + ": " + kv.getValue())
                        .collect(COLLECTOR));
    }
}
