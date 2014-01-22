package ro.infoiasi.wad.sesi.service.util;

import javax.ws.rs.core.MediaType;

public class MimeTypeConstants {

    private MimeTypeConstants () {}

    public static final MediaType TURTLE = new MediaType("text", "turtle");
    public static final String TURTLE_STRING = "text/turtle";
    public static final MediaType RDFXML = new MediaType("application", "rdf+xml");
    public static final String RDFXML_STRING = "application/rdf+xml";

}
