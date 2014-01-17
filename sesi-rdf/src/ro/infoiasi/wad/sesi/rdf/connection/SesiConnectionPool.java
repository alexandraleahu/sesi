package ro.infoiasi.wad.sesi.rdf.connection;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.ConnectionPool;
import com.complexible.stardog.api.ConnectionPoolConfig;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import com.complexible.stardog.reasoning.api.ReasoningType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

public enum SesiConnectionPool implements Closeable {

    INSTANCE;

    private final ConnectionPool pool;
    private static Logger LOGGER = LoggerFactory.getLogger(SesiConnectionPool.class);

    SesiConnectionPool() {

        ConnectionConfiguration conConfig = ConnectionConfiguration
                                            .to("sesi")
                                            .server("http://localhost:5820")
                                            .credentials("admin", "admin")
                                            .reasoning(ReasoningType.SL);

        ConnectionPoolConfig conPoolConfig = ConnectionPoolConfig
                                        .using(conConfig)							// use my connection configuration to spawn new connections
                                        .minPool(10)								// the number of objects to start my pool with
                                        .maxPool(100)								// the maximum number of objects that can be in the pool (leased or idle)
                                        .expiration(1, TimeUnit.HOURS)				// Connections can expire after being idle for 1 hr.
                                        .blockAtCapacity(1, TimeUnit.MINUTES);

        pool = conPoolConfig.create();
    }

    public ReasoningConnection getConnection() throws StardogException {

        return pool.obtain()
                   .as(ReasoningConnection.class);
    }

    public void releaseConnection(Connection con) throws StardogException {
        pool.release(con);
    }

    @Override
    public void close() {
        try {
            pool.shutdown();
        } catch (StardogException e) {
           LOGGER.error("Could not shutdown connection pool", e);
        }
    }





}
