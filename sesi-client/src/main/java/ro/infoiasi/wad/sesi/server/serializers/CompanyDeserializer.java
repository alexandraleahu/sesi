package ro.infoiasi.wad.sesi.server.serializers;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import ro.infoiasi.wad.sesi.core.model.Company;

import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class CompanyDeserializer implements ResourceDeserializer<Company> {
    @Override
    public Company deserialize(OntModel m, String id) {
        Company company = new Company();
        company.setId(id);

        Resource companyResource = m.getOntResource(SESI_OBJECTS_NS + id);
        // name
        Statement statement = m.getProperty(companyResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));
        company.setName(statement.getLiteral().getString());

        //description
        statement = m.getProperty(companyResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, DESCRIPTION_PROP));
        company.setDescription(statement.getLiteral().getString());

        //site url
        statement = m.getProperty(companyResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, SITE_URL_PROP));
        company.setSiteUrl(statement.getLiteral().getString());

        //is active
        statement = m.getProperty(companyResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, IS_ACTIVE_PROP));
        company.setActive(statement.getLiteral().getBoolean());

        return company;
    }

    public List<Company> deserialize(OntModel m) {
        List<Company> companies = Lists.newArrayList();

        ResIterator resIterator = m.listResourcesWithProperty(ResourceFactory.createProperty(SESI_SCHEMA_NS, ID_PROP));
        while (resIterator.hasNext()) {
            Resource resource = resIterator.nextResource();
            String[] parts = resource.getURI().split("/");
            companies.add(deserialize(m, parts[parts.length - 1]));
        }
        return companies;
    }
}
