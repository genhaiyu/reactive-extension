package io.genhai.client.flux.service;

import io.genhai.client.flux.exception.ResourceNotFoundException;
import io.genhai.client.flux.model.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yugenhai
 */
@Service
public class ResourceService {

    private final Map<String, Resource> resourceMap = new ConcurrentHashMap<>();

    public Flux<Resource> list() {
        return Flux.fromIterable(this.resourceMap.values());
    }

    public Flux<Resource> getById(final Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.resourceMap.get(id)));
    }

    public Mono<Resource> getById(final String id) {
        return Mono.justOrEmpty(this.resourceMap.get(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

    public Mono<Resource> createOrUpdate(final Resource resource) {
        this.resourceMap.put(resource.getId(), resource);
        return Mono.just(resource);
    }

    public Mono<Resource> delete(final String id){
        return Mono.justOrEmpty(this.resourceMap.remove(id));
    }
}
