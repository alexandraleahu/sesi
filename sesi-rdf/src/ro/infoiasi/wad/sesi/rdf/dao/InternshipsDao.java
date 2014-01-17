package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;

public class InternshipsDao {

    public static void main(String[] args) throws Exception {
    ReasoningConnection connection = SesiConnectionPool.INSTANCE.getConnection();
    //        SelectQuery query = connection.select("select ?c ?i where {?i sesiSchema:publishedByCompany ?c}");
    //        TupleQueryResult result = query.execute();
    //        QueryResultIO.write(result, TupleQueryResultFormat.SPARQL, System.out);
    //        result.close();


//    Getter getter = connection.get();
//    URI uri = ValueFactoryImpl.getInstance().createURI("http://www.infoiasi.ro/wad/objects/sesi/001");
//    getter.subject(uri);
//
      /*Iteration<Statement, StardogException> iter = connection.get()
		                                                    .subject(uri)
		                                                    .iterator();


		while (iter.hasNext()) {
			System.out.println(iter.next());
		}*/

        GraphQuery graphQuery = connection.graph("describe ?c where {?i sesiSchema:publishedByCompany ?c}");
        GraphQueryResult result = graphQuery.execute();
        QueryResultIO.write(result, RDFFormat.TURTLE, System.out);

        result.close();
        connection.close();


    }
}
