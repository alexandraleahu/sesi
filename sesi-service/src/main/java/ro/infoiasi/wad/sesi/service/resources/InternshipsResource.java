package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.rdf.dao.InternshipsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * internships Resource (exposed at "/api/internships" path)
 */
@Path("/internships")
public class InternshipsResource {

    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllInternships(@QueryParam("category") Internship.Category category,
                                      @QueryParam("latest") Boolean latest,
                                      @Context HttpHeaders headers) {

        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();

            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);
            String allInternships = null;
            if (latest != null && latest == true) {
                allInternships = dao.getLatestInternships((RDFFormat) returnTypes.getRdfFormat());
            } else if (category != null) {
                allInternships = dao.getInternshipsByCategory(category, (RDFFormat) returnTypes.getRdfFormat());
            } else {
                allInternships = dao.getAllInternships((RDFFormat) returnTypes.getRdfFormat());
            }
            return Response.ok(allInternships, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getInternship(@PathParam("id") String internshipId,
                                  @Context HttpHeaders headers) {
        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();


            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String internship = dao.getInternshipById(internshipId, returnTypes.getRdfFormat());
            return Response.ok(internship, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.SPARQL_JSON_STRING, MediaTypeConstants.SPARQL_XML_STRING})
    public Response getInternship(@PathParam("id") String internshipId,
                                  @QueryParam("fields") List<String> fields,
                                  @Context HttpHeaders headers) {
        InternshipsDao dao = new InternshipsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<TupleQueryResultFormat> returnTypes = MediaTypeConstants.getBestSparqlReturnTypes(acceptableMediaTypes);

            String internship = dao.getInternshipFieldsById(internshipId, fields, returnTypes.getRdfFormat());
            if (internship == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {

                return Response.ok(internship, returnTypes.getMediaType()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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

            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String applications = dao.getAllInternshipApplications(internshipId, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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

            MediaTypeConstants.MediaTypeAndRdfFormat<TupleQueryResultFormat> returnTypes = MediaTypeConstants.getBestSparqlReturnTypes(acceptableMediaTypes);

            String applications = dao.getInternshipSalary(internshipId, returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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

            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String applications = dao.getInternshipCurrency(internshipId, returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @POST
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createInternship(@Context HttpHeaders headers, Internship internship,
                                     @Context UriInfo uriInfo) {
        InternshipsDao dao = new InternshipsDao();
        String id = RandomStringUtils.randomAlphanumeric(4);
        internship.setId(id);
        internship.setPublishedAt(new Date());
        try {
             dao.createInternship(internship);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
            return Response.created(uri)
                    .entity(internship)
                    .build();
        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}
