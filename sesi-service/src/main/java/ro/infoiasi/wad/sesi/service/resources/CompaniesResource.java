package ro.infoiasi.wad.sesi.service.resources;

import ro.infoiasi.wad.sesi.core.model.Company;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

@PermitAll
@Path("/companies")
public class CompaniesResource {


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getCompany(@PathParam("id") String companyId) {
            return null;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public Response getCompanies(@PathParam("id") String companyId,
            @QueryParam("hasAvailableInternships") Boolean hasAvailableInternships) {
            return null;
    }

    @RolesAllowed("company")
    @PUT
    @Path("id")
    @Produces(MediaType.APPLICATION_XML)
    public Response editCompany(@PathParam("id") String companyId, JAXBElement<Company> company) {
        return null;
    }

    @RolesAllowed("company")
    @POST
    @Path("id")
    @Produces(MediaType.APPLICATION_XML)
    public Response addCompany(@PathParam("id") String companyId, JAXBElement<Company> company) {
       return null;
    }
}
