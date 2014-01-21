package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import java.util.List;

import static ro.infoiasi.wad.sesi.rdf.util.Constants.*;
public class TeachersDao implements Dao {

    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getTeacherById(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?t where {?t rdf:type sesiSchema:Teacher ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getAllTeachers(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?t where {?t rdf:type sesiSchema:Teacher .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getAllInternshipProgressDetails(String teacherId) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?details ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Teacher ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:isAssociateInternshipTeacherOf ?details . ")
                    .append("?details sesiSchema:sesiUrl ?sesiUrl . ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", teacherId);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery,"details", SESI_URL_PROP);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String createTeacher(Teacher teacher) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            Resource newTeacher = Values.uri(SESI_OBJECTS_NS, teacher.getId());
            URI progressDetailsClass = Values.uri(SESI_SCHEMA_NS, getOntClassName());

            con.begin();

            Adder adder = con.add();

            // adding the class and the owl:namedIndividual type
            adder.statement(newTeacher, RDF.TYPE, progressDetailsClass);
            adder.statement(newTeacher, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            // then, the id, sesiUrl and name
            URI ID = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI sesiUrl = Values.uri(SESI_SCHEMA_NS, SESI_URL_PROP);
            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);

            adder.statement(newTeacher, ID, Values.literal(teacher.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newTeacher, name, Values.literal(teacher.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(newTeacher, sesiUrl, Values.literal(teacher.getRelativeUri()));

            // adding the faculty property

            con.commit();

            return teacher.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public void deleteTeacher(String teacherId) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();

        try {
            URI teacherUri =
                    Values.uri(SESI_OBJECTS_NS + teacherId);

            // deleting an individual means deleting all statements that have the individual as a subject or as an object
            con.begin();
            con.remove()
                    .statements(teacherUri, null, null)
                    .statements(null, null, teacherUri);
            con.commit();
        } finally {
            connectionPool.releaseConnection(con);
        }

    }


    @Override
    public String getOntClassName() {
        return TEACHER_CLASS;
    }
}
