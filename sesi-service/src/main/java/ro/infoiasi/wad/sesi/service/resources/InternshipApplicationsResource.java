package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;
import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.rdf.dao.InternshipApplicationsDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Date;
import java.util.List;

@Path("/applications")
public class InternshipApplicationsResource {

    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllApplications(@QueryParam("fields") List<String> fields,
                                       @Context HttpHeaders headers) {

        InternshipApplicationsDao dao = new InternshipApplicationsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String allApplications = dao.getAllApplications(returnTypes.getRdfFormat());
            return Response.ok(allApplications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getApplicationById(@PathParam("id") String applicationId,
                                       @QueryParam("fields") List<String> fields,
                                       @Context HttpHeaders headers) {

        InternshipApplicationsDao dao = new InternshipApplicationsDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String application = dao.getApplicationById(applicationId, returnTypes.getRdfFormat());
            if (application == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {

                return Response.ok(application, returnTypes.getMediaType()).build();
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

        InternshipApplicationsDao dao = new InternshipApplicationsDao();
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

        InternshipApplicationsDao dao = new InternshipApplicationsDao();
        try {
            dao.updateFeedback(appId, newFeedback);
            return Response.ok().build();
        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/")
    public Response createApplication(@FormParam("internshipId") String internshipId,
                                      @FormParam("studentId") String studentId,
                                      @FormParam("motivation") String motivation,
                                      @Context UriInfo uriInfo) {

        InternshipApplication application = new InternshipApplication();

        String id = RandomStringUtils.randomAlphanumeric(Constants.ID_LENGTH);
        application.setId(id);
        application.setPublishedAt(new Date());

        Student student = new Student();
        student.setId(studentId);
        application.setStudent(student);

        Internship i = new Internship();
        i.setId(internshipId);
        application.setInternship(i);


        application.setMotivation(motivation);

        InternshipApplicationsDao dao = new InternshipApplicationsDao();

        try {
            if (dao.applicationExists(studentId, internshipId)) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            dao.createApplication(application);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
            return Response.created(uri)
                    .entity(id)
                    .build();

        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

}
