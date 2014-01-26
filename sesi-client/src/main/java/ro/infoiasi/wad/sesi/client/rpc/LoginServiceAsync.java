package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

    void login(String username, String password, AsyncCallback<Boolean> async);

    void authenticate(String username, String password, String type, AsyncCallback<Boolean> async);

    void getType(String username, AsyncCallback<String> async);
}

