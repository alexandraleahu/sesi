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
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllStudents(@QueryParam("q") String searchParam,
                                   @QueryParam("fields") List<String> fields,
                                   @QueryParam("matching") String studentId,
                                   @Context HttpHeaders headers) {

        StudentsDao dao = new StudentsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String allInternships = dao.getAllStudents(returnTypes.getRdfFormat());
            return Response.ok(allInternships, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve students", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getStudent(@PathParam("id") String studentId,
                               @QueryParam("fields") List<String> fields,
                               @Context HttpHeaders headers) {

        StudentsDao dao = new StudentsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String student = dao.getStudent(studentId, returnTypes.getRdfFormat());
            return Response.ok(student, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve student with id " + studentId, e);
        }
    }

    @GET
    @Path("/{id}/recommendedInternships")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getStudentRecommendedInternships(@PathParam("id") String studentId,
                                                     @Context HttpHeaders headers) {

        StudentsDao dao = new StudentsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String recommendedInternships = dao.getStudentRecommendedInternships(studentId, returnTypes.getRdfFormat());
            return Response.ok(recommendedInternships, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve recommended internships student with id " + studentId, e);
        }
    }

    @GET
    @Path("/{id}/applications")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getStudentApplications(@PathParam("id") String studentId,
                                           @QueryParam("fields") List<String> fields,
                                           @QueryParam("accepted") Boolean accepted,
                                           @Context HttpHeaders headers) {

        StudentsDao dao = new StudentsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String applications = dao.getAllStudentApplications(studentId, returnTypes.getRdfFormat());
            return Response.ok(applications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internship applications for student with id" + studentId, e);
        }
    }

    @GET
    @Path("/{id}/internshipProgressDetails")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getStudentProgressDetails(@PathParam("id") String studentId,
                                              @QueryParam("fields") List<String> fields,
                                              @QueryParam("accepted") Boolean accepted,
                                              @Context HttpHeaders headers) {

        StudentsDao dao = new StudentsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);
            String progressDetails = dao.getStudentInternshipsProgressDetails(studentId, returnTypes.getRdfFormat());
            return Response.ok(progressDetails, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve internship progress details for student with id" + studentId, e);
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
