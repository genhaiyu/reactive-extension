package org.yugh.coral.core.config.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;

/**
 * @author yugenhai
 */
public class ApplicationContextHelper implements ApplicationContextAware {

    @Nullable
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextHelper.context = context;
    }

    @Nullable
    public static <T> T getBean(Class<T> clazz) {
        if (context == null) {
            return null;
        }
        return context.getBean(clazz);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        if (context == null) {
            return null;
        }
        return (T) context.getBean(beanName);
    }

    @Nullable
    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (context == null) {
            return null;
        }
        return context.getBean(beanName, clazz);
    }

    @Nullable
    public static ApplicationContext getContext() {
        return context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context == null) {
            return;
        }
        context.publishEvent(event);
    }
}
