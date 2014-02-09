package ro.infoiasi.wad.sesi.service.resources;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openrdf.rio.RDFFormat;

import ro.infoiasi.wad.sesi.core.model.Course;
import ro.infoiasi.wad.sesi.core.model.Faculty;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.StudentProject;
import ro.infoiasi.wad.sesi.core.model.Studies;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.core.model.TechnicalSkill;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.rdf.dao.TeachersDao;
import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import com.complexible.stardog.StardogException;

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
                             @FormParam("name") String teacherName) {
        if (usersTable.addUser(new DBUser(username, password, UserAccountType.TEACHER_ACCOUNT.getDescription()))) {
            try {
                new TeachersDao().createMinimalTeacher(teacherName, username);
                return Response.ok().build();

            } catch (StardogException e) {
                throw new InternalServerErrorException(e.getMessage());
            }
        } else {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateStudent(@PathParam("id") String id,
            @FormParam("name") String name,
            @FormParam("courses") String courses,
            @FormParam("monitoringInternships") String monitoringInternships,
            @FormParam("siteUrl") String siteUrl,
            @FormParam("title") String title,
            @FormParam("faculty") String faculty) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName(name);
        List<Course> c = new LinkedList<Course>();
        for(String co : courses.split(",")) {
            Course course = new Course();
            c.add(course);
        }
        teacher.setCourses(c);
        List<InternshipProgressDetails> proj = new LinkedList<InternshipProgressDetails>();
        for (String project : monitoringInternships.split(",")) {
            InternshipProgressDetails e = new InternshipProgressDetails();
            proj.add(e);
        }
        teacher.setMonitoringInternships(proj);
        teacher.setTitle(title);
        teacher.setSiteUrl(siteUrl);
        
        Faculty f = new Faculty();
        teacher.setFaculty(f);
        
        try {
            new TeachersDao().updateTeacher(teacher);
        } catch (StardogException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
        return Response.ok().build();
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
