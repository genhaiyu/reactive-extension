/**
 * 17.5 Feign Hystrix Fallbacks
 * Hystrix supports the notion of a fallback: a default code path that is executed when they circuit is open or there is an error. To enable fallbacks for a given @FeignClient set the fallback attribute to the class name that implements the fallback. You also need to declare your implementation as a Spring bean.
 *
 * @FeignClient(name = "hello", fallback = HystrixClientFallback.class)
 * protected interface HystrixClient {
 * @RequestMapping(method = RequestMethod.GET, value = "/hello")
 * Hello iFailSometimes();
 * }
 * <p>
 * static class HystrixClientFallback implements HystrixClient {
 * @Override public Hello iFailSometimes() {
 * return new Hello("fallback");
 * }
 * }
 * If one needs access to the cause that made the fallback trigger, one can use the fallbackFactory attribute inside @FeignClient.
 * @FeignClient(name = "hello", fallbackFactory = HystrixClientFallbackFactory.class)
 * protected interface HystrixClient {
 * @RequestMapping(method = RequestMethod.GET, value = "/hello")
 * Hello iFailSometimes();
 * }
 * @Component static class HystrixClientFallbackFactory implements FallbackFactory<HystrixClient> {
 * @Override public HystrixClient create(Throwable cause) {
 * return new HystrixClient() {
 * @Override public Hello iFailSometimes() {
 * return new Hello("fallback; reason was: " + cause.getMessage());
 * }
 * };
 * }
 * }
 */
package org.yugh.coral.core.config.proxy;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yugenhai
 */
@Slf4j
public class DefaultFallbackFactory<T> implements FallbackFactory<T>, InitializingBean {

    private Class<T> clazz;
    private T t;

    @Override
    public T create(Throwable throwable) {
        log.error("\n Feign Exception ClassName : {}, Throwable Msg : {} ", this.clazz.getSimpleName(), throwable.getMessage());
        return t;
    }


    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType pt = ParameterizedType.class.cast(type);
        clazz = Class.class.cast(pt.getActualTypeArguments()[0]);
        t = new FeignProxy<>(clazz).getProxy();
    }
}

