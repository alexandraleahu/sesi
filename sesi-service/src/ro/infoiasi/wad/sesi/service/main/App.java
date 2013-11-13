package ro.infoiasi.wad.sesi.service.main;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/") // makes web.xml no longer needed
public class App extends ResourceConfig {

    public App() {
        packages("ro.infoiasi.wad.sesi.service.resources");
    }
}
