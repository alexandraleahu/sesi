package ro.infoiasi.wad.sesi.client.main;

import com.github.gwtbootstrap.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import ro.infoiasi.wad.sesi.client.authentication.LoginService;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;
import ro.infoiasi.wad.sesi.client.util.HasEventBus;


public class LoginForm extends Composite implements HasEventBus {
    @Override
    public HandlerManager getEventBus() {
        return eventBus;
    }

    interface LoginFormUiBinder extends UiBinder<HTMLPanel, LoginForm> {
    }

    private static LoginFormUiBinder ourUiBinder = GWT.create(LoginFormUiBinder.class);
    private final HandlerManager eventBus;

    @UiField
    Form loginForm;
    @UiField
    ControlGroup userNameControlGroup;
    @UiField
    TextBox userName;
    @UiField
    HelpInline userNameHelpInline;
    @UiField
    ControlGroup passwordControlGroup;
    @UiField
    PasswordTextBox password;
    @UiField
    HelpInline passwordHelpInline;
    @UiField
    Button loginBtn;

    @UiField
    Button loginLinkedinButton;

    @UiField
    Icon loadingResultsIcon;
    @UiField
    SimplePanel errorResultsPanel;

    public LoginForm(HandlerManager eventBus) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.eventBus = eventBus;

    }


    @UiHandler("loginLinkedinButton")
    public void onClickLinkedin(ClickEvent event) {

    }
    @UiHandler("loginBtn")
    public void onClickLogin(ClickEvent event) {

        final String username = userName.getText();
        final String passwd = password.getText();

        loadingResultsIcon.setVisible(true);
        errorResultsPanel.setVisible(false);

        LoginService.App.getInstance().login(username, passwd, new AsyncCallback<UserAccountType>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
                errorResultsPanel.setVisible(true);
            }

            @Override
            public void onSuccess(UserAccountType accountType) {
                loadingResultsIcon.setVisible(false);
                errorResultsPanel.setVisible(false);

                eventBus.fireEvent(new LoginSuccessfulEvent(username, null, accountType));
            }
        });
    }
}