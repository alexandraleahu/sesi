package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.StudentLinkedinProfile;
import ro.infoiasi.wad.sesi.core.model.User;

public interface SigninServiceAsync {
    public void getAuthenticateUrl(String provider, String callbackUrl, AsyncCallback<String> callback);

    public void verify(String verifier, AsyncCallback<User> callback);

    void getProfile(String verifierToken, AsyncCallback<StudentLinkedinProfile> async);
}
