package ro.infoiasi.wad.sesi.service.resources;

import com.google.common.collect.ImmutableList;
import ro.infoiasi.wad.sesi.core.model.Internship;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Internships Resource (exposed at "/api/internships" path)
 */
@Path("/internships")
public class InternshipsResource {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Internship> getAllInternships() {

        Internship i = new Internship();
        i.setName("First Internship");
        Internship i2 = new Internship();
        i2.setName("Second Internship");

        return ImmutableList.of(i, i2);
    }
}
