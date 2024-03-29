package ro.infoiasi.wad.sesi.service.resources;

import com.complexible.stardog.StardogException;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.rdf.dao.CompaniesDao;
import ro.infoiasi.wad.sesi.service.authentication.DBUser;
import ro.infoiasi.wad.sesi.service.authentication.UsersTable;
import ro.infoiasi.wad.sesi.service.util.MediaTypeConstants;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
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
            if (company == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {

                return Response.ok(company, returnTypes.getMediaType()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


//    @RolesAllowed("company")
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response editCompany(@PathParam("id") String companyId,
            Company updatedCompany) {

        try {
            CompaniesDao dao = new CompaniesDao();
            dao.updateCompany(updatedCompany);
            return Response.ok().build();
        } catch (StardogException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response register(@FormParam("username") String username, @FormParam("password") String password,
                             @FormParam("name") String companyName, @Context UriInfo uriInfo) {
        if (usersTable.addUser(new DBUser(username, password, UserAccountType.COMPANY_ACCOUNT.getDescription()))) {
            try {
                new CompaniesDao().createMinimalCompany(companyName, username);

                URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(username)).build();
                return Response.created(uri)
                        .build();

            } catch (StardogException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
