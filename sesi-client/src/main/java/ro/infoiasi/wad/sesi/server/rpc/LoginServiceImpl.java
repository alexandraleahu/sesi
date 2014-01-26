package ro.infoiasi.wad.sesi.server.rpc;

import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.SESI_BASE_URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ro.infoiasi.wad.sesi.client.rpc.LoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

    private static final long serialVersionUID = -8705612257489901077L;
    public static final String RESOURCE_PATH = "login";
    
    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean authenticate(String username, String password, String type) {
        return false;
    }

    @Override
    public String getType(String username) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH)
                .path(username);

        Invocation invocation = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .buildGet();

        Response response = invocation.invoke();
        client.close();

        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            return null;
        }
        return response.readEntity(String.class);
    }
}
