package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.rdf.dao.TeachersDao;
import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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
    public Response register(@FormParam("username") String username, @FormParam("password") String password,
                             @FormParam("name") String teacherName,
                             @Context UriInfo uriInfo) {
        if (usersTable.addUser(new DBUser(username, password, UserAccountType.TEACHER_ACCOUNT.getDescription()))) {
            try {
                new TeachersDao().createMinimalTeacher(teacherName, username);
                URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(username)).build();
                return Response.created(uri)
                        .build();

            } catch (StardogException e) {
                throw new InternalServerErrorException(e.getMessage());
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
           Teacher updatedTeacher) {

        
        try {
            new TeachersDao().updateTeacher(updatedTeacher);
            return Response.ok().build();
        } catch (StardogException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
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
