package ro.infoiasi.wad.sesi.server.util;


import com.hp.hpl.jena.query.*;
import org.apache.jena.atlas.web.auth.ServiceAuthenticator;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;

public class SparqlService {
    final static String serviceEndpoint = "http://localhost:5820/sesi/query/";

    public OntologyExtraInfo getOntologyExtraInfo(String uri) {
        String comNameQuery = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> " +
                " select ?name ?seeAlso where { <http://rdf.freebase.com/ns/m.01fhg3> sesiSchema:name ?name . <http://rdf.freebase.com/ns/m.01fhg3> rdfs:seeAlso ?seeAlso . }";
        Query query = QueryFactory.create(comNameQuery);
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        OntologyExtraInfo extraInfo = new OntologyExtraInfo();
        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                extraInfo.setName(solution.getLiteral("name").getString());
                extraInfo.setInfoUrl(solution.getLiteral("seeAlso").getString());
                extraInfo.setOntologyUri(uri);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return extraInfo;
    }
}
