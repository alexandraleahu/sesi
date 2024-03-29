package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.Remover;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class CompaniesDao extends BasicDao {

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
        if (!resourceExists(id)) {
            return null;
        }
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
                    .append("?c rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id . ")
                    .append("?internship sesiSchema:publishedByCompany ?c. ")
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
                    .append("?c rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id . ")
                    .append("?i sesiSchema:publishedByCompany ?c. ?application sesiSchema:applicationInternship ?i")
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
                    .append("describe ?pd ")
                    .append("where {")
                    .append("?c rdf:type sesiSchema:SoftwareCompany ; ")
                    .append("sesiSchema:id ?id . ")
                    .append("?i sesiSchema:publishedByCompany ?c. ?pd sesiSchema:attendedInternship ?i.")
                    .append("}");


            GraphQuery graphQuery = con.graph(sb.toString());
            graphQuery.parameter("id", id);
            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }
    public String createMinimalCompany(String companyName, String companyId) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            Resource newCompany = Values.uri(SESI_OBJECTS_NS, companyId);
            URI companyClass = Values.uri(SESI_SCHEMA_NS, COMPANY_CLASS);

            con.begin();

            Adder adder = con.add();
            adder.statement(newCompany, RDF.TYPE, companyClass);
            adder.statement(newCompany, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);
            URI id = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI uri = Values.uri(SESI_SCHEMA_NS, SESI_URL_PROP);

            adder.statement(newCompany, RDFS.LABEL, Values.literal(companyName));
            adder.statement(newCompany, name, Values.literal(companyName, StardogValueFactory.XSD.STRING));
            adder.statement(newCompany, id, Values.literal(companyId, StardogValueFactory.XSD.STRING));
            adder.statement(newCompany, uri, Values.literal("/companies/" + companyId));

            con.commit();
            return "/companies/" + companyId;
        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public String createFullCompany(Company company) throws StardogException {

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
            adder.statement(newCompany, RDFS.LABEL, Values.literal(company.getName()));
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

    public String updateCompany(Company company) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {
            Resource newCompany = Values.uri(SESI_OBJECTS_NS, company.getId());

            con.begin();

            Adder adder = con.add();
            Remover remover = con.remove();

            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);
            URI description = Values.uri(SESI_SCHEMA_NS, DESCRIPTION_PROP);
            URI siteUrl = Values.uri(SESI_SCHEMA_NS, SITE_URL_PROP);
            URI isActive = Values.uri(SESI_SCHEMA_NS, IS_ACTIVE_PROP);

            remover.statements(newCompany, RDFS.LABEL, null);
            adder.statement(newCompany, RDFS.LABEL, Values.literal(company.getName()));

            remover.statements(newCompany, name, null);
            adder.statement(newCompany, name, Values.literal(company.getName(), StardogValueFactory.XSD.STRING));

            remover.statements(newCompany, description, null);
            adder.statement(newCompany, description, Values.literal(company.getDescription(), StardogValueFactory.XSD.STRING));

            remover.statements(newCompany, siteUrl, null);
            adder.statement(newCompany, siteUrl, Values.literal(company.getSiteUrl()));

            remover.statements(newCompany, isActive, null);
            adder.statement(newCompany, isActive, Values.literal(company.isActive()));

            con.commit();
            return company.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }


    public static void main(String[] args) {
        CompaniesDao companiesDao = new CompaniesDao();
        try {
            System.out.println("ALL COMPANIES");
            System.out.println(companiesDao.getAllCompanies(RDFFormat.TURTLE));

            System.out.println("Company id 2");
            System.out.println(companiesDao.getCompany("virtualcomp", RDFFormat.TURTLE));

            System.out.println("\n\n company internships");
            System.out.println(companiesDao.getAllCompanyInternships("virtualcomp", RDFFormat.TURTLE));

            System.out.println("\n\n company applications");
            System.out.println(companiesDao.getAllCompanyApplications("virtualcomp", RDFFormat.TURTLE));

            System.out.println("\n\n company internships progress details");
            System.out.println(companiesDao.getAllCompanyInternshipProgressDetails("virtualcomp", RDFFormat.TURTLE));

            //adding a company
            Company company = new Company();
            company.setName("Company 1");
            company.setDescription("My first company");
            company.setId(RandomStringUtils.randomAlphanumeric(4));
            company.setSiteUrl("www.Company1.com");
            company.setActive(true);

            companiesDao.createFullCompany(company);
            System.out.println(companiesDao.getCompany(company.getId(), RDFFormat.TURTLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getOntClassName() {
        return COMPANY_CLASS;
    }
}
