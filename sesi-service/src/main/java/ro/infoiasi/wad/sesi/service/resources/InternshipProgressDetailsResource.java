package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.*;
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            if (progressDetails == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {

                return Response.ok(progressDetails, returnTypes.getMediaType()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @Consumes("application/x-www-form-urlencoded")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateStatus(@PathParam("id") String appId, @FormParam("status")String newStatus) {

        InternshipProgressDetailsDao dao = new InternshipProgressDetailsDao();
        try {
            dao.updateStatus(appId, StudentInternshipRelation.Status.valueOf(newStatus));
            return Response.ok().build();
        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/feedback")
    @Consumes("application/x-www-form-urlencoded")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateFeedback(@PathParam("id") String appId, @FormParam("feedback")String newFeedback) {

        InternshipProgressDetailsDao dao = new InternshipProgressDetailsDao();
        try {
            dao.updateFeedback(appId, newFeedback);
            return Response.ok().build();
        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/")
    public Response createProgressDetails(@FormParam("internshipId") String internshipId,
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
                    .entity(progress)
                    .type(MediaType.APPLICATION_XML_TYPE)
                    .build();

        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

}
