package ro.infoiasi.wad.sesi.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.util.HasEventBus;


public class HomePanel extends Composite implements HasEventBus {
    @Override
    public HandlerManager getEventBus() {
        return eventBus;
    }

    interface HomePanelUiBinder extends UiBinder<HTMLPanel, HomePanel> {
    }

    private static HomePanelUiBinder ourUiBinder = GWT.create(HomePanelUiBinder.class);
    @UiField(provided = true)
    RegisterForm registerForm;

    @UiField(provided = true)
    LoginForm loginForm;

    private final HandlerManager eventBus;

    public HomePanel(HandlerManager eventBus) {
        this.eventBus = eventBus;
        registerForm = new RegisterForm(eventBus);
        loginForm = new LoginForm(eventBus);
        initWidget(ourUiBinder.createAndBindUi(this));

    }
}