package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.dao.ProgressDetailsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/internshipProgressDetails")
public class InternshipProgressDetailsResource {
    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllInternshipProgressDetails(@QueryParam("fields") List<String> fields,
                                                    @Context HttpHeaders headers) {

        ProgressDetailsDao dao = new ProgressDetailsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String allProgressDetails = dao.getAllProgressDetails((RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(allProgressDetails, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve progress details ", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getInternshipProgressDetails(@PathParam("id") String id,
                                                 @QueryParam("fields") List<String> fields,
                                                 @Context HttpHeaders headers) {

        ProgressDetailsDao dao = new ProgressDetailsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String progressDetails = dao.getProgressDetailsById(id, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(progressDetails, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve progress details for id: " + id, e);
        }
    }

}
