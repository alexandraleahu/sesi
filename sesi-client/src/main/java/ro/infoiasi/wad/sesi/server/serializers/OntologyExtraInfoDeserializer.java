package ro.infoiasi.wad.sesi.server.serializers;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;

import static ro.infoiasi.wad.sesi.core.util.Constants.NAME_PROP;
import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;

public class OntologyExtraInfoDeserializer implements Deserializer<OntologyExtraInfo> {
    @Override
    public OntologyExtraInfo deserialize(OntModel m, String uri) {
        OntologyExtraInfo ontologyExtraInfo = new OntologyExtraInfo();

        Resource resource = m.getOntResource(uri);

        Statement statement = m.getProperty(resource, ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));
        ontologyExtraInfo.setName(statement.getLiteral().getString());


        return ontologyExtraInfo;
    }
}
