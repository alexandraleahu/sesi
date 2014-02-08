package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.event.shared.EventHandler;


public interface RegisterSuccessfulEventHandler extends EventHandler {
    void onRegisterSuccessful(RegisterSuccessfulEvent event);
}
