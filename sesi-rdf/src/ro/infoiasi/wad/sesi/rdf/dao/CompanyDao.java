package ro.infoiasi.wad.sesi.rdf.dao;

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
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import java.util.List;

import static ro.infoiasi.wad.sesi.rdf.util.Constants.*;

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

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);
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
                    .append("sesiSchema:hasApplication ?application . ")
                    .append("?application sesiSchema:sesiUrl ?sesiUrl .  ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", id);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "application", "sesiUrl");

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getAllCompanyInternshipProgressDetails(String id) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?progressDetails ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:publishedInternship ?internship . ")
                    .append("?internship sesiSchema:progressDetails ?progressDetails . ")
                    .append("?progressDetails sesiSchema:sesiUrl ?sesiUrl .  ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", id);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "application", "sesiUrl");

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String createCompany(Company company) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {
            Resource newCompany = Values.uri(SESI_OBJECTS_NS, company.getId());
            URI companyClass = Values.uri(SESI_SCHEMA_NS, COMPANY_CLASS);

            con.begin();

            Adder adder = con.add();
            adder.statement(newCompany, RDF.TYPE, companyClass);
            adder.statement(newCompany, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);
            URI description = Values.uri(SESI_SCHEMA_NS, DESCRIPTION_PROP);
            URI siteUrl = Values.uri(SESI_SCHEMA_NS, SITE_URL);
            URI isActive = Values.uri(SESI_SCHEMA_NS, IS_ACTIVE);

            adder.statement(newCompany, name, Values.literal(company.getName()));
            adder.statement(newCompany, description, Values.literal(company.getDescription()));
            adder.statement(newCompany, siteUrl, Values.literal(company.getSiteUrl()));
            adder.statement(newCompany, isActive, Values.literal(company.isActive()));

            con.commit();
            return company.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }


    public static void main(String[] args) {
        CompanyDao companyDao = new CompanyDao();
        try {
//            System.out.println("ALL COMPANIES");
//            System.out.println(companyDao.getAllCompanies(RDFFormat.TURTLE));

            System.out.println("Company id 2");
            System.out.println(companyDao.getCompany("002", RDFFormat.TURTLE));

//            System.out.println("\n\n company internships");
//            System.out.println(companyDao.getAllCompanyInternships("002"));
//
//            System.out.println("\n\n company applications");
//            System.out.println(companyDao.getAllCompanyApplications("002"));
//
//            System.out.println("\n\n company internships progress details");
//            System.out.println(companyDao.getAllCompanyInternshipProgressDetails("002"));
//
//            //adding a company
//            Company company = new Company();
//            company.setName("Company 1");
//            company.setDescription("My first company");
//            company.setId("200");
//            company.setSiteUrl("www.Company1.com");
//            company.setActive(true);
//            companyDao.createCompany(company);
//
//            System.out.println("company id 200");
//            System.out.println(companyDao.getCompany("210", RDFFormat.TURTLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getOntClassName() {
        return COMPANY_CLASS;
    }
}
