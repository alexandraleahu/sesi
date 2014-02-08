package ro.infoiasi.wad.sesi.client.main;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Hero;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.ResponsiveNavbar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.authentication.*;
import ro.infoiasi.wad.sesi.client.internships.InternshipsByCategoryView;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.HasEventBus;
import ro.infoiasi.wad.sesi.client.teachers.TeacherView;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;
import ro.infoiasi.wad.sesi.core.model.UserAccountType;


public class MainView implements IsWidget, ValueChangeHandler<String>, HasEventBus,
        LoginSuccessfulEventHandler, RegisterSuccessfulEventHandler {

    public static final String NOT_LOGGED_IN = "You are not logged in. Click on the buttons above to Login or create a new account using the Register button";

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
    ResponsiveNavbar navBar;


    @UiField
    Button logoutBtn;

    @UiField
    Button profileBtn;
    @UiField
    Button loginBtn;
    @UiField
    Button registerBtn;
    @UiField
    SimplePanel mainPanel;
    @UiField
    NavLink homeLink;
    @UiField
    NavLink internshipsLink;
    @UiField
    NavLink companiesLink;
    private HTMLPanel root;


    private final HandlerManager eventBus;
    public MainView(HandlerManager eventBus) {
        root = ourUiBinder.createAndBindUi(this);
        this.eventBus = eventBus;

        wireUiElements();

        fillHomeLink();
    }

    private void wireUiElements() {

        eventBus.addHandler(LoginSuccessfulEvent.TYPE, this);
        eventBus.addHandler(RegisterSuccessfulEvent.TYPE, this);

        History.addValueChangeHandler(this);
    }

    @Override
    public void onRegisterSuccessful(RegisterSuccessfulEvent event) {
        showNotLoggedInMessage("Your account was successfully created! Press the Login button above to login into the application.");
    }

    @Override
    public void onLoginSuccessful(LoginSuccessfulEvent event) {
        Sesi.setCurrentUserType(event.getAccountType());
        Sesi.setCurrentUserId(event.getId());
        if (event.getName() != null) {

            Sesi.setCurrentUsername(event.getName());
        }

        showWelcomeMessage();

        logoutBtn.setVisible(true);
        profileBtn.setVisible(true);
        loginBtn.setVisible(false);
        registerBtn.setVisible(false);
    }

    private void showNotLoggedInMessage(String msg) {

        Hero hero = new Hero();
        hero.setWidth("50%");
        hero.add(new Label(msg));
        mainPanel.setWidget(hero);
    }

    private void showWelcomeMessage() {

        Hero hero = new Hero();
        hero.setWidth("50%");
        Label welcome = new Label();

        welcome.setText("You are logged in as " + Sesi.getCurrentUserId());
        hero.add(welcome);
        mainPanel.setWidget(hero);
    }

    @UiHandler("homeLink")
    public void homeLinkClicked(ClickEvent event) {
        fillHomeLink();
    }

    @UiHandler("internshipsLink")
    public void internshipsLinkClicked(ClickEvent event) {
        mainPanel.setWidget(new InternshipsByCategoryView());
    }

    @UiHandler("companiesLink")
    public void companiesLinkClicked(ClickEvent event) {

    }

    @UiHandler("loginBtn")
    public void loginBtnClicked(ClickEvent event) {
        mainPanel.setWidget(new LoginForm(eventBus));

    }

    @UiHandler("registerBtn")
    public void registerBtnClicked(ClickEvent event) {
        mainPanel.setWidget(new RegisterForm(eventBus));

    }


    @UiHandler("logoutBtn")
    public void logoutBtnClicked(ClickEvent event) {
        Sesi.removeAllCookies();
        logoutBtn.setVisible(false);
        profileBtn.setVisible(false);
        loginBtn.setVisible(true);
        registerBtn.setVisible(true);
        showNotLoggedInMessage(NOT_LOGGED_IN);
    }

    @UiHandler("profileBtn")
    public void profileBtnClicked(ClickEvent event) {
        UserAccountType currentUserType = Sesi.getCurrentUserType();
        if (currentUserType != null) {

            switch (currentUserType) {
                case COMPANY_ACCOUNT:
                    break;
                case TEACHER_ACCOUNT:
                     mainPanel.setWidget(new TeacherView());
                    break;
                case STUDENT_ACCOUNT:
                    break;
            }
        }
    }

    private void fillHomeLink() {
        String currentAccountId = Sesi.getCurrentUserId();
        if (currentAccountId == null) {
            // not logged in
            showNotLoggedInMessage(NOT_LOGGED_IN);
            loginBtn.setVisible(true);
            registerBtn.setVisible(true);
            logoutBtn.setVisible(false);
            profileBtn.setVisible(false);
        } else {
            logoutBtn.setVisible(true);
            profileBtn.setVisible(true);
            loginBtn.setVisible(false);
            registerBtn.setVisible(false);
            showWelcomeMessage();

            switch(Sesi.getCurrentUserType()) {
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


    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String[] historyToken = event.getValue().split(WidgetConstants.dataSeparator);

        if (historyToken[0].equals(WidgetConstants.VIEW_TOKEN)) {

        }
    }

    // toSignin("Linkedin")
    private void toSignin(String provider) {
        SigninService.App.getInstance().getAuthenticateUrl(provider, Window.Location.getHref(), new AsyncCallback<String>() {

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