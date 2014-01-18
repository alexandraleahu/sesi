package ro.infoiasi.wad.sesi.rdf.util;

import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.google.common.collect.Lists;
import org.openrdf.query.BindingSet;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.rio.RDFFormat;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

public class ResultIOUtils {

    private ResultIOUtils() {}

    public static String writeGraphResultsToString(GraphQuery graphQuery, RDFFormat format) throws Exception {
        GraphQueryResult graphQueryResult = graphQuery.execute();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        QueryResultIO.write(graphQueryResult, format, baos);

        graphQueryResult.close();
        return baos.toString();
    }

    public static List<ResourceLinks> getResourceLinksFromSelectQuery(SelectQuery selectQuery, String resourceBinding,
                                                                      String sesiUrlBinding)
                                                                     throws Exception {
        TupleQueryResult tupleQueryResult = selectQuery.execute();

        List<ResourceLinks> links = Lists.newArrayList();

        while (tupleQueryResult.hasNext()) {

            BindingSet next = tupleQueryResult.next();

            ResourceLinks resource = new ResourceLinks();
            resource.setResourceUri(new URI(next.getValue(resourceBinding).stringValue()));
            resource.setSesiRelativeUrl(next.getValue(sesiUrlBinding).stringValue());
            links.add(resource);
        }
        tupleQueryResult.close();
        return links;
    }

}
