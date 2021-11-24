package reactive.extension.basement.config;

/**
 * Processor of Core request.
 *
 * @author Genhai Yu
 */
@FunctionalInterface
public interface DispatcherRequestCustomizer<T> {

    /**
     * Tracking is available.
     *
     * @param object ...
     */
    void customize(T object);
}
