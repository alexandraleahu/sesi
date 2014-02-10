package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.Student;
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
            throw new InternalServerErrorException("Could not retrieve applications", e);
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
            return Response.ok(application, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve application with id:" + applicationId, e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
            dao.createApplication(application);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
            return Response.created(uri)
                    .build();

        } catch (StardogException e) {
            throw new InternalServerErrorException("Could not create application", e);
        }

    }

}
