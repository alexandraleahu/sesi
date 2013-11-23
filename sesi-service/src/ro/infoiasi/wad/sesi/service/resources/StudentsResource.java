package ro.infoiasi.wad.sesi.service.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ro.infoiasi.wad.sesi.core.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Path("/students")
public class StudentsResource {

    @GET
    @Path("/{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("id") int studentId,
                              @QueryParam("fields") List<String> fields) throws URISyntaxException {

        Student student = new Student();
        student.setId(1);
        StudentProfile profile = new StudentProfile();
        profile.setName("Ion Popescu");
        profile.setSummary("Interested in Java technologies.");
        profile.setLanguages(Lists.newArrayList(Language.Romanian, Language.English));
        Project project = new Project();
        project.setName("Poshet");
        project.setDescription("POP3 mail client");
        project.setTechnologies(Lists.newArrayList("Java", "XML"));
        profile.setProjects(Lists.newArrayList(project));
        profile.setSkills(Lists.newArrayList("Java", "SQL", "XML"));
        School school = new School();
        school.setId(2);
        school.setName("Faculty of Computer Science");
        school.setDescription("Be among the first");
        Map<School, String> education = Maps.newHashMap();
        education.put(school, "I participated in various academic activities");
        profile.setEducation(education);

        student.setStudentProfile(profile);
        return Response.ok(student, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Path("/{id: \\d+}/internships")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Internship> applyToInternship(@PathParam("id") int studentId, @FormParam("internshipId") int internshipId) {

        Internship internship = new Internship();
        internship.setId(internshipId);

        internship.setAcquiredSkills(Lists.newArrayList("JavaEE", "JSP"));
        internship.setCategory(Internship.Category.Business);
        internship.setApplicantsNo(3);
        internship.setName("JavaEE Internship");

        List<Internship> pendingInternships = Lists.newArrayList(internship);

        return pendingInternships;

    }
}
