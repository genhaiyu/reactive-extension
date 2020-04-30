package io.genhai.client.flux.api;

import io.genhai.client.flux.exception.ResourceNotFoundException;
import io.genhai.client.flux.model.Resource;
import io.genhai.client.flux.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 *  Using 'application/json' given [/] and supported [application/json, application/+json, text/event-stream]
 *
 * @author yugenhai
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(final ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound() {
    }

    @GetMapping("/list")
    public Flux<Resource> list() {
        return this.resourceService.list();
    }

    @GetMapping("/get/{id}")
    public Mono<Resource> getById(@PathVariable("id") final String id) {
        return this.resourceService.getById(id);
    }

    @PostMapping("/create")
    public Mono<Resource> create(@RequestBody final Resource resource) {
        return this.resourceService.createOrUpdate(resource);
    }

    @PutMapping("/update/{id}")
    public Mono<Resource> update(@PathVariable("id") final String id, @RequestBody final Resource resource) {
        Objects.requireNonNull(resource);
        resource.setId(id);
        return this.resourceService.createOrUpdate(resource);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Resource> delete(@PathVariable("id") final String id) {
        return this.resourceService.delete(id);
    }

}
