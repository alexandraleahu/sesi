package ro.infoiasi.wad.sesi.client.main;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Hero;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.ResponsiveNavbar;
import com.github.gwtbootstrap.client.ui.constants.LabelType;
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
import ro.infoiasi.wad.sesi.client.applications.InternshipApplicationService;
import ro.infoiasi.wad.sesi.client.authentication.*;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.HasEventBus;
import ro.infoiasi.wad.sesi.client.companies.CompaniesService;
import ro.infoiasi.wad.sesi.client.internships.InternshipView;
import ro.infoiasi.wad.sesi.client.internships.InternshipsByCategoryView;
import ro.infoiasi.wad.sesi.client.internships.InternshipsService;
import ro.infoiasi.wad.sesi.client.progressdetails.InternshipsProgressDetailsService;
import ro.infoiasi.wad.sesi.client.students.StudentView;
import ro.infoiasi.wad.sesi.client.students.StudentsService;
import ro.infoiasi.wad.sesi.client.teachers.TeacherMainView;
import ro.infoiasi.wad.sesi.client.teachers.TeacherProfileView;
import ro.infoiasi.wad.sesi.client.teachers.TeachersService;
import ro.infoiasi.wad.sesi.core.model.*;


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
        verifyOauth();

        wireUiElements();

        fillHomeLink();
    }

    private void verifyOauth() {

        String v = Window.Location.getParameter("oauth_verifier");
        if (v != null) {
            SigninService.App.getInstance().verify(v, new AsyncCallback<User>() {

                @Override
                public void onFailure(Throwable caught) {
                    //to be decided what to do
                }

                @Override
                public void onSuccess(final User user) {
                    LoginService.App.getInstance().login(user.id, null, new AsyncCallback<UserAccountType>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            StudentsService.App.getInstance().registerStudent(user.userName, null, user.firstName, new AsyncCallback<Boolean>() {
                                @Override
                                public void onFailure(Throwable throwable) {
                                    //
                                }

                                @Override
                                public void onSuccess(Boolean aBoolean) {
                                    eventBus.fireEvent(new LoginSuccessfulEvent(user.id, user.firstName + " " + user.lastName, UserAccountType.STUDENT_ACCOUNT));
                                }
                            });
                        }

                        @Override
                        public void onSuccess(UserAccountType accountType) {
                            eventBus.fireEvent(new LoginSuccessfulEvent(user.id, user.firstName + " " + user.lastName, accountType));
                        }
                    });
                }
            });
            return;
        }
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
                     mainPanel.setWidget(new TeacherMainView());
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

        }

    }


    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        if (!event.getValue().matches("/\\w+/\\w+")) {
            return;
        }

        String[] resourceUrl = event.getValue().split("/");
        String resourceType = resourceUrl[1];
        String id = resourceUrl[2];

        if (resourceType.equals(InternshipsService.RESOURCE_PATH)) {

            InternshipsService.App.getInstance().getInternshipById(id, new AsyncCallback<Internship>() {
                @Override
                public void onFailure(Throwable caught) {
                    showGeneralError();
                }

                @Override
                public void onSuccess(Internship result) {
                    if (result != null) {

                        InternshipView view = new InternshipView();
                        mainPanel.add(view);
                        view.edit(result);
                    } else {
                        showResourceNotFoundError();
                    }
                }
            });

        } else if (resourceType.equals(TeachersService.RESOURCE_PATH))  {

            TeachersService.App.getInstance().getTeacherById(id, new AsyncCallback<Teacher>() {
                @Override
                public void onFailure(Throwable caught) {
                    showGeneralError();
                }

                @Override
                public void onSuccess(Teacher result) {
                    if (result != null) {

                        TeacherProfileView view = new TeacherProfileView();
                        mainPanel.add(view);
                        view.edit(result);
                    } else {
                        showResourceNotFoundError();
                    }
                }
            });

        } else if (resourceType.equals(StudentsService.RESOURCE_PATH))  {

            StudentsService.App.getInstance().getStudentById(id, new AsyncCallback<Student>() {
                @Override
                public void onFailure(Throwable caught) {
                    showGeneralError();
                }

                @Override
                public void onSuccess(Student result) {
                    if (result != null) {

                        StudentView view = new StudentView();
                        mainPanel.add(view);
                        view.edit(result);
                    } else {
                        showResourceNotFoundError();
                    }
                }
            });

        }  else if (resourceType.equals(CompaniesService.RESOURCE_PATH))  {

            CompaniesService.App.getInstance().getCompanyById(id, new AsyncCallback<Company>() {
                @Override
                public void onFailure(Throwable caught) {
                    showGeneralError();
                }

                @Override
                public void onSuccess(Company result) {
                    if (result != null) {

                        // TODO
                    } else {
                        showResourceNotFoundError();
                    }
                }
            });

        } else if (resourceType.equals(InternshipApplicationService.RESOURCE_PATH))  {

            InternshipApplicationService.App.getInstance().getApplicationById(id, new AsyncCallback<InternshipApplication>() {
                @Override
                public void onFailure(Throwable caught) {
                    showGeneralError();
                }

                @Override
                public void onSuccess(InternshipApplication result) {
                    if (result != null) {

                        // TODO
                    } else {
                        showResourceNotFoundError();
                    }
                }
            });


        } else if (resourceType.equals(InternshipsProgressDetailsService.RESOURCE_PATH))  {
            InternshipsProgressDetailsService.App.getInstance().getProgressDetailsById(id, new AsyncCallback<InternshipProgressDetails>() {
                @Override
                public void onFailure(Throwable caught) {
                    showGeneralError();
                }

                @Override
                public void onSuccess(InternshipProgressDetails result) {
                    if (result != null) {

                        // TODO
                    } else {
                        showResourceNotFoundError();
                    }
                }
            });
        }

    }

    private void showGeneralError() {
        showError("Error! Could not load the selected resource!");
    }

    private void showResourceNotFoundError() {
        showError("Error! Resource not found!");
    }

    private void showError(String msg) {
        com.github.gwtbootstrap.client.ui.Label errorLabel = new com.github.gwtbootstrap.client.ui.Label();
        errorLabel.setType(LabelType.IMPORTANT);
        errorLabel.setText(msg);
        Hero hero = new Hero();
        hero.add(errorLabel);
        mainPanel.setWidget(hero);
    }

}