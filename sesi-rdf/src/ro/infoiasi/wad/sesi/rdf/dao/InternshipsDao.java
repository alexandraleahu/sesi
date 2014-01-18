package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.Constants;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import java.util.List;

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
                    ValueFactoryImpl.getInstance().createURI(Constants.SESI_OBJECTS_NS + internshipId);

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


    public String createInternship(Internship internship) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            con.begin();

            con.commit();
            return "";
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public static void main(String[] args) throws Exception {
        InternshipsDao dao = new InternshipsDao();

        System.out.println(dao.getAllInternships(RDFFormat.TURTLE));

    }

    @Override
    public String getOntClassName() {
        return Constants.INTERNSHIP_CLASS;
    }
}
