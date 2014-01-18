package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static ro.infoiasi.wad.sesi.rdf.util.Constants.STUDENT_CLASS;

public class StudentsDao implements Dao {
    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getAllStudents(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?s where {?s rdf:type sesiSchema:Student .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getStudent(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?s where {?s rdf:type sesiSchema:Student ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            GraphQueryResult graphQueryResult = graphQuery.execute();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            QueryResultIO.write(graphQueryResult, format, baos);

            return baos.toString();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getAllStudentApplications(String id) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?application ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Student ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:submittedApplication ?application . ")
                    .append("?application sesiSchema:sesiUrl ?sesiUrl .  ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", id);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "application", "sesiUrl");

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public static void main(String[] args) {
        StudentsDao dao = new StudentsDao();

        try {
            System.out.println("Student ID 1");
            System.out.println(dao.getStudent("001", RDFFormat.TURTLE));

            System.out.println("\n All students");
            System.out.println(dao.getAllStudents(RDFFormat.TURTLE));

            System.out.println("\n Student applications");
            System.out.println(dao.getAllStudentApplications("001"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getOntClassName() {
        return STUDENT_CLASS;
    }
}
