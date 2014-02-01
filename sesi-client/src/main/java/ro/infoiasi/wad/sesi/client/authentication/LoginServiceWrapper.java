package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginServiceWrapper {

    public static boolean login(String username, String password) {
        LoginCallback lc = new LoginCallback();
        LoginServiceAsync lsa = LoginService.App.getInstance();
        lsa.login(username, password, lc);
        return lc.value;
    }

    public static boolean authenticate(String username, String password, String type) {
        LoginCallback lc = new LoginCallback();
        LoginService.App.getInstance().authenticate(username, password, type, lc);
        return lc.value;
    }

    public static String getUserType(String username) {
        UserCallback lc = new UserCallback();
        LoginService.App.getInstance().getType(username, lc);
        return lc.value;
    }
}

class LoginCallback implements AsyncCallback<Boolean> {

    public volatile boolean value = false;
    public volatile boolean set = false;

    @Override
    public void onFailure(Throwable arg0) {
        arg0.printStackTrace();
        set = true;
    }

    @Override
    public void onSuccess(Boolean arg0) {
        if(arg0 == null || arg0 == false) {
            value = false;
        } else {
            value = true;
        }
        set = true;
    }
}

class UserCallback implements AsyncCallback<String> {

    public volatile String value = null;
    public volatile boolean set = false;
    
    @Override
    public void onFailure(Throwable arg0) {
        arg0.printStackTrace();
        set = true;
    }

    @Override
    public void onSuccess(String arg0) {
        value = arg0;
        set = true;
    }
}