package ro.infoiasi.wad.sesi.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.rpc.InternshipApplicationService;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.server.serializers.InternshipApplicationDeserializer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;

public class InternshipApplicationServiceImpl extends RemoteServiceServlet implements InternshipApplicationService {
    @Override
    public InternshipApplication getApplicationById(String companyId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(companyId);

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

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
}