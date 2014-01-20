package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static ro.infoiasi.wad.sesi.rdf.util.Constants.*;

public class InternshipsDao implements Dao {

    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getInternshipById(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?i where {?i rdf:type sesiSchema:Internship ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public String getAllInternships(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?i where {?i rdf:type sesiSchema:Internship .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getAllInternshipApplications(String internshipId) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?application ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Internship ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:hasInternshipApplication ?application . ")
                    .append("?application sesiSchema:sesiUrl ?sesiUrl . ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", internshipId);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery,"application", SESI_URL_PROP);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }



    public List<ResourceLinks> getAllInternshipProgressDetails(String internshipId) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                                    .append("select ?progressDetails ?sesiUrl ")
                                    .append("where {")
                                    .append("[] rdf:type sesiSchema:Internship ; ")
                                    .append("sesiSchema:id ?id ; ")
                                    .append("sesiSchema:progressDetails ?progressDetails . ")
                                    .append("?progressDetails sesiSchema:sesiUrl ?sesiUrl . ")
                                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", internshipId);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "progressDetails", SESI_URL_PROP);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public void deleteInternship(String internshipId) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();

        try {
            org.openrdf.model.URI internshipUri =
                    ValueFactoryImpl.getInstance().createURI(SESI_OBJECTS_NS + internshipId);

            // deleting an individual means deleting all statements that have the individual as a subject or as an object
            con.begin();
            con.remove()
               .statements(internshipUri, null, null)
               .statements(null, null, internshipUri);
            con.commit();
        } finally {
            connectionPool.releaseConnection(con);
        }

    }

    // we return the relative URI of the newly created resource for the service to set the Location header
    public String createInternship(Internship internship) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            Resource newInternship = Values.uri(SESI_OBJECTS_NS, internship.getId());
            URI internshipClass = Values.uri(SESI_SCHEMA_NS, getOntClassName());
            ValueFactoryImpl valueFactory = ValueFactoryImpl.getInstance();

            con.begin();

            Adder adder = con.add();

            // adding the class and the owl:namedIndividual type
            adder.statement(newInternship, RDF.TYPE, internshipClass);
            adder.statement(newInternship, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            //adding the event specific properties
            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);
            URI description = Values.uri(SESI_SCHEMA_NS, DESCRIPTION_PROP);
            URI startDate = Values.uri(SESI_SCHEMA_NS, START_DATE_PROP);
            URI endDate = Values.uri(SESI_SCHEMA_NS, END_DATE_PROP);
            URI city = Values.uri(SESI_SCHEMA_NS, CITY_PROP);


            adder.statement(newInternship, name, Values.literal(internship.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(newInternship, RDFS.LABEL, Values.literal(internship.getName()));
            adder.statement(newInternship, description, Values.literal(internship.getDescription(), StardogValueFactory.XSD.STRING));
            adder.statement(newInternship, startDate, valueFactory.createLiteral(internship.getStartDate()));
            adder.statement(newInternship, endDate, valueFactory.createLiteral(internship.getEndDate()));

            //adding the city
            City internshipCity = internship.getCity();
            URI cityResource = Values.uri(internshipCity.getOntologyUri());

            adder.statement(cityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(cityResource, RDF.TYPE, Values.uri(FREEBASE_NS, CITY_CLASS));
            adder.statement(cityResource, RDFS.LABEL, Values.literal(internshipCity.getName()));
            adder.statement(cityResource, RDFS.SEEALSO, Values.literal(internshipCity.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

            adder.statement(newInternship, city, cityResource);

            // adding other internship specific properties
            URI ID = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI publishedByCompany = Values.uri(SESI_SCHEMA_NS, PUBLISHED_BY_PROP);
            URI offersRelocation = Values.uri(SESI_SCHEMA_NS, RELOCATION_PROP);
            URI openingsCount = Values.uri(SESI_SCHEMA_NS, OPENINGS_PROP);
            URI category = Values.uri(SESI_SCHEMA_NS, CATEGORY_PROP);
            URI sesiUrl = Values.uri(SESI_SCHEMA_NS, SESI_URL_PROP);

            adder.statement(newInternship, publishedByCompany, Values.uri(SESI_OBJECTS_NS, internship.getCompanyId()));
            adder.statement(newInternship, ID, Values.literal(internship.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newInternship, offersRelocation, Values.literal(internship.isOfferingRelocation()));
            adder.statement(newInternship, openingsCount, Values.literal(internship.getOpenings()));
            adder.statement(newInternship, category, Values.uri(SESI_OBJECTS_NS, internship.getCategory().toString()));
            adder.statement(newInternship, sesiUrl, Values.literal(internship.getRelativeUri()));

            // adding another salary
            Salary internshipSalary = internship.getSalary();
            if (internshipSalary != null) {

                URI salaryResource = Values.uri(SESI_OBJECTS_NS, RandomStringUtils.randomAlphanumeric(ID_LENGTH));
                URI hasCurrency = Values.uri(SESI_SCHEMA_NS, CURRENCY_PROP);
                URI numericalValue = Values.uri(SESI_SCHEMA_NS, SALARY_VALUE_PROP);
                // adding the currency
                Currency internshipCurrency = internshipSalary.getCurrency();
                URI currencyResource = Values.uri(internshipCurrency.getOntologyUri());

                adder.statement(currencyResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
                adder.statement(currencyResource, RDF.TYPE, Values.uri(FREEBASE_NS, CURRENCY_CLASS));
                adder.statement(currencyResource, RDFS.LABEL, Values.literal(internshipCurrency.getName()));
                adder.statement(currencyResource, RDFS.SEEALSO, Values.literal(internshipCurrency.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

                // linking the currency resource to the salary resource
                adder.statement(salaryResource, hasCurrency, currencyResource);
                adder.statement(salaryResource, numericalValue, Values.literal(internshipSalary.getNumericalValue()));

                // linking the salary to the internship
                URI offersSalary = Values.uri(SESI_SCHEMA_NS, SALARY_PROP);
                adder.statement(newInternship, offersSalary, salaryResource);
            }
            if (internship.getAcquiredGeneralSkills() != null)  {

                for (String acquiredGeneralSkill : internship.getAcquiredGeneralSkills()) {

                    URI acqGeneralSkillProp = Values.uri(SESI_SCHEMA_NS, ACQUIRED_GENERAL_PROP);
                    adder.statement(newInternship, acqGeneralSkillProp, Values.literal(acquiredGeneralSkill, StardogValueFactory.XSD.STRING));
                }
            }

            if (internship.getPreferredGeneralSkills() != null) {

                for (String preferredGeneralSkill : internship.getPreferredGeneralSkills()) {

                    URI prefGeneralSkillProp = Values.uri(SESI_SCHEMA_NS, PREFERRED_GENERAL_PROP);
                    adder.statement(newInternship, prefGeneralSkillProp, Values.literal(preferredGeneralSkill, StardogValueFactory.XSD.STRING));
                }
            }

            if (internship.getAcquiredTechnicalSkills() != null) {

                URI acqTechnicalSkillProp =  Values.uri(SESI_SCHEMA_NS, ACQUIRED_TECHNICAL_PROP);
                addTechnicalSkills(internship.getAcquiredTechnicalSkills(), newInternship, adder, acqTechnicalSkillProp);
            }

            if (internship.getPreferredTechnicalSkills() != null) {

                URI prefTechnicalSkillProp =  Values.uri(SESI_SCHEMA_NS, PREFERRED_TECHNICAL_PROP);
                addTechnicalSkills(internship.getPreferredTechnicalSkills(), newInternship, adder, prefTechnicalSkillProp);
            }

            con.commit();
            return internship.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    private void addTechnicalSkills(List<TechnicalSkill> technicalSkills, Resource newInternship, Adder adder, URI technicalSkillProp) throws StardogException {

        for (TechnicalSkill technicalSkill : technicalSkills) {

            // creating the programming language / technology (if it exists, the old triples won't be affected)

            StringBuilder sb = new StringBuilder();
            ProgrammingLanguage programmingLanguage = technicalSkill.getProgrammingLanguage();
            URI technologyUri = null;
            URI languageOrSoftwareUri = null;

            if (programmingLanguage != null) {
                technologyUri = Values.uri(programmingLanguage.getOntologyUri());
                adder.statement(technologyUri, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
                adder.statement(technologyUri, RDF.TYPE, Values.uri(FREEBASE_NS, PROGRAMMING_LANG_CLASS));
                adder.statement(technologyUri, RDFS.LABEL, Values.literal(programmingLanguage.getName()));
                adder.statement(technologyUri, RDFS.SEEALSO, Values.literal(programmingLanguage.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

                languageOrSoftwareUri = Values.uri(SESI_SCHEMA_NS, PROGRAMMING_USED_PROP);
                sb.append(programmingLanguage.getName().replace(' ', '_'));

            } else {
                Technology software = technicalSkill.getTechnology();
                technologyUri = Values.uri(software.getOntologyUri());
                adder.statement(technologyUri, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
                adder.statement(technologyUri, RDF.TYPE, Values.uri(FREEBASE_NS, SOFTWARE_CLASS));
                adder.statement(technologyUri, RDFS.LABEL, Values.literal(software.getName()));
                adder.statement(technologyUri, RDFS.SEEALSO, Values.literal(software.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

                languageOrSoftwareUri = Values.uri(SESI_SCHEMA_NS, TECHNOLOGY_USED_PROP);
                sb.append(software.getName().replace(' ', '_'));

            }
            sb.append(technicalSkill.getLevel().toString());

            URI softwareSkillResource = Values.uri(SESI_OBJECTS_NS, sb.toString());
            URI level = Values.uri(SESI_SCHEMA_NS, technicalSkill.getLevel().toString());
            URI hasLevel = Values.uri(SESI_SCHEMA_NS, LEVEL_PROP);

            // composing the software skill
            adder.statement(softwareSkillResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(softwareSkillResource, RDF.TYPE, Values.uri(SESI_SCHEMA_NS, SOFTWARE_SKILL_CLASS));

            adder.statement(softwareSkillResource, languageOrSoftwareUri, technologyUri);
            adder.statement(softwareSkillResource, hasLevel, level);

            // adding the software skills to the technicalSkills
            adder.statement(newInternship, technicalSkillProp, softwareSkillResource);
        }
    }


    public static void main(String[] args) throws Exception {
        
        InternshipsDao dao = new InternshipsDao();

//        System.out.println(dao.createInternship(createNewInternship()));
        System.out.println(dao.getInternshipById("tmrS", RDFFormat.TURTLE));

    }

    private static Internship createNewInternship() throws Exception {
        Internship internship = new Internship();
        
        internship.setId(RandomStringUtils.randomAlphanumeric(ID_LENGTH));
        internship.setName("Android internship");
        internship.setDescription("An internship in which students will work on Android applications published on the market");
        internship.setCompanyId("002");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2014, Calendar.JUNE, 19, 9, 0 , 0);
        internship.setStartDate(calendar.getTime());
        calendar.set(2014, Calendar.SEPTEMBER, 19, 9, 0 , 0);
        internship.setEndDate(calendar.getTime());

        internship.setOfferingRelocation(true);
        internship.setOpenings(3);
        internship.setCategory(Internship.Category.Mobile);

        City iasi = new City();

        iasi.setOntologyUri("http://rdf.freebase.com/ns/m.01fhg3");
        iasi.setName("Iasi");
        iasi.setInfoUrl("http://www.freebase.com/m/01fhg3");

        internship.setCity(iasi);

        Currency euro = new Currency();
        euro.setName("EURO");
        euro.setInfoUrl("http://www.freebase.com/m/02l6h");
        euro.setOntologyUri("http://rdf.freebase.com/ns/m.02l6h");

        Salary salary = new Salary();
        salary.setCurrency(euro);
        salary.setNumericalValue(200);

        internship.setSalary(salary);

        internship.setPreferredGeneralSkills(Arrays.asList("Team player", "Motivated", "Hard Working"));
        internship.setAcquiredGeneralSkills(Arrays.asList("Working in a team"));

        ProgrammingLanguage java = new ProgrammingLanguage();
        java.setName("Java");
        java.setInfoUrl("http://www.freebase.com/m/07sbkfb");
        java.setOntologyUri("http://rdf.freebase.com/ns/m.07sbkfb");

        TechnicalSkill javaIntermediate = new TechnicalSkill();
        javaIntermediate.setLevel(KnowledgeLevel.Intermediate);

        javaIntermediate.setProgrammingLanguage(java);

        internship.setPreferredTechnicalSkills(Arrays.asList(javaIntermediate));

        Technology android = new Technology();
        android.setName("Android");
        android.setInfoUrl("http://www.freebase.com/m/02wxtgw");
        android.setOntologyUri("http://rdf.freebase.com/ns/m.02wxtgw");

        TechnicalSkill androidAdvanced = new TechnicalSkill();
        androidAdvanced.setLevel(KnowledgeLevel.Advanced);
        androidAdvanced.setTechnology(android);

        internship.setAcquiredTechnicalSkills(Arrays.asList(androidAdvanced));

        return internship;
    }

    @Override
    public String getOntClassName() {
        return INTERNSHIP_CLASS;
    }
}
