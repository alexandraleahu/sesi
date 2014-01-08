package ro.infoiasi.wad.sesi.service.resources;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import ro.infoiasi.wad.sesi.core.model.Company;

@PermitAll
@Path("/companies")
public class CompaniesResource {

    private static Map<Integer, Company> companies = new HashMap<Integer, Company>();

    @Context
    private Context context;

    @GET
    @Path("/{id: \\d+}")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<Company> getCompany(@PathParam("id") Integer companyId)
            throws URISyntaxException {
        if(companies.containsKey(companyId)) {
            return new JAXBElement<Company>(new QName("company"), Company.class, companies.get(companyId));
        }
        throw new NotFoundException();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<List<Company>> getCompanies(@PathParam("id") Integer companyId,
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
    @Path("id: \\d+")
    @Produces(MediaType.APPLICATION_XML)
    public Response editCompany(@PathParam("id") Integer companyId, JAXBElement<Company> company) {
        if (companies.containsKey(companyId)) {
            companies.put(companyId, company.getValue());
            return Response.status(Status.ACCEPTED).build();
        }
        throw new NotFoundException();
    }

    @RolesAllowed("company")
    @POST
    @Path("id: \\d+")
    @Produces(MediaType.APPLICATION_XML)
    public Response addCompany(@PathParam("id") Integer companyId, JAXBElement<Company> company) {
        if (!companies.containsKey(companyId)) {
            companies.put(companyId, company.getValue());
            return Response.status(Status.ACCEPTED).build();
        }
        return Response.status(Status.NOT_ACCEPTABLE).build();
    }
}
