package ro.infoiasi.wad.sesi.server.util;

import javax.ws.rs.core.MediaType;

public class ServiceConstants {

    private ServiceConstants() {}

    public static final String SESI_BASE_URL = "http://localhost:8080/sesi/api/";
    public static final MediaType TURTLE = new MediaType("text", "turtle");
    public static final MediaType DEFAULT_ACCEPT_RDF_TYPE = TURTLE;
    public static final String DEFAULT_JENA_LANG = "TURTLE";

}
