package ro.infoiasi.wad.sesi.service.resources;

import ro.infoiasi.wad.sesi.core.model.Company;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.net.URISyntaxException;
import java.util.*;

@PermitAll
@Path("/companies")
public class CompaniesResource {

    private static Map<String, Company> companies = new HashMap<>();

    @Context
    private Context context;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<Company> getCompany(@PathParam("id") String companyId)
            throws URISyntaxException {
        if(companies.containsKey(companyId)) {
            return new JAXBElement<Company>(new QName("company"), Company.class, companies.get(companyId));
        }
        throw new NotFoundException();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<List<Company>> getCompanies(@PathParam("id") String companyId,
            @QueryParam("hasAvailableInternships") Boolean hasAvailableInternships)
            throws URISyntaxException {
        Collection<Company> companies = this.companies.values();
        List<Company> returnedCompanies = new LinkedList<Company>();
        for (Company company : companies) {
            if (hasAvailableInternships != null) {
                if (hasAvailableInternships.equals(company.getAvailableInternships().size() > 0)) {
                    returnedCompanies.add(company);
                }
            } else {
                returnedCompanies.add(company);
            }
        }
        Class<List<Company>> c = (Class<List<Company>>) returnedCompanies.getClass();
        return new JAXBElement<List<Company>>(new QName("companies"), c, returnedCompanies);
    }

    @RolesAllowed("company")
    @PUT
    @Path("id")
    @Produces(MediaType.APPLICATION_XML)
    public Response editCompany(@PathParam("id") String companyId, JAXBElement<Company> company) {
        if (companies.containsKey(companyId)) {
            companies.put(companyId, company.getValue());
            return Response.status(Status.ACCEPTED).build();
        }
        throw new NotFoundException();
    }

    @RolesAllowed("company")
    @POST
    @Path("id")
    @Produces(MediaType.APPLICATION_XML)
    public Response addCompany(@PathParam("id") String companyId, JAXBElement<Company> company) {
        if (!companies.containsKey(companyId)) {
            companies.put(companyId, company.getValue());
            return Response.status(Status.ACCEPTED).build();
        }
        return Response.status(Status.NOT_ACCEPTABLE).build();
    }
}
