package ro.infoiasi.wad.sesi.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.rpc.CompaniesService;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.server.serializers.InternshipApplicationDeserializer;
import ro.infoiasi.wad.sesi.server.serializers.CompanyDeserializer;
import ro.infoiasi.wad.sesi.server.serializers.InternshipDeserializer;
import ro.infoiasi.wad.sesi.server.serializers.InternshipProgressDetailsDeserializer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;


public class CompaniesServiceImpl extends RemoteServiceServlet implements CompaniesService {
    @Override
    public Company getCompanyById(String companyId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH)
                .path(companyId);

        Invocation invocation = target.request()
                .accept(DEFAULT_ACCEPT_RDF_TYPE)
                .buildGet();

        String rdfAnswer = invocation.invoke()
                .readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        Company company = new CompanyDeserializer().deserialize(m, companyId);
        client.close();
        return company;
    }

    @Override
    public List<InternshipApplication> getCompanyApplications(String companyId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(companyId).path("applications");

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);
        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<InternshipApplication> applications = new InternshipApplicationDeserializer().deserialize(m);
        client.close();
        return applications;
    }

    @Override
    public List<Company> getAllCompanies() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<Company> companies = new CompanyDeserializer().deserialize(m);
        client.close();
        return companies;
    }

    @Override
    public List<Internship> getCompanyInternships(String companyId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(companyId).path("internships");

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<Internship> internships = new InternshipDeserializer().deserialize(m);
        client.close();
        return internships;
    }

    @Override
    public List<InternshipProgressDetails> getCompanyProgressDetails(String companyId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(companyId).path("internshipProgressDetails");

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<InternshipProgressDetails> details = new InternshipProgressDetailsDeserializer().deserialize(m);
        client.close();
        return details;
    }
}