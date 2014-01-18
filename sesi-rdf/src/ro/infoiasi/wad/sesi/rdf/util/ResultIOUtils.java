package ro.infoiasi.wad.sesi.rdf.util;

import com.complexible.stardog.api.GraphQuery;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.rio.RDFFormat;

import java.io.ByteArrayOutputStream;

public class ResultIOUtils {

    private ResultIOUtils() {}

    public static String writeGraphResultsToString(GraphQuery graphQuery, RDFFormat format) throws Exception {
        GraphQueryResult graphQueryResult = graphQuery.execute();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        QueryResultIO.write(graphQueryResult, format, baos);

        graphQueryResult.close();
        return baos.toString();
    }

}
