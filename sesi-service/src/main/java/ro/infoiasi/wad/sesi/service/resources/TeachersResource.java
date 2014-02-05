package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.UserAccount;
import ro.infoiasi.wad.sesi.rdf.dao.TeachersDao;
import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teachers")
public class TeachersResource {
    private static final UsersTable usersTable = new UsersTable();

    static {
        usersTable.createTable();
    }

    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getTeachers(@QueryParam("fields") List<String> fields,
                                @Context HttpHeaders headers) {

        TeachersDao dao = new TeachersDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String allTeachers = dao.getAllTeachers(returnTypes.getRdfFormat());
            return Response.ok(allTeachers, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve teachers", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getTeacher(@PathParam("id") String teacherId,
                               @QueryParam("fields") List<String> fields,
                               @Context HttpHeaders headers) {

        TeachersDao dao = new TeachersDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String teacher = dao.getTeacherById(teacherId, returnTypes.getRdfFormat());
            return Response.ok(teacher, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve teacher with id " + teacherId, e);
        }
    }


    @GET
    @Path("/{id}/internshipProgressDetails")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getTeachersInternshipProgressDetails(@PathParam("id") String teacherId,
                                                         @QueryParam("fields") List<String> fields,
                                                         @Context HttpHeaders headers) {

        TeachersDao dao = new TeachersDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String teacher = dao.getAllInternshipProgressDetails(teacherId, returnTypes.getRdfFormat());
            return Response.ok(teacher, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve teacher internship progress details with sid " + teacherId, e);
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(@FormParam("username") String username, @FormParam("pass") String password) {
        if (usersTable.addUser(new DBUser(username, password, UserAccount.TEACHER_ACCOUNT))) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();

    }
}
