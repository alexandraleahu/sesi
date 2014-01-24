package ro.infoiasi.wad.sesi.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsService;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.server.serializers.InternshipDeserializer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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

        String rdfAnswer = invocation.invoke()
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
    public List<Internship> getAllinternshipsByCategory(Internship.Category category) {
        return null;
    }


}