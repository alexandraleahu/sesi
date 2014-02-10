package ro.infoiasi.wad.sesi.server.applications;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.applications.InternshipApplicationsService;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;
import ro.infoiasi.wad.sesi.shared.ResourceAlreadyExistsException;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;

public class InternshipApplicationsServiceImpl extends RemoteServiceServlet implements InternshipApplicationsService {
    @Override
    public InternshipApplication getApplicationById(String companyId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(companyId);

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        Response response = invocation.invoke();
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode())  {
            return null;
        }
        String rdfAnswer = response.readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        InternshipApplication application = new InternshipApplicationDeserializer().deserialize(m, companyId);
        client.close();
        return application;
    }

    @Override
    public List<InternshipApplication> getAllApplications() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public InternshipApplication createApplication(String studentId, String internshipId, String motivation) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH);
        Form form = new Form();
        form.param("studentId", studentId);
        form.param("internshipId", internshipId);
        form.param("motivation", motivation);
        Invocation invocation = target.request(MediaType.APPLICATION_XML)
                .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        Response response = invocation.invoke();
        int status = response.getStatus();
        client.close();
        if (status / 100 == 2) {

            return response.readEntity(InternshipApplication.class);
        }  else if
                (status == Response.Status.CONFLICT.getStatusCode()) {

            throw new ResourceAlreadyExistsException("You cannot apply more than once");
        }

        else {
            return null;// there was an internal error
        }
    }

    @Override
    public boolean updateStatus(String appId, StudentInternshipRelation.Status newStatus) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH).path("status");
        Response response = target.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(newStatus.toString(), MediaType.APPLICATION_XML_TYPE));

        int status = response.getStatus();
        client.close();
        return (status/100 == 2);
    }

    @Override
    public boolean updateFeedback(String appId, String newFeedback) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH).path("feedback");
        Response response = target.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(newFeedback.toString(), MediaType.APPLICATION_XML_TYPE));

        int status = response.getStatus();
        client.close();
        return (status/100 == 2);
    }


}