package ro.infoiasi.wad.sesi.service.resources;

import ro.infoiasi.wad.sesi.core.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

@Path("/students")
public class StudentsResource {

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getStudent(@PathParam("id") String studentId,
            @QueryParam("fields") List<String> fields)
            throws URISyntaxException {

        Student student = new Student();
//        student.setId(1);
//        StudentProfile profile = new StudentProfile();
//        profile.setName("Ion Popescu");
//        profile.setSummary("Interested in Java technologies.");
//        profile.setLanguages(Lists.newArrayList(Language.Romanian,
//                Language.English));
//        Project project = new Project();
//        project.setName("Poshet");
//        project.setDescription("POP3 mail client");
//        project.setTechnologies(Lists.newArrayList("Java", "XML"));
//        profile.setProjects(Lists.newArrayList(project));
//        profile.setGeneralSkills(Lists.newArrayList("Java", "SQL", "XML"));
//        Faculty school = new Faculty();
//        school.setId(2);
//        school.setName("Faculty of Computer Science");
//        school.setDescription("Be among the first");
//        Map<Faculty, String> education = Maps.newHashMap();
//        education.put(school, "I participated in various academic activities");
//        profile.setEducation(education);
//
//        student.setStudentProfile(profile);
        return Response.ok(student, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Path("/{id}/internships")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response applyToInternship(@PathParam("id") String studentId,
            @FormParam("internshipId") String internshipId) {

      return null;
    }
}
