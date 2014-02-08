package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.User;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;

public interface LoginServiceAsync {

    void login(String username, String password, AsyncCallback<UserAccountType> async);
    void getType(String username, AsyncCallback<String> async);
}

