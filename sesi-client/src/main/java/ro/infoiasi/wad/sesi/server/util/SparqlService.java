package ro.infoiasi.wad.sesi.server.util;


import com.hp.hpl.jena.query.*;
import org.apache.jena.atlas.web.auth.ServiceAuthenticator;

public class SparqlService {
    final static String serviceEndpoint = "http://localhost:5820/sesi/query/";

    public String getCity(String uri) {
        return null;

    }

    public static void main(String[] args) {
        String comNameQuery =
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> " +
                "select ?name where {<http://rdf.freebase.com/ns/m.01fhg3> sesiSchema:name ?name } ";
//                " <http://rdf.freebase.com/ns/m.01fhg3> rdfs:seeAlso ?seeAlso}";
        Query query = QueryFactory.create(comNameQuery);
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                System.out.println(ResultSetFormatter.asText(rs));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
    }
}
