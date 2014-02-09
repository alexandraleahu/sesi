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

import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.StudentProject;
import ro.infoiasi.wad.sesi.core.model.Studies;
import ro.infoiasi.wad.sesi.core.model.TechnicalSkill;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.rdf.dao.StudentsDao;
import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import com.complexible.stardog.StardogException;

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

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(@FormParam("username") String username, @FormParam("password") String password,
                             @FormParam("name") String studentName) {
        if (usersTable.addUser(new DBUser(username, password, UserAccountType.STUDENT_ACCOUNT.getDescription()))) {
            try {
                new StudentsDao().createMinimalStudent(studentName, username);
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
            @FormParam("description") String description,
            @FormParam("relativeUri") String relativeUri,
            @FormParam("generalSkills") String generalSkills,
            @FormParam("projects") String projects,
            @FormParam("studies") String studies,
            @FormParam("technicalSkills") String technicalSkills) {
        Student student = new Student();
        student.setDescription(description);
        student.setGeneralSkills(Arrays.asList(generalSkills.split(",")));
        student.setId(id);
        List<StudentProject> proj = new LinkedList<StudentProject>();
        for (String project : projects.split(",")) {
            StudentProject e = new StudentProject();
            proj.add(e);
        }
        student.setProjects(proj);
        student.setName(name);
        student.setStudies(new Studies());
        List<TechnicalSkill> tech = new LinkedList<TechnicalSkill>();
        for (String t : technicalSkills.split(",")) {
            TechnicalSkill ts = new TechnicalSkill();
            tech.add(ts);
        }
        student.setTechnicalSkills(tech);
        try {
            new StudentsDao().updateStudent(student);
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
