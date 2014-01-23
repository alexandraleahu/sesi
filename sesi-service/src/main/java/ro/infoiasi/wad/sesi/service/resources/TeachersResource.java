package ro.infoiasi.wad.sesi.service.resources;

import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.dao.CompanyDao;
import ro.infoiasi.wad.sesi.rdf.dao.TeachersDao;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/teachers")
public class TeachersResource {
    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getTeachers(@QueryParam("fields") List<String> fields,
                                @Context HttpHeaders headers) {

        TeachersDao dao = new TeachersDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String allTeachers = dao.getAllTeachers((RDFFormat) returnTypes.getRdfFormat());
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
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String teacher = dao.getTeacherById(teacherId, (RDFFormat) returnTypes.getRdfFormat());
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
            MediaTypeConstants.MediaTypeAndRdfFormat returnTypes = MediaTypeConstants.getBestReturnTypes(acceptableMediaTypes);

            String teacher = dao.getAllInternshipProgressDetails(teacherId, (RDFFormat) returnTypes.getRdfFormat());
            return Response.ok(teacher, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve teacher internship progress details with sid " + teacherId, e);
        }
    }
}
