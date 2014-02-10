package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.rdf.dao.StudentsDao;
import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/students")
public class StudentsResource {
    private static final UsersTable usersTable = new UsersTable();

    static {
        usersTable.createTable();
    }

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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            if (student == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {

                return Response.ok(student, returnTypes.getMediaType()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(@FormParam("username") String username, @FormParam("password") String password,
                             @FormParam("name") String studentName,
                             @Context UriInfo uriInfo) {
        if (usersTable.addUser(new DBUser(username, password, UserAccountType.STUDENT_ACCOUNT.getDescription()))) {
            try {
                new StudentsDao().createMinimalStudent(studentName, username);
                URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(username)).build();
                return Response.created(uri)
                        .build();

            } catch (StardogException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
        } else {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateStudent(@PathParam("id") String id,
            Student updatedStudent) {

        try {
            new StudentsDao().updateStudent(updatedStudent);
            return Response.ok().build();
        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteUser(@PathParam("username") String username) {
        boolean removeUser = usersTable.removeUser(new DBUser(username, null, null));

        if (removeUser) {
            return Response.ok().build();
        }

        // TODO delete from rdf too - setting it to inactive
        return Response.status(Response.Status.NOT_FOUND).build();

    }

}
