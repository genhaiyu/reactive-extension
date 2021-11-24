package reactive.extension.basement.request;

import java.util.Objects;

/**
 * Subscriber Context for WebFlux.
 */
public class ReactiveWebContextHeader<H, I> {

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

    public void initialReactContextHeader(I ids, H contextMap) {
        this.setContextMap(contextMap);
        this.setIds(ids);
    }

    @Override
    public String toString() {
        return "ReactiveContextHeader{" +
                "ids=" + ids +
                ", contextMap=" + contextMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReactiveWebContextHeader)) return false;
        ReactiveWebContextHeader<?, ?> header = (ReactiveWebContextHeader<?, ?>) o;
        return Objects.equals(getIds(), header.getIds()) && Objects.equals(getContextMap(), header.getContextMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIds(), getContextMap());
    }
}
