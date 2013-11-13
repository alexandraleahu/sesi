package ro.infoiasi.wad.sesi.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Internships Resource (exposed at "/api/internships" path)
 */
@Path("/internships")
public class InternshipsResource {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllInternships() {

        return "Internships not available yet. Model classes not yet defined.";
    }
}
