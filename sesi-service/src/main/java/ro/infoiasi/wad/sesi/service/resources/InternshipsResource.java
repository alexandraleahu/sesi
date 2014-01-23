package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.rio.RDFFormat;
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
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllInternships(@QueryParam("q") String searchParam,
                                      @QueryParam("fields") List<String> fields,
                                      @QueryParam("matching") String studentId,
                                      @Context HttpHeaders headers) {

        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();

            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String allInternships = dao.getAllInternships((RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(allInternships, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internships", e);
        }
    }


    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getInternship(@PathParam("id") String internshipId,
                                  @QueryParam("q") String searchParam,
                                  @QueryParam("fields") List<String> fields,
                                  @Context HttpHeaders headers) {
        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();

            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);
            String internship = dao.getInternshipById(internshipId, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(internship, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internship with id" + internshipId, e);
        }
    }

    @GET
    @Path("/{id}/applications")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getInternshipApplications(@PathParam("id") String internshipId,
                                              @QueryParam("fields") List<String> fields,
                                              @QueryParam("accepted") Boolean accepted,
                                              @Context HttpHeaders headers) {

        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();

            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String applications = dao.getAllInternshipApplications(internshipId, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internship applications for internship with id" + internshipId, e);
        }
    }

    @GET
    @Path("/{id}/salary")
    @Produces({MediaTypeConstants.SPARQL_JSON_STRING, MediaTypeConstants.SPARQL_XML_STRING})
    public Response getInternshipSalary(@PathParam("id") String internshipId,
                                        @Context HttpHeaders headers) {

        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();

            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String applications = dao.getInternshipSalary(internshipId, (TupleQueryResultFormat) returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internship applications for internship with id" + internshipId, e);
        }
    }

    @GET
    @Path("/{id}/currency")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getInternshipSalaryCurrency(@PathParam("id") String internshipId,
                                                @Context HttpHeaders headers) {

        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();

            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String applications = dao.getInternshipCurrency(internshipId, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internship applications for internship with id" + internshipId, e);
        }
    }
}
