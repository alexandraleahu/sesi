package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import static ro.infoiasi.wad.sesi.rdf.util.Constants.*;

public class ProgressDetailsDao implements Dao {


    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getProgressDetailsById(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?p where {?p rdf:type sesiSchema:InternshipProgressDetails ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getAllProgressDetails(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?a where {?a rdf:type sesiSchema:InternshipProgressDetails .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String createProgressDetails(InternshipProgressDetails progressDetails) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            Resource newApplication = Values.uri(SESI_OBJECTS_NS, progressDetails.getId());
            URI progressDetailsClass = Values.uri(SESI_SCHEMA_NS, getOntClassName());

            con.begin();

            Adder adder = con.add();

            // adding the class and the owl:namedIndividual type
            adder.statement(newApplication, RDF.TYPE, progressDetailsClass);
            adder.statement(newApplication, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            // then, the id and sesiUrl
            URI ID = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI sesiUrl = Values.uri(SESI_SCHEMA_NS, SESI_URL_PROP);

            adder.statement(newApplication, ID, Values.literal(progressDetails.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newApplication, sesiUrl, Values.literal(progressDetails.getRelativeUri()));

            // adding the attendeeStudent and the internship and the teacher
            URI attendeeStudent = Values.uri(SESI_SCHEMA_NS, ATTENDEE_STUDENT_PROP);
            URI internship = Values.uri(SESI_SCHEMA_NS, ATTENDED_INTERNSHIP_PROP);
            URI mentorTeacher = Values.uri(SESI_SCHEMA_NS, TEACHER_MENTOR_PROP);

            adder.statement(newApplication, attendeeStudent, Values.uri(SESI_OBJECTS_NS, progressDetails.getStudentId()));
            adder.statement(newApplication, internship, Values.uri(SESI_OBJECTS_NS, progressDetails.getInternshipId()));
            adder.statement(newApplication, mentorTeacher, Values.uri(SESI_OBJECTS_NS, progressDetails.getTeacherId()));

            // adding the status and the feedback
            URI status = Values.uri(SESI_SCHEMA_NS, STATUS_PROP);
            URI feedback = Values.uri(SESI_SCHEMA_NS, FEEDBACK_PROP);

            // we set the status initially to inProgress, when the progressDetails is first submitted
            adder.statement(newApplication, status, Values.uri(SESI_SCHEMA_NS, InternshipProgressDetails.Status.inProgress.toString()));
            adder.statement(newApplication, feedback, Values.literal(INITIAL_ATTENDANCE_FEEDBACK, StardogValueFactory.XSD.STRING));
            con.commit();

            return progressDetails.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public void updateStatus(String progressDetailsId, InternshipProgressDetails.Status newStatus) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            URI progressDetails = Values.uri(SESI_OBJECTS_NS, progressDetailsId);
            URI hasStatus = Values.uri(SESI_SCHEMA_NS, STATUS_PROP);
            con.begin();
            // removing the old statement and inserting the new one
            con.remove()
                    .statements(progressDetails, hasStatus, null);
            con.add()
                    .statement(progressDetails, hasStatus, Values.uri(SESI_SCHEMA_NS, newStatus.toString()));
            con.commit();

        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public void updateFeedback(String progressDetailsId, String newFeedback) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            URI progressDetails = Values.uri(SESI_OBJECTS_NS, progressDetailsId);
            URI hasFeedback = Values.uri(SESI_SCHEMA_NS, FEEDBACK_PROP);
            con.begin();

            // removing the old statement and inserting the new one
            con.remove()
                    .statements(progressDetails, hasFeedback, null);

            con.add()
                    .statement(progressDetails, hasFeedback, Values.literal(newFeedback, StardogValueFactory.XSD.STRING));

            con.commit();

        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public void deleteProgressDetails(String progressDetailsId) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();

        try {
            URI progressDetailsUri =
                    Values.uri(SESI_OBJECTS_NS + progressDetailsId);

            // deleting an individual means deleting all statements that have the individual as a subject or as an object
            con.begin();
            con.remove()
                    .statements(progressDetailsUri, null, null)
                    .statements(null, null, progressDetailsUri);
            con.commit();
        } finally {
            connectionPool.releaseConnection(con);
        }

    }

    @Override
    public String getOntClassName() {
        return PROGRESS_DETAILS_CLASS;
    }

    public static void main(String[] args) throws Exception {

        ProgressDetailsDao dao = new ProgressDetailsDao();
//        InternshipProgressDetails details = new InternshipProgressDetails();
//
//        String id = RandomStringUtils.randomAlphanumeric(ID_LENGTH);
//        details.setId(id);
//
//        details.setInternshipId("003");
//        details.setStudentId("001");
//        details.setTeacherId("fhsdjk");
//
//        System.out.println(dao.createProgressDetails(details));
//
//        System.out.println(dao.getProgressDetailsById(id, RDFFormat.TURTLE));

        dao.deleteProgressDetails("1Jn0");


    }
}
