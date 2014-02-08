package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.event.shared.EventHandler;


public interface LoginSuccessfulEventHandler extends EventHandler {
    void onLogin(LoginSuccessfulEvent event);
}
