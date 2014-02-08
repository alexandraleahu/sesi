package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.event.shared.GwtEvent;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;


public class LoginSuccessfulEvent extends GwtEvent<LoginSuccessfulEventHandler> {

    public static Type<LoginSuccessfulEventHandler> TYPE = new Type<LoginSuccessfulEventHandler>();

    public Type<LoginSuccessfulEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(LoginSuccessfulEventHandler handler) {
        handler.onLogin(this);
    }

    private String id;
    private String name;
    private UserAccountType accountType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(UserAccountType accountType) {
        this.accountType = accountType;
    }

    public LoginSuccessfulEvent(String id, String name, UserAccountType accountType) {
        this.id = id;
        this.name = name;
        this.accountType = accountType;
    }
}
