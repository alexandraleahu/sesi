package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class ApplicationsDao implements Dao {
    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getApplicationById(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?a where {?a rdf:type sesiSchema:InternshipApplication ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getAllApplications(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?a where {?a rdf:type sesiSchema:InternshipApplication .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String createApplication(InternshipApplication application) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            Resource newApplication = Values.uri(SESI_OBJECTS_NS, application.getId());
            URI applicationClass = Values.uri(SESI_SCHEMA_NS, getOntClassName());
            ValueFactoryImpl valueFactory = ValueFactoryImpl.getInstance();

            con.begin();

            Adder adder = con.add();

            // adding the class and the owl:namedIndividual type
            adder.statement(newApplication, RDF.TYPE, applicationClass);
            adder.statement(newApplication, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            // then, the id and sesiUrl
            URI ID = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI sesiUrl = Values.uri(SESI_SCHEMA_NS, SESI_URL_PROP);

            adder.statement(newApplication, ID, Values.literal(application.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newApplication, sesiUrl, Values.literal(application.getRelativeUri()));

            // adding the candidate and the internship
            URI candidate = Values.uri(SESI_SCHEMA_NS, CANDIDATE_PROP);
            URI internship = Values.uri(SESI_SCHEMA_NS, APPLICATION_INTERNSHIP_PROP);

            adder.statement(newApplication, candidate, Values.uri(SESI_OBJECTS_NS, application.getStudent().getId()));
            adder.statement(newApplication, internship, Values.uri(SESI_OBJECTS_NS, application.getInternship().getId()));

            // adding the status and the feedback and the motivation
            URI status = Values.uri(SESI_SCHEMA_NS, STATUS_PROP);
            URI feedback = Values.uri(SESI_SCHEMA_NS, FEEDBACK_PROP);
            URI motivation = Values.uri(SESI_SCHEMA_NS, MOTIVATION_PROP);
            URI publishedAt = Values.uri(SESI_SCHEMA_NS, PUBLISHED_AT_PROP);

            // we set the status initially to pending, when the application is first submitted
            adder.statement(newApplication, status, Values.uri(SESI_SCHEMA_NS, InternshipApplication.Status.pending.toString()));
            adder.statement(newApplication, feedback, Values.literal(INITIAL_FEEDBACK, StardogValueFactory.XSD.STRING));
            adder.statement(newApplication, motivation, Values.literal(application.getMotivation(), StardogValueFactory.XSD.STRING));
            adder.statement(newApplication, publishedAt, valueFactory.createLiteral(application.getPublishedAt()));
            con.commit();

            return application.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public void updateStatus(String applicationId, InternshipApplication.Status newStatus) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            URI application = Values.uri(SESI_OBJECTS_NS, applicationId);
            URI hasStatus = Values.uri(SESI_SCHEMA_NS, STATUS_PROP);
            con.begin();
            // removing the old statement and inserting the new one
            con.remove()
               .statements(application, hasStatus, null);
            con.add()
                .statement(application, hasStatus, Values.uri(SESI_SCHEMA_NS, newStatus.toString()));
            con.commit();

        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public void updateFeedback(String applicationId, String newFeedback) throws StardogException {

            ReasoningConnection con = connectionPool.getConnection();

            try {

                URI application = Values.uri(SESI_OBJECTS_NS, applicationId);
                URI hasFeedback = Values.uri(SESI_SCHEMA_NS, FEEDBACK_PROP);
                con.begin();

                // removing the old statement and inserting the new one
                con.remove()
                   .statements(application, hasFeedback, null);

                con.add()
                   .statement(application, hasFeedback, Values.literal(newFeedback, StardogValueFactory.XSD.STRING));

                con.commit();

            } finally {
                connectionPool.releaseConnection(con);
            }


    }

    public void deleteApplication(String applicationId) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();

        try {
            URI applicationUri =
                    Values.uri(SESI_OBJECTS_NS + applicationId);

            // deleting an individual means deleting all statements that have the individual as a subject or as an object
            con.begin();
            con.remove()
                    .statements(applicationUri, null, null)
                    .statements(null, null, applicationUri);
            con.commit();
        } finally {
            connectionPool.releaseConnection(con);
        }

    }

    @Override
    public String getOntClassName() {
        return APPLICATION_CLASS;
    }

    public static void main(String[] args) throws Exception {
        ApplicationsDao dao = new ApplicationsDao();

//        InternshipApplication application = new InternshipApplication();
//        String id = RandomStringUtils.randomAlphanumeric(ID_LENGTH);
//        application.setId(id);
//        application.setInternship("003");
//        application.setStudent("dm");
//
//        System.out.println(dao.createProgressDetails(application));


        dao.updateFeedback("7QP3", "bla bla");
        System.out.println(dao.getApplicationById("7QP3", RDFFormat.TURTLE));
//        dao.deleteApplication("7QP3");



    }
}
