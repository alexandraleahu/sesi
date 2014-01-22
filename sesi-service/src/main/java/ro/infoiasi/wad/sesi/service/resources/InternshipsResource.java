package ro.infoiasi.wad.sesi.service.resources;

import ro.infoiasi.wad.sesi.rdf.dao.InternshipsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Internships Resource (exposed at "/api/internships" path)
 */
@Path("/internships")
public class InternshipsResource {

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllInternships(@QueryParam("q") String searchParam,
                                              @QueryParam("fields") List<String> fields,
                                              @QueryParam("matching") String studentId,
                                              @Context HttpHeaders headers) {

        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();

            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String allInternships = dao.getAllInternships(returnTypes.getRdfFormat());
            return Response.ok(allInternships, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internships", e);
        }

    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getInternship(@PathParam("id") String internshipId,
                                    @QueryParam("fields") List<String> fields) {
        return null;
    }

    @GET
    @Path("/{id}/applications")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getInternshipStudents(
                                                @PathParam("id") String internshipId,
                                                @QueryParam("fields") List<String> fields,
                                                @QueryParam("accepted") Boolean accepted) {

        return null;
    }
}
