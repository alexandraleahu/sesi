package ro.infoiasi.wad.sesi.service.resources;

import ro.infoiasi.wad.sesi.core.model.Application;
import ro.infoiasi.wad.sesi.core.model.Internship;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Internships Resource (exposed at "/api/internships" path)
 */
@Path("/internships")
public class InternshipsResource {

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Internship> getAllInternships(@QueryParam("q") String searchParam,
                                              @QueryParam("fields") List<String> fields,
                                              @QueryParam("matching") int studentId) {

        return null;
    }


    @GET
    @Path("/{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Internship getInternship(@PathParam("id") int internshipId,
                                    @QueryParam("fields") List<String> fields) {
        return null;
    }

    @GET
    @Path("/{id: \\d+}/applications")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Application> getInternshipStudents(
                                                @PathParam("id") int internshipId,
                                                @QueryParam("fields") List<String> fields,
                                                @QueryParam("accepted") Boolean accepted) {

        return null;
    }
}
