package ro.infoiasi.wad.sesi.client.companies;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.github.gwtbootstrap.client.ui.constants.LabelType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.ResourceListVew;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
import ro.infoiasi.wad.sesi.client.internships.InternshipsService;
import ro.infoiasi.wad.sesi.client.students.StudentsService;
import ro.infoiasi.wad.sesi.client.students.StudentsServiceAsync;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import java.util.List;

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
    @UiField
    HTMLPanel internshipPanel;
    @UiField
    HTMLPanel progressDetailsPanel;
    @UiField
    HTMLPanel applicationsPanel;

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
        initApplicationsPanel();
        initProgressDetailsTab();
        initInternships();
    }

    private void initInternships() {
        if(company == null) {
            return;
        }
        CompaniesService.App.getInstance().getCompanyInternships(company.getId(), new AsyncCallback<List<Internship>>() {
            @Override
            public void onFailure(Throwable caught) {

                internshipPanel.add(new Label(LabelType.IMPORTANT, "Could not load internships!"));
            }

            @Override
            public void onSuccess(List<Internship> result) {

                ResourceListVew<Internship> resourceListVew = new ResourceListVew<Internship>();
                internshipPanel.add(resourceListVew);

                resourceListVew.setValue(result);
            }
        });
    }

    private void initProgressDetailsTab() {
        if(company == null) {
            return;
        }
        CompaniesService.App.getInstance().getCompanyProgressDetails(company.getId(), new AsyncCallback<List<InternshipProgressDetails>>() {
            @Override
            public void onFailure(Throwable caught) {

                progressDetailsPanel.add(new Label(LabelType.IMPORTANT, "Could not load progress details!"));
            }

            @Override
            public void onSuccess(List<InternshipProgressDetails> result) {

                ResourceListVew<InternshipProgressDetails> resourceListVew = new ResourceListVew<InternshipProgressDetails>();
                progressDetailsPanel.add(resourceListVew);

                resourceListVew.setValue(result);
            }
        });
    }

    private void initApplicationsPanel() {
        if(company == null) {
            return;
        }
        CompaniesService.App.getInstance().getCompanyApplications(company.getId(), new AsyncCallback<List<InternshipApplication>>() {
            @Override
            public void onFailure(Throwable caught) {

                applicationsPanel.add(new Label(LabelType.IMPORTANT, "Could not load internship applications!"));
            }

            @Override
            public void onSuccess(List<InternshipApplication> result) {

                ResourceListVew<InternshipApplication> resourceListVew = new ResourceListVew<InternshipApplication>();
                applicationsPanel.add(resourceListVew);

                resourceListVew.setValue(result);
            }
        });

    }
}