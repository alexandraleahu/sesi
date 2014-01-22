package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.dao.InternshipsDao;
import ro.infoiasi.wad.sesi.service.util.MimeTypeConstants;

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
    @Produces({MediaType.APPLICATION_JSON, MimeTypeConstants.RDFXML_STRING, MimeTypeConstants.TURTLE_STRING})
    public Response getAllInternships(@QueryParam("q") String searchParam,
                                              @QueryParam("fields") List<String> fields,
                                              @QueryParam("matching") String studentId,
                                              @Context HttpHeaders headers) {

        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            System.out.println(acceptableMediaTypes.get(0).toString());
            String allInternships = dao.getAllInternships(RDFFormat.JSONLD);
            return Response.ok(allInternships, MediaType.APPLICATION_JSON).build();
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
