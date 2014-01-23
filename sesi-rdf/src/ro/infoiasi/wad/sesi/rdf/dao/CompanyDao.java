package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

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

    public String getAllCompanyInternships(String id, RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("describe ?internship ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:publishedInternship ?internship . ")
                    .append("}");

            GraphQuery graphQuery = con.graph(sb.toString());
            graphQuery.parameter("id", id);
            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getAllCompanyApplications(String id, RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("describe ?application ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:hasApplication ?application . ")
                    .append("}");

            GraphQuery graphQuery = con.graph(sb.toString());
            graphQuery.parameter("id", id);
            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getAllCompanyInternshipProgressDetails(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("describe ?progressDetails ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:publishedInternship ?internship . ")
                    .append("?internship sesiSchema:progressDetails ?progressDetails . ")
                    .append("}");


            GraphQuery graphQuery = con.graph(sb.toString());
            graphQuery.parameter("id", id);
            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

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

            URI id = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);
            URI description = Values.uri(SESI_SCHEMA_NS, DESCRIPTION_PROP);
            URI siteUrl = Values.uri(SESI_SCHEMA_NS, SITE_URL_PROP);
            URI isActive = Values.uri(SESI_SCHEMA_NS, IS_ACTIVE_PROP);

            adder.statement(newCompany, id, Values.literal(company.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newCompany, name, Values.literal(company.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(newCompany, description, Values.literal(company.getDescription(), StardogValueFactory.XSD.STRING));
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
            System.out.println("ALL COMPANIES");
            System.out.println(companyDao.getAllCompanies(RDFFormat.TURTLE));

            System.out.println("Company id 2");
            System.out.println(companyDao.getCompany("virtualcomp", RDFFormat.TURTLE));

            System.out.println("\n\n company internships");
            System.out.println(companyDao.getAllCompanyInternships("virtualcomp", RDFFormat.TURTLE));

            System.out.println("\n\n company applications");
            System.out.println(companyDao.getAllCompanyApplications("virtualcomp", RDFFormat.TURTLE));

            System.out.println("\n\n company internships progress details");
            System.out.println(companyDao.getAllCompanyInternshipProgressDetails("virtualcomp", RDFFormat.TURTLE));

            //adding a company
            Company company = new Company();
            company.setName("Company 1");
            company.setDescription("My first company");
            company.setId(RandomStringUtils.randomAlphanumeric(4));
            company.setSiteUrl("www.Company1.com");
            company.setActive(true);

            companyDao.createCompany(company);
            System.out.println(companyDao.getCompany(company.getId(), RDFFormat.TURTLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getOntClassName() {
        return COMPANY_CLASS;
    }
}
