package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import com.complexible.stardog.jena.SDJenaFactory;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

public class InternshipsDao {

    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getInternshipById(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?i where {?i rdf:type sesiSchema:Internship ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            GraphQueryResult graphQueryResult = graphQuery.execute();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            QueryResultIO.write(graphQueryResult, format, baos);

            return baos.toString();
        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public List<ResourceLinks> getAllInternships() throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            SelectQuery selectQuery = con.select("select ?internship ?sesiUrl where {?internship rdf:type sesiSchema:Internship ; sesiSchema:sesiUrl ?sesiUrl .}");
            TupleQueryResult tupleQueryResult = selectQuery.execute();

            List<ResourceLinks> internships = Lists.newArrayList();
            while (tupleQueryResult.hasNext()) {

                BindingSet next = tupleQueryResult.next();

                ResourceLinks resource = new ResourceLinks();
                resource.setResourceUri(new URI(next.getValue("internship").stringValue()));
                resource.setSesiRelativeUrl(next.getValue("sesiUrl").stringValue());
                internships.add(resource);
            }

            return internships;

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
                                    .append("?application sesiSchema:sesiUrl ?sesiUrl .")
                                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", internshipId);
            TupleQueryResult tupleQueryResult = selectQuery.execute();

            List<ResourceLinks> internships = Lists.newArrayList();

            while (tupleQueryResult.hasNext()) {

                BindingSet next = tupleQueryResult.next();

                ResourceLinks resource = new ResourceLinks();
                resource.setResourceUri(new URI(next.getValue("application").stringValue()));
                resource.setSesiRelativeUrl(next.getValue("sesiUrl").stringValue());
                internships.add(resource);
            }

            return internships;

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
                                    .append("?application sesiSchema:sesiUrl ?sesiUrl .")
                                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", internshipId);
            TupleQueryResult tupleQueryResult = selectQuery.execute();

            List<ResourceLinks> internships = Lists.newArrayList();

            while (tupleQueryResult.hasNext()) {

                BindingSet next = tupleQueryResult.next();

                ResourceLinks resource = new ResourceLinks();
                resource.setResourceUri(new URI(next.getValue("progressDetails").stringValue()));
                resource.setSesiRelativeUrl(next.getValue("sesiUrl").stringValue());
                internships.add(resource);
            }

            return internships;

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    //TODO
    public void deleteInternship(String internshipId) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();

        try {

            Model model = SDJenaFactory.createModel(con);
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF, model);






        } finally {
            connectionPool.releaseConnection(con);
        }

    }

    public static void main(String[] args) throws Exception {
        InternshipsDao dao = new InternshipsDao();

        System.out.println(dao.getAllInternshipProgressDetails("003"));
    }
}
