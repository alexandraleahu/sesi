package ro.infoiasi.wad.sesi.client.main;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.internships.InternshipsByCategoryView;
import ro.infoiasi.wad.sesi.client.authentication.SigninService;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;


public class MainView implements IsWidget, ClickHandler, ValueChangeHandler<String> {
    @Override
    public Widget asWidget() {
        return root;
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
    Button loginLink;
    @UiField
    Button registerLink;
    @UiField
    Button logoutLink;
    @UiField
    Button profileLink;
    @UiField
    Button loginLinkedinButton;

    private HTMLPanel root;
    private SimplePanel currentPanel;

    public MainView() {
        root = ourUiBinder.createAndBindUi(this);
        wireUiElements();
        fillHomeTab();
    }

    private void wireUiElements() {
        homeTab.addClickHandler(this);
        internshipsTab.addClickHandler(this);
        companiesTab.addClickHandler(this);
        loginLinkedinButton.addClickHandler(this);

        History.addValueChangeHandler(this);
    }

    @Override
    public void onClick(ClickEvent event) {

        if (event.getSource().equals(homeTab.asTabLink().getAnchor())) {
            System.out.println("home");
            currentPanel = homePanel;
            fillHomeTab();

        } else if (event.getSource().equals(internshipsTab.asTabLink().getAnchor())) {
            System.out.println("internship");
            currentPanel = internshipsPanel;
            internshipsPanel.setWidget(new InternshipsByCategoryView());

        } else if (event.getSource().equals(companiesTab.asTabLink().getAnchor())) {
            System.out.println("company");

            currentPanel = companiesPanel;
        } else if (event.getSource().equals(loginLinkedinButton)) {
            toSignin("Linkedin");
        }
    }

    private void fillHomeTab() {
        String currentUserType = Sesi.getCurrentUserType();
        if ("student".equals(currentUserType)) {
            // afisam internshipuri recomandate

            // afisam aplicarile

            // afisam progress details
        } else if ("teacher".equals(currentUserType)) {
            // afisam chestiile de grafice
        } else if ("company".equals(currentUserType)) {
            // afisam aplicarile

            // afisam progress details

            // afisam internshipurile mele
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String[] historyToken = event.getValue().split(WidgetConstants.dataSeparator);

        if (historyToken[0].equals(WidgetConstants.VIEW_TOKEN)) {

        }
    }

    private void toSignin(String provider) {
        SigninService.Util.getInstance().getAuthenticateUrl(provider, Window.Location.getHref(), new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) {
                // TODO Auto-generated method stub
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                Window.Location.replace(result);
            }
        });
    }

}