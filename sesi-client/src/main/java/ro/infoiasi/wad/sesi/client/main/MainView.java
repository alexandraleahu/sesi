package ro.infoiasi.wad.sesi.client.main;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.authentication.SigninService;
import ro.infoiasi.wad.sesi.client.internships.InternshipsByCategoryView;
import ro.infoiasi.wad.sesi.client.util.HasEventBus;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;


public class MainView implements IsWidget, ClickHandler, ValueChangeHandler<String>, HasEventBus,
                                LoginSuccessfulEventHandler {
    @Override
    public Widget asWidget() {
        return root;
    }

    @Override
    public HandlerManager getEventBus() {
        return eventBus;
    }

    interface MainViewUiBinder extends UiBinder<HTMLPanel, MainView> {


    }

    private static MainViewUiBinder ourUiBinder = GWT.create(MainViewUiBinder.class);

    @UiField
    TabPanel tabPanel;

    @UiField
    SimplePanel homePanel;
    @UiField
    SimplePanel internshipsPanel;
    @UiField
    SimplePanel companiesPanel;

    @UiField
    Tab homeTab;
    @UiField
    Tab internshipsTab;
    @UiField
    Tab companiesTab;
    @UiField
    Button logoutBtn;

    @UiField
    Button profileBtn;
    private HTMLPanel root;


    private SimplePanel currentPanel;
    private final HandlerManager eventBus;
    public MainView(HandlerManager eventBus) {
        root = ourUiBinder.createAndBindUi(this);
        this.eventBus = eventBus;

        wireUiElements();

        currentPanel = homePanel;
        fillHomeTab();
    }

    private void wireUiElements() {
        homeTab.addClickHandler(this);
        internshipsTab.addClickHandler(this);
        companiesTab.addClickHandler(this);
        logoutBtn.addClickHandler(this);

        eventBus.addHandler(LoginSuccessfulEvent.TYPE, this);

        History.addValueChangeHandler(this);
    }

    @Override
    public void onLogin(LoginSuccessfulEvent event) {
        System.out.println(event.getAccountType().getDescription());
        Sesi.setCurrentUserType(event.getAccountType());
        Sesi.setCurrentUserId(event.getId());
        if (event.getName() != null) {

            Sesi.setCurrentUsername(event.getName());
        }

        showWelcomeMessage();

        logoutBtn.setVisible(true);
        profileBtn.setVisible(true);
    }

    private void showWelcomeMessage() {
        Label welcome = new Label();

        welcome.setText("You are logged in as " + Sesi.getCurrentUserId());
        currentPanel.setWidget(welcome);
    }

    @Override
    public void onClick(ClickEvent event) {

        if (event.getSource().equals(homeTab.asTabLink().getAnchor())) {
            currentPanel = homePanel;
            fillHomeTab();

        } else if (event.getSource().equals(internshipsTab.asTabLink().getAnchor())) {
            currentPanel = internshipsPanel;
            internshipsPanel.setWidget(new InternshipsByCategoryView());

        } else if (event.getSource().equals(companiesTab.asTabLink().getAnchor())) {
            currentPanel = companiesPanel;
        } else if (event.getSource().equals(logoutBtn)) {

            Sesi.removeAllCookies();
            logoutBtn.setVisible(false);
            profileBtn.setVisible(false);
            homeTab.setActive(true);

            currentPanel = homePanel;
            showLoginAndRegisterForms();
        }
    }

    private void fillHomeTab() {
        UserAccountType currentAccountType = Sesi.getCurrentUserType();
        if (currentAccountType == null) {
            // not logged in
            showLoginAndRegisterForms();
        } else {
            logoutBtn.setVisible(true);
            profileBtn.setVisible(true);
            showWelcomeMessage();

            switch(currentAccountType) {
                case STUDENT_ACCOUNT:
                    // afisam internshipuri recomandate

                    // afisam aplicarile

                    // afisam progress details
                    break;
                case COMPANY_ACCOUNT:
                    // afisam aplicarile

                    // afisam progress details

                    // afisam internshipurile companiei
                    break;
                case TEACHER_ACCOUNT:
                    // afisam chestiile de grafice
                    break;
                default:
                    break;
            }
        }

    }

    private void showLoginAndRegisterForms() {

        currentPanel.setWidget(new HomePanel(eventBus));
    }


    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String[] historyToken = event.getValue().split(WidgetConstants.dataSeparator);

        if (historyToken[0].equals(WidgetConstants.VIEW_TOKEN)) {

        }
    }

    // toSignin("Linkedin")
    private void toSignin(String provider) {
        SigninService.Util.getInstance().getAuthenticateUrl(provider, Window.Location.getHref(), new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                System.out.println("result " + result);
                Window.Location.replace(result);

            }
        });
    }

}