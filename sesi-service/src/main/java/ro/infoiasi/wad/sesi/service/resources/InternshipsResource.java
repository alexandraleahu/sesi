package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.rdf.dao.InternshipsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
            throw new InternalServerErrorException("Could not retrieve internships", e);
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
            throw new InternalServerErrorException("Could not retrieve internship with id" + internshipId, e);
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
            throw new InternalServerErrorException("Could not retrieve internship fields with id" + internshipId, e);
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

            MediaTypeConstants.MediaTypeAndRdfFormat<TupleQueryResultFormat> returnTypes = MediaTypeConstants.getBestSparqlReturnTypes(acceptableMediaTypes);

            String applications = dao.getInternshipSalary(internshipId, returnTypes.getRdfFormat());
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

            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String applications = dao.getInternshipCurrency(internshipId, returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internship applications for internship with id" + internshipId, e);
        }
    }


//    @POST
//    @Path("/")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Response createInternship(@Context HttpHeaders headers, Internship internship) {
//        InternshipsDao dao = new InternshipsDao();
//        internship.setId(RandomStringUtils.randomAlphanumeric(4));
//        try {
//            String uri = dao.createInternship(internship);
//            System.out.println("i got here");
//            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
//            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);
//            return Response.ok(uri, returnTypes.getMediaType()).build();
//        } catch (StardogException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response applyToInternship(@PathParam("id") String studentId,
                                      @FormParam("internshipId") String internshipId) {

        return null;
    }
}
