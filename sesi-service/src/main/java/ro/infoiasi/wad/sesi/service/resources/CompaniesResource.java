package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.rdf.dao.CompaniesDao;
import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@PermitAll
@Path("/companies")
public class CompaniesResource {

    private static final UsersTable usersTable = new UsersTable();

    static {
        usersTable.createTable();
    }


    @GET
    @Path("/{id}")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getCompany(@PathParam("id") String companyId,
                               @QueryParam("fields") List<String> fields,
                               @Context HttpHeaders headers) {

        CompaniesDao dao = new CompaniesDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String company = dao.getCompany(companyId, returnTypes.getRdfFormat());
            return Response.ok(company, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve company with id " + companyId, e);
        }
    }

    @GET
    @Path("/")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllCompanies(@PathParam("id") String companyId,
                                    @QueryParam("q") String searchParam,
                                    @QueryParam("fields") List<String> fields,
                                    @Context HttpHeaders headers) {

        CompaniesDao dao = new CompaniesDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String allCompanies = dao.getAllCompanies(returnTypes.getRdfFormat());
            return Response.ok(allCompanies, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve companies", e);
        }
    }

    @GET
    @Path("/{id}/internships")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllCompanyInternships(@PathParam("id") String companyId,
                                             @QueryParam("fields") List<String> fields,
                                             @Context HttpHeaders headers) {

        CompaniesDao dao = new CompaniesDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String companiesInternships = dao.getAllCompanyInternships(companyId, returnTypes.getRdfFormat());
            return Response.ok(companiesInternships, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve companies internships", e);
        }
    }

    @GET
    @Path("/{id}/applications")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllCompanyApplications(@PathParam("id") String companyId,
                                              @QueryParam("fields") List<String> fields,
                                              @Context HttpHeaders headers) {

        CompaniesDao dao = new CompaniesDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String companyApplications = dao.getAllCompanyApplications(companyId, returnTypes.getRdfFormat());
            return Response.ok(companyApplications, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve company applications", e);
        }
    }

    @GET
    @Path("/{id}/internshipProgressDetails")
    @Produces({MediaTypeConstants.JSON_LD_STRING, MediaTypeConstants.RDFXML_STRING, MediaTypeConstants.TURTLE_STRING})
    public Response getAllCompanyInternshipProgressDetails(@PathParam("id") String companyId,
                                                           @QueryParam("fields") List<String> fields,
                                                           @Context HttpHeaders headers) {

        CompaniesDao dao = new CompaniesDao();
        try {
            List<MediaType> acceptableMediaTypes = headers.getAcceptableMediaTypes();
            MediaTypeConstants.MediaTypeAndRdfFormat<RDFFormat> returnTypes = MediaTypeConstants.getBestRdfReturnTypes(acceptableMediaTypes);

            String companyInternshipProgressDetails = dao.getAllCompanyInternshipProgressDetails(companyId, returnTypes.getRdfFormat());
            return Response.ok(companyInternshipProgressDetails, returnTypes.getMediaType()).build();
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not retrieve company internship progress details", e);
        }
    }


    @RolesAllowed("company")
    @PUT
    @Path("id")
    @Produces(MediaType.APPLICATION_XML)
    public Response editCompany(@PathParam("id") String companyId) {
        return null;
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(@FormParam("username") String username, @FormParam("password") String password,
                             @FormParam("name") String companyName) {
        if (usersTable.addUser(new DBUser(username, password, UserAccountType.COMPANY_ACCOUNT.getDescription()))) {
            try {
                new CompaniesDao().createMinimalCompany(companyName, username);
                return Response.ok().build();

            } catch (StardogException e) {
                throw new InternalServerErrorException(e.getMessage());
            }
        } else {

            return Response.status(Response.Status.FORBIDDEN).build();
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
