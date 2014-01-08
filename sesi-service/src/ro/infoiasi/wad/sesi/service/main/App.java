package ro.infoiasi.wad.sesi.service.main;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/api/")
// makes web.xml no longer needed
public class App extends ResourceConfig {

    public App() {
        register(RolesAllowedDynamicFeature.class);
        packages("ro.infoiasi.wad.sesi.service.resources");
    }
}
