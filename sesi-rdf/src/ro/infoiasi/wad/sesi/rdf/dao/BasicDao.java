package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.BooleanQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;

public abstract class BasicDao implements Dao {

    protected final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public boolean resourceExists(String id) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            BooleanQuery boolQuery = con.ask("ask {?a sesiSchema:id ?id.}");
            boolQuery.parameter("id", id);
            return boolQuery.execute();

        } finally {
            connectionPool.releaseConnection(con);
        }
    }
}
