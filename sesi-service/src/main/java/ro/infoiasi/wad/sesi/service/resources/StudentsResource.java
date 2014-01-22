package ro.infoiasi.wad.sesi.service.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentsResource {

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getStudent(@PathParam("id") String studentId,
            @QueryParam("fields") List<String> fields) {


       return null;
    }

    @POST
    @Path("/{id}/internships")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response applyToInternship(@PathParam("id") String studentId,
            @FormParam("internshipId") String internshipId) {

      return null;
    }
}
