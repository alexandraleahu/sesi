package ro.infoiasi.wad.sesi.server.internships;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import ro.infoiasi.wad.sesi.client.companies.CompaniesService;
import com.hp.hpl.jena.rdf.model.Resource;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.server.companies.CompaniesServiceImpl;
import ro.infoiasi.wad.sesi.server.deserializerinterfaces.ResourceDeserializer;
import ro.infoiasi.wad.sesi.server.util.SparqlService;

import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class InternshipDeserializer implements ResourceDeserializer<Internship> {

    @Override
    public Internship deserialize(OntModel m, String internshipId) {
        SparqlService sparqlService = new SparqlService();
        CompaniesService serviceImpl = new CompaniesServiceImpl();

        Internship internship = new Internship();
        internship.setId(internshipId);

        Resource internshipResource = m.getOntResource(SESI_OBJECTS_NS + internshipId);
        // name
        Statement statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));

        internship.setName(statement.getLiteral().getString());

        // company
        statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(SESI_SCHEMA_NS, PUBLISHED_BY_PROP));

        String companyId = statement.getObject().asResource().getLocalName();

        internship.setCompany(serviceImpl.getCompanyById(companyId));

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

        // published at
        statement = m.getProperty(internshipResource,
                ResourceFactory.createProperty(SESI_SCHEMA_NS, PUBLISHED_AT_PROP));

        XSDDateTime publishedAt = (XSDDateTime) (statement.getLiteral().getValue());
        internship.setPublishedAt(publishedAt.asCalendar().getTime());

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
            statement = m.getProperty(internshipResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, CURRENCY_PROP));

            OntologyExtraInfo currencyExtraInfo = sparqlService.getOntologyExtraInfo(statement.getResource().getURI());
            Currency currency = new Currency();
            currency.setName(currencyExtraInfo.getName());
            currency.setInfoUrl(currencyExtraInfo.getInfoUrl());
            currency.setOntologyUri(currencyExtraInfo.getOntologyUri());
            internship.setSalaryCurrency(currency);
        }

        //city
        statement = m.getProperty(internshipResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, CITY_PROP));
        OntologyExtraInfo extraInfo = sparqlService.getOntologyExtraInfo(statement.getResource().getURI());
        City city = new City();
        city.setInfoUrl(extraInfo.getInfoUrl());
        city.setName(extraInfo.getName());
        city.setOntologyUri(extraInfo.getOntologyUri());
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

        //acquiredTechnicalSkills
        List<TechnicalSkill> acquiredTechnicalSkills = Lists.newArrayList();
        stmtIterator = internshipResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, ACQUIRED_TECHNICAL_PROP));
        while (stmtIterator.hasNext()) {
            Statement nextStatement = stmtIterator.nextStatement();
            TechnicalSkill acquiredSkill = sparqlService.getTechnicalSkill(nextStatement.getResource().getURI());
            if (acquiredSkill != null)
                acquiredTechnicalSkills.add(acquiredSkill);
        }
        internship.setAcquiredTechnicalSkills(acquiredTechnicalSkills);


        //prefferedTechnicalSkill
        List<TechnicalSkill> prefferedTechnicalSkills = Lists.newArrayList();
        stmtIterator = internshipResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, PREFERRED_TECHNICAL_PROP));
        while (stmtIterator.hasNext()) {
            Statement nextStatement = stmtIterator.nextStatement();
            TechnicalSkill preferredSkill = sparqlService.getTechnicalSkill(nextStatement.getResource().getURI());
            if (preferredSkill != null)
                prefferedTechnicalSkills.add(preferredSkill);
        }
        internship.setPreferredTechnicalSkills(prefferedTechnicalSkills);

        return internship;
    }


    public List<Internship> deserialize(OntModel m) {
        List<Internship> internships = Lists.newArrayList();

        ResIterator resIterator = m.listResourcesWithProperty(ResourceFactory.createProperty(SESI_SCHEMA_NS, ID_PROP));
        while (resIterator.hasNext()) {
            Resource resource = resIterator.nextResource();
            String[]parts = resource.getURI().split("/");
            internships.add(deserialize(m, parts[parts.length - 1]));
        }
        return internships;
    }
}
