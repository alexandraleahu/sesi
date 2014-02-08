package ro.infoiasi.wad.sesi.server.login;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ro.infoiasi.wad.sesi.client.authentication.LoginService;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.shared.UnsuccessfulLoginException;

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
    public UserAccountType login(String username, String password) throws UnsuccessfulLoginException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH);
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        Invocation invocation = target.request(MediaType.APPLICATION_JSON_TYPE)
                .buildPost(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        Response response = invocation.invoke();
        if (response.getStatus() == Status.FORBIDDEN.getStatusCode()) {
            throw new UnsuccessfulLoginException("Wrong username or password");
        }

        return UserAccountType.fromDescription(response.readEntity(String.class));
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
