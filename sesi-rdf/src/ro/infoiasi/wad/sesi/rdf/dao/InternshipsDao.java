package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.OWL;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.City;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

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
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery,"application", "sesiUrl");

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
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "progressDetails", "sesiUrl");

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
            URI internshipClass = Values.uri(SESI_SCHEMA_NS, INTERNSHIP_CLASS);
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

            adder.statement(newInternship, name, Values.literal(internship.getName()));
            adder.statement(newInternship, description, Values.literal(internship.getDescription()));
            adder.statement(newInternship, startDate, valueFactory.createLiteral(internship.getStartDate()));
            adder.statement(newInternship, endDate, valueFactory.createLiteral(internship.getEndDate()));

            //adding the city
            City internshipCity = internship.getCity();
            URI cityResource = Values.uri(internshipCity.getOntologyUri().toString());

            adder.statement(cityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(cityResource, RDF.TYPE, Values.uri(FREEBASE_NS, CITY_CLASS));
            adder.statement(cityResource, RDFS.LABEL, Values.literal(internshipCity.getName()));
            adder.statement(cityResource, RDFS.SEEALSO, Values.uri(internshipCity.getInfoUrl().toString()));

            adder.statement(newInternship, city, cityResource);

            // adding other internship specific properties - TODO
            URI publishedByCompany = Values.uri(SESI_SCHEMA_NS, PUBLISHED_BY_PROP);
            adder.statement(newInternship, publishedByCompany, Values.uri(SESI_OBJECTS_NS, internship.getCompanyId()));


            con.commit();
            return internship.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }


    public static void main(String[] args) throws Exception {
        InternshipsDao dao = new InternshipsDao();
        System.out.println(OWL.INDIVIDUAL);

    }

    @Override
    public String getOntClassName() {
        return INTERNSHIP_CLASS;
    }
}
