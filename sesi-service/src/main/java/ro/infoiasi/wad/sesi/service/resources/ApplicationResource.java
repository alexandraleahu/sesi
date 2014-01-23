package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.dao.ApplicationsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/applications")
public class ApplicationResource {

    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllApplications(@QueryParam("fields") List<String> fields,
                                       @Context HttpHeaders headers) {

        ApplicationsDao dao = new ApplicationsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String allApplications = dao.getAllApplications((RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(allApplications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve applications", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllApplications(@PathParam("id") String applicationId,
                                       @QueryParam("fields") List<String> fields,
                                       @Context HttpHeaders headers) {

        ApplicationsDao dao = new ApplicationsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String application = dao.getApplicationById(applicationId, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(application, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve application with id:" + applicationId, e);
        }
    }

}
