package ro.infoiasi.wad.sesi.server.internships;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.internships.InternshipsService;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.server.applications.InternshipApplicationDeserializer;
import ro.infoiasi.wad.sesi.server.progressdetails.InternshipProgressDetailsDeserializer;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;

public class InternshipsServiceImpl extends RemoteServiceServlet implements InternshipsService {

    @Override
    public Internship getInternshipById(String internshipId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH)
                .path(internshipId);

        Invocation invocation = target.request()
                .accept(DEFAULT_ACCEPT_RDF_TYPE)
                .buildGet();

        Response response = invocation.invoke();
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode())  {
            return null;
        }
        String rdfAnswer = response
                .readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        Internship internship = new InternshipDeserializer().deserialize(m, internshipId);
        client.close();
        return internship;
    }

    @Override
    public int getApplicationsCount(String internshipId) {
        return 0;
    }

    @Override
    public List<Internship> getAllInternshipsByCategory(Internship.Category category) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).queryParam("category", category);
        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<Internship> internships = new InternshipDeserializer().deserialize(m);
        client.close();
        return internships;
    }

    @Override
    public List<Internship> getAllInternships() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);
        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<Internship> internships = new InternshipDeserializer().deserialize(m);
        client.close();
        return internships;
    }

    @Override
    public List<InternshipApplication> getInternshipApplications(String internshipId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(internshipId).path("applications");
        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<InternshipApplication> applications = new InternshipApplicationDeserializer().deserialize(m);
        client.close();
        return applications;
    }

    @Override
    public List<InternshipProgressDetails> getInternshipProgressDetails(String internshipId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(internshipId).path("internshipProgressDetails");
        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<InternshipProgressDetails> details = new InternshipProgressDetailsDeserializer().deserialize(m);
        client.close();
        return details;
    }

    @Override
    public boolean postInternship(Internship internship) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE)
                .post(Entity.entity(internship, MediaType.APPLICATION_XML_TYPE));

        int status = response.getStatus();
        client.close();
        return status == Response.Status.OK.getStatusCode();
    }

    @Override
    public void save(Internship internship) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);
        Entity<Internship> entity = Entity.entity(internship, MediaType.APPLICATION_FORM_URLENCODED);
        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildPost(entity);

        invocation.invoke();
        client.close();
    }


}