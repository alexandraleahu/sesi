package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.dao.StudentsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentsResource {

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("id") String studentId,
                               @QueryParam("fields") List<String> fields,
                               @Context HttpHeaders headers) {

        StudentsDao dao = new StudentsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String student = dao.getStudent(studentId, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(student, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internships", e);
        }
    }

    @POST
    @Path("/{id}/internships")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response applyToInternship(@PathParam("id") String studentId,
                                      @FormParam("internshipId") String internshipId) {

        return null;
    }
}
