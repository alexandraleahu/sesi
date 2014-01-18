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

import static ro.infoiasi.wad.sesi.rdf.util.Constants.COMPANY_CLASS;

public class CompanyDao implements Dao {
    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getAllCompanies(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?c where {?c rdf:type sesiSchema:SoftwareCompany .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getCompany(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?c where {?c rdf:type sesiSchema:SoftwareCompany ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            GraphQueryResult graphQueryResult = graphQuery.execute();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            QueryResultIO.write(graphQueryResult, format, baos);

            return baos.toString();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getAllCompanyInternships(String id) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?internship ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:publishedInternship ?internship . ")
                    .append("?internship sesiSchema:sesiUrl ?sesiUrl . ")
                    .append("}");

            System.out.println(sb);
            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", id);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "internship", "sesiUrl");

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getAllCompanyApplications(String id) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?application ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:publishedInternship ?internship . ")
                    .append("?internship sesiSchema:hasInternshipApplication ?application .")
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
        CompanyDao companyDao = new CompanyDao();
        try {
            System.out.println("ALL COMPANIES");
            System.out.println(companyDao.getAllCompanies(RDFFormat.TURTLE));

            System.out.println("Company id 2");
            System.out.println(companyDao.getCompany("002", RDFFormat.TURTLE));

            System.out.println("\n\n company internships");
            System.out.println(companyDao.getAllCompanyInternships("002"));

            System.out.println("\n\n internship applications");
            System.out.println(companyDao.getAllCompanyApplications("002"));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public String getOntClassName() {
        return COMPANY_CLASS;
    }
}
