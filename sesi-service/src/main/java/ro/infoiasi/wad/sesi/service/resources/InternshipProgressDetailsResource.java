package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.rdf.dao.InternshipProgressDetailsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/internshipsProgressDetails")
public class InternshipProgressDetailsResource {
    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllInternshipProgressDetails(@QueryParam("fields") List<String> fields,
                                                    @Context HttpHeaders headers) {

        InternshipProgressDetailsDao dao = new InternshipProgressDetailsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String allProgressDetails = dao.getAllProgressDetails(returnTypes.getRdfFormat());
            return Response.ok(allProgressDetails, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve progress details ", e);
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getInternshipProgressDetails(@PathParam("id") String id,
                                                 @QueryParam("fields") List<String> fields,
                                                 @Context HttpHeaders headers) {

        InternshipProgressDetailsDao dao = new InternshipProgressDetailsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String progressDetails = dao.getProgressDetailsById(id, returnTypes.getRdfFormat());
            return Response.ok(progressDetails, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve progress details for id: " + id, e);
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/")
    public Response createApplication(@FormParam("internshipId") String internshipId,
                                      @FormParam("studentId") String studentId,
                                      @FormParam("teacherId") String teacherId,
                                      @FormParam("motivation") String motivation,
                                      @Context UriInfo uriInfo) {

        InternshipProgressDetails progress = new InternshipProgressDetails();

        String id = RandomStringUtils.randomAlphanumeric(Constants.ID_LENGTH);
        progress.setId(id);

        Student student = new Student();
        student.setId(studentId);
        progress.setStudent(student);

        Internship i = new Internship();
        i.setId(internshipId);
        progress.setInternship(i);

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        progress.setTeacher(teacher);


        InternshipProgressDetailsDao dao = new InternshipProgressDetailsDao();

        try {
            dao.createProgressDetails(progress);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
            return Response.created(uri)
                    .build();

        } catch (StardogException e) {
            throw new InternalServerErrorException("Could not create progress", e);
        }

    }

}
