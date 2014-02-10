package ro.infoiasi.wad.sesi.client.companies;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.constants.LabelType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.commonwidgets.ResourceListVew;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
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
    @UiField
    Icon loadingResultsIcon;
    @UiField
    Label errorLabel;
    @UiField
    Tab progressDetailsTab;
    @UiField
    Tab applicationsTab;
    @UiField
    Tab profileTab;

    private CompanyView profileView;
    private CompanyEditor profileEditor;

    @UiHandler("editProfileBtn")
    public void editProfile(ClickEvent event) {

        switchEditMode();
    }

    @UiHandler("saveProfileBtn")
    public void saveProfile(ClickEvent event) {

        switchViewMode();
        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        company.setId(Sesi.getCurrentUserId());
        CompaniesService.App.getInstance().updateCompany(company, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
                errorLabel.setVisible(true);
                errorLabel.setText("Could not update profile!");
                System.out.println("Could not update company because: " + caught);

            }

            @Override
            public void onSuccess(Boolean result) {
                loadingResultsIcon.setVisible(false);
                if (result) {
                    errorLabel.setVisible(false);
                    editProfileBtn.setVisible(true);
                    saveProfileBtn.setVisible(true);
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Could not update profile!");
                }
            }
        });
    }

    public CompanyMainView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        String companyId = Sesi.getCurrentUserId();
        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);

        CompaniesService.App.getInstance().getCompanyById(companyId, new AsyncCallback<Company>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
//                errorLabel.setVisible(true);
//                System.out.println("Could not load company because: " + caught);
                company = new Company();
                company.setActive(true);
                company.setCommunityRating(4);
                company.setDescription("A company dealing with Java technologies, for business clients.");
                company.setName("VirtualComp");
                company.setSiteUrl("http://virtualcomp.ro");
                company.setId("virtualcomp");
                editProfileBtn.setVisible(true);
                saveProfileBtn.setVisible(true);
                switchViewMode();

                initApplicationsPanel();
                initProgressDetailsTab();
                initInternships();

            }

            @Override
            public void onSuccess(Company result) {
                loadingResultsIcon.setVisible(false);
                if (result != null) {
                    errorLabel.setVisible(false);
                    company = result;
                    editProfileBtn.setVisible(true);
                    saveProfileBtn.setVisible(true);
                    switchViewMode();

                    initApplicationsPanel();
                    initProgressDetailsTab();
                    initInternships();
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Profile not found!");
                }


            }
        });


    }

    private void initInternships() {
        if(company == null) {
            return;
        }

        ResourceListVew<Internship> resourceListVew = new ResourceListVew<Internship>();
        internshipPanel.add(resourceListVew);


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