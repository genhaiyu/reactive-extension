package org.yugh.coral.http.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Sample
 *
 * @author yugenhai
 */
@Component
@ConfigurationProperties(prefix = "spring.application")
public class ApplicationProperties {

    private final Application application = new Application();
    private final Profiles profiles =  new Profiles();

    public Application getApplication() {
        return application;
    }

    public Profiles getProfiles() {
        return profiles;
    }

    public static class Application {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Profiles {

        private String active;

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }
    }

}
