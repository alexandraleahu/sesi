package ro.infoiasi.wad.sesi.server.progressdetails;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.progressdetails.InternshipsProgressDetailsService;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;

public class InternshipsProgressDetailsServiceImpl extends RemoteServiceServlet implements InternshipsProgressDetailsService {
    @Override
    public InternshipProgressDetails getProgressDetailsById(String id) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH)
                .path(id);

        Invocation invocation = target.request()
                .accept(DEFAULT_ACCEPT_RDF_TYPE)
                .buildGet();

        String rdfAnswer = invocation.invoke()
                .readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        InternshipProgressDetails progressDetails = new InternshipProgressDetailsDeserializer().deserialize(m, id);
        client.close();
        return progressDetails;
    }

    @Override
    public List<InternshipProgressDetails> getAllProgressDetails() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}