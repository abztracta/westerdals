package no.woact.config;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@ApplicationPath("/rs")
@ApplicationScoped
public class ApplicationConfig extends Application {
}
