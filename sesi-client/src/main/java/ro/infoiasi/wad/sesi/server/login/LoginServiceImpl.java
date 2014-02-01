package ro.infoiasi.wad.sesi.server.login;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ro.infoiasi.wad.sesi.client.authentication.LoginService;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.SESI_BASE_URL;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

    private static final long serialVersionUID = -8705612257489901077L;
    public static final String RESOURCE_PATH = "login";

    @Override
    public Boolean login(String username, String password) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH);
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Status.FORBIDDEN.getStatusCode()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean authenticate(String username, String password, String type) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH);
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        form.param("type", type);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Status.FORBIDDEN.getStatusCode()) {
            return false;
        }
        return true;
    }

    @Override
    public String getType(String username) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH)
                .path(username);
        Invocation invocation = target.request()
                .accept(MediaType.TEXT_PLAIN)
                .buildGet();
        Response response = invocation.invoke();
        client.close();
        if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
            return null;
        }
        return response.readEntity(String.class);
    }
}
