package ro.infoiasi.wad.sesi.server.serializers;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsService;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsServiceAsync;
import ro.infoiasi.wad.sesi.client.rpc.OntologyExtraInfoService;
import ro.infoiasi.wad.sesi.client.rpc.OntologyExtraInfoServiceAsync;
import ro.infoiasi.wad.sesi.core.model.City;
import ro.infoiasi.wad.sesi.core.model.Currency;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;
import ro.infoiasi.wad.sesi.resources.SesiResources;
import ro.infoiasi.wad.sesi.server.rpc.OntologyExtraInfoServiceImpl;

import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class InternshipDeserializer implements ResourceDeserializer<Internship> {

    @Override
    public Internship deserialize(OntModel m, String internshipId) {
        Internship internship = new Internship();
        internship.setId(internshipId);

        Resource internshipResource = m.getOntResource(SESI_OBJECTS_NS + internshipId);
        // name
        Statement statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));

        internship.setName(statement.getLiteral().getString());

        // description
        statement = m.getProperty(internshipResource,
                        ResourceFactory.createProperty(SESI_SCHEMA_NS, DESCRIPTION_PROP));

        internship.setDescription(statement.getLiteral().getString());

        // start date
        statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(FREEBASE_NS, START_DATE_PROP));

        XSDDateTime startDate = (XSDDateTime) (statement.getLiteral().getValue());
        internship.setStartDate(startDate.asCalendar().getTime());

        // end date
        statement = m.getProperty(internshipResource,
                      ResourceFactory.createProperty(FREEBASE_NS, END_DATE_PROP));

        XSDDateTime endDate = (XSDDateTime) (statement.getLiteral().getValue());
        internship.setEndDate(endDate.asCalendar().getTime());

        // openings
        statement = m.getProperty(internshipResource,
                                  ResourceFactory.createProperty(SESI_SCHEMA_NS, OPENINGS_PROP));

        internship.setOpenings(statement.getLiteral().getInt());

        // offersRelocation
        statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(SESI_SCHEMA_NS, RELOCATION_PROP));

        internship.setOfferingRelocation(statement.getLiteral().getBoolean());

        // category
        statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(SESI_SCHEMA_NS, CATEGORY_PROP));

        Resource category = statement.getObject().asResource();
        internship.setCategory(Internship.Category.valueOf(category.getLocalName()));

        // salary
        statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(SESI_SCHEMA_NS, SALARY_VALUE_PROP));

        if (statement != null) {
            internship.setSalaryValue(statement.getLiteral().getDouble());

            // salary currency - just taking the name
            statement = m.getProperty(internshipResource,
                    ResourceFactory.createProperty(SESI_SCHEMA_NS, CURRENCY_PROP));

            Currency currency = new Currency();
            currency.setName(statement.getObject().asResource().getLocalName());
            internship.setSalaryCurrency(currency);
        }

        //city
        City city = (City) new OntologyExtraInfoServiceImpl().get("A", internshipId);
        internship.setCity(city);
        // preferred general skills
        List<String> preferredGeneralSkills = Lists.newArrayList();
        StmtIterator stmtIterator = internshipResource.listProperties(ResourceFactory.
                                                    createProperty(SESI_SCHEMA_NS, PREFERRED_GENERAL_PROP));

        while (stmtIterator.hasNext()) {
            Statement nextStatement = stmtIterator.nextStatement();
            String generalSkill = nextStatement.getLiteral().getString();

            preferredGeneralSkills.add(generalSkill);
        }
        internship.setPreferredGeneralSkills(preferredGeneralSkills);

        // acquired general skills
        List<String> acquiredGeneralSkills = Lists.newArrayList();
        stmtIterator = internshipResource.listProperties(ResourceFactory.
                                                    createProperty(SESI_SCHEMA_NS, ACQUIRED_GENERAL_PROP));

        while (stmtIterator.hasNext()) {
            Statement nextStatement = stmtIterator.nextStatement();
            String generalSkill = nextStatement.getLiteral().getString();

            acquiredGeneralSkills.add(generalSkill);
        }
        internship.setAcquiredGeneralSkills(acquiredGeneralSkills);

        return internship;
    }


}
