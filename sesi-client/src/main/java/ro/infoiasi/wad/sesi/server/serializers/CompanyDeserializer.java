package ro.infoiasi.wad.sesi.server.serializers;

import com.hp.hpl.jena.ontology.OntModel;
import ro.infoiasi.wad.sesi.core.model.Company;

public class CompanyDeserializer implements ResourceDeserializer<Company> {
    @Override
    public Company deserialize(OntModel m, String resourceId) {
        return null;
    }
}
