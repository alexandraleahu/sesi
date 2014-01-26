package ro.infoiasi.wad.sesi.client.authentication;

import java.util.concurrent.ExecutionException;

import ro.infoiasi.wad.sesi.client.rpc.LoginService;

import com.google.common.util.concurrent.SettableFuture;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginServiceWrapper {

    public static boolean login(String username, String password) {
        LoginCallback lc = new LoginCallback();
        LoginService.App.getInstance().login(username, password, lc);
        try {
            return lc.f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean authenticate(String username, String password, String type) {
        LoginCallback lc = new LoginCallback();
        LoginService.App.getInstance().authenticate(username, password, type, lc);
        try {
            return lc.f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getUserType(String username) {
        UserCallback lc = new UserCallback();
        LoginService.App.getInstance().getType(username, lc);
        try {
            return lc.f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class LoginCallback implements AsyncCallback<Boolean> {

    public SettableFuture<Boolean> f = SettableFuture.create();

    @Override
    public void onFailure(Throwable arg0) {
        f.setException(arg0);
    }

    @Override
    public void onSuccess(Boolean arg0) {
        if(arg0 == null || arg0 == false) {
            f.set(false);
        } else {
            f.set(true);
        }
    }
}

class UserCallback implements AsyncCallback<String> {

    public SettableFuture<String> f = SettableFuture.create();

    @Override
    public void onFailure(Throwable arg0) {
        f.setException(arg0);
    }

    @Override
    public void onSuccess(String arg0) {
        f.set(arg0);
    }
}