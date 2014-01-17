package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import com.google.common.collect.Lists;
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

    public String getInternshipById(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = SesiConnectionPool.INSTANCE.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?i where {?i rdf:type sesiSchema:Internship ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            GraphQueryResult graphQueryResult = graphQuery.execute();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            QueryResultIO.write(graphQueryResult, format, baos);

            return  baos.toString();
        } finally {
            SesiConnectionPool.INSTANCE.releaseConnection(con);
        }


    }

    public static void main(String[] args) throws Exception {
        InternshipsDao dao = new InternshipsDao();

        System.out.println(dao.getAllInternships());
    }

    // <rdf resource URI, sesiURL>
    public List<ResourceLinks> getAllInternships() throws Exception {
        ReasoningConnection con = SesiConnectionPool.INSTANCE.getConnection();
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
            SesiConnectionPool.INSTANCE.releaseConnection(con);
        }
    }
}
