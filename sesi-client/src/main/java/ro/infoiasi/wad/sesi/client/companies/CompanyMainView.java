package ro.infoiasi.wad.sesi.client.companies;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
import ro.infoiasi.wad.sesi.core.model.Company;

public class CompanyMainView extends Composite implements ResourceMainView<Company> {
    private Company company;

    @Override
    public void switchViewMode() {

        if (profileEditor != null) {
            company = profileEditor.save();
            profilePanel.remove(profileEditor);
        }

        if (profileView == null) {
            profileView = new CompanyView();
        }

        profilePanel.remove(saveProfileBtn);
        profilePanel.add(profileView);
        profilePanel.add(editProfileBtn);

        profileView.edit(company);
    }

    @Override
    public void switchEditMode() {
        if (profileView != null) {
            profilePanel.remove(profileView);
        }

        if (profileEditor == null) {
            profileEditor = new CompanyEditor();
        }

        profilePanel.remove(editProfileBtn);
        profilePanel.add(profileEditor);
        profilePanel.add(saveProfileBtn);

        profileEditor.edit(company);
    }

    @Override
    public Company getResource() {
        return company;
    }

    interface CompanyMainViewUiBinder extends UiBinder<TabPanel, CompanyMainView> {
    }

    private static CompanyMainViewUiBinder ourUiBinder = GWT.create(CompanyMainViewUiBinder.class);
    @UiField
    Button saveProfileBtn;
    @UiField
    Button editProfileBtn;
    @UiField
    HTMLPanel profilePanel;

    private CompanyView profileView;
    private CompanyEditor profileEditor;

    @UiHandler("editProfileBtn")
    public void editProfile(ClickEvent event) {
        // TODO call the service
        switchEditMode();
    }

    @UiHandler("saveProfileBtn")
    public void saveProfile(ClickEvent event) {
        // TODO call the service
        switchViewMode();
    }

    public CompanyMainView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        // TODO make a call and init company
        company = new Company();
        switchViewMode();
    }


    public CompanyMainView(Company company) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.company = company;
        switchViewMode();
    }
}