import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.query.* ;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra
 */
public class Example {
    public static void main(String[] args) {
        OntModel m = ModelFactory.createOntologyModel();
        m.read("file:///D:/Facultate/master2sem1/WAD/jena-fuseki-1.0.1-SNAPSHOT/Data/infoiasi-wade/semantic-web.rdf");

        String queryString = "\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "\n" +
                "SELECT ?numeStudenta\n" +
                "WHERE {\n" +
                "   ?persoana foaf:gen ?gen ;\n" +
                "             foaf:firstName ?numeStudenta .\n" +
                "   FILTER regex (?gen, \"^female\", \"i\") .\n" +
                "}";
        Query query = QueryFactory.create(queryString) ;
        QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
        try {
            ResultSet results = qexec.execSelect() ;
            for ( ; results.hasNext() ; )
            {
                QuerySolution soln = results.nextSolution() ;
//                RDFNode x = soln.get("varName") ;       // Get a result variable by name.
//                Resource r = soln.getResource("VarR") ; // Get a result variable - must be a resource
//                Literal l = soln.getLiteral("VarL") ;   // Get a result variable - must be a literal
            }
        } finally { qexec.close() ; }

    }
}
