package ro.infoiasi.wad.sesi.server.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.rpc.OntologyExtraInfoService;
import ro.infoiasi.wad.sesi.client.rpc.OntologyExtraInfoServiceAsync;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;
import ro.infoiasi.wad.sesi.resources.SesiResources;
import ro.infoiasi.wad.sesi.server.serializers.InternshipDeserializer;
import ro.infoiasi.wad.sesi.server.serializers.OntologyExtraInfoDeserializer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.StringReader;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.DEFAULT_ACCEPT_RDF_TYPE;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.DEFAULT_JENA_LANG;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.SESI_BASE_URL;


public class OntologyExtraInfoServiceImpl extends RemoteServiceServlet implements OntologyExtraInfoService {
    @Override
    public OntologyExtraInfo get(String ontologyClassName, String internshipId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path("internships")
                .path(internshipId)
                .path("city");

        Invocation invocation = target.request()
                .accept(DEFAULT_ACCEPT_RDF_TYPE)
                .buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        OntologyExtraInfo extraInfo = new OntologyExtraInfoDeserializer().deserialize(m, internshipId);

        client.close();
        return extraInfo;
    }
}