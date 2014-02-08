package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.shared.UnsuccessfulLoginException;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService{
    public static class App {
        private static final LoginServiceAsync ourInstance = (LoginServiceAsync) GWT.create(LoginService.class);

        public static LoginServiceAsync getInstance() {
            return ourInstance;
        }
    }

    UserAccountType login(String username, String password) throws UnsuccessfulLoginException;

    String getType(String username);

}
