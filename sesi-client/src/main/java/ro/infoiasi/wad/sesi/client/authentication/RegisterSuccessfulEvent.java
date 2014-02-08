package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.event.shared.GwtEvent;


public class RegisterSuccessfulEvent extends GwtEvent<RegisterSuccessfulEventHandler> {
    public static Type<RegisterSuccessfulEventHandler> TYPE = new Type<RegisterSuccessfulEventHandler>();

    public Type<RegisterSuccessfulEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(RegisterSuccessfulEventHandler handler) {
        handler.onRegisterSuccessful(this);
    }
}
