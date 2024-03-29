package ro.infoiasi.wad.sesi.client.companies;

import com.github.gwtbootstrap.client.ui.*;
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
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.commonwidgets.ResourceListVew;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
import ro.infoiasi.wad.sesi.client.internships.InternshipEditor;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import java.util.List;

public class CompanyMainView extends Composite implements ResourceMainView<Company> {
    private Company company;
    private List<Internship> internships;

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
    @UiField
    Button newInternship;
    @UiField
    HTMLPanel newInternshipsPanel;
    @UiField
    ResourceListVew<Internship> internshipsList;
    @UiField
    ResourceListVew<InternshipProgressDetails> progressDetailsList;
    @UiField
    ResourceListVew<InternshipApplication> applicationsList;
    @UiField
    Tab internshipsTab;
    @UiField
    Button publishBtn;

    private CompanyView profileView;
    private CompanyEditor profileEditor;

    private InternshipEditor editor = new InternshipEditor();

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

    @UiHandler("publishBtn")
    public void publishInternship(ClickEvent e) {
        final Internship i = editor.save();
//        InternshipsService.App.getInstance().postInternship(i, new AsyncCallback<Internship>() {
//            @Override
//            public void onFailure(Throwable caught) {
//                System.out.println(caught.getMessage() + Arrays.toString(caught.getStackTrace()));
//                newInternshipsPanel.clear();
//                internshipPanel.add(new Label(LabelType.IMPORTANT, "Could not create internship!"));
//                publishBtn.setVisible(false);
//                newInternship.setVisible(true);
//            }
//
//            @Override
//            public void onSuccess(Internship result) {

                i.setId("005");
                internships.add(i);
                internshipsList.setValue(internships);
                newInternshipsPanel.clear();
                publishBtn.setVisible(false);
                newInternship.setVisible(true);
//
//            }
//        });
    }
    @UiHandler("newInternship")
    public void createNewInternship(ClickEvent e) {

        editor.edit(new Internship());
        newInternshipsPanel.add(editor);
        Sesi.freebase();
        newInternship.setVisible(false);
        publishBtn.setVisible(true);


    }
    public CompanyMainView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        String companyId = Sesi.getCurrentUserId();
        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        internshipsTab.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                initInternships();
            }
        });
        applicationsTab.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                initApplicationsPanel();
            }
        });
        progressDetailsTab.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                initProgressDetailsTab();
            }
        });
        CompaniesService.App.getInstance().getCompanyById(companyId, new AsyncCallback<Company>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
                errorLabel.setVisible(true);
                System.out.println("Could not load company because: " + caught);


            }

            @Override
            public void onSuccess(Company result) {
                loadingResultsIcon.setVisible(false);
                if (result != null) {
                    errorLabel.setVisible(false);
                    company = result;
//                    editProfileBtn.setVisible(true);
//                    saveProfileBtn.setVisible(true);
                    switchViewMode();

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

        CompaniesService.App.getInstance().getCompanyInternships(company.getId(), new AsyncCallback<List<Internship>>() {
            @Override
            public void onFailure(Throwable caught) {

                internshipPanel.add(new Label(LabelType.IMPORTANT, "Could not load internships!"));
            }

            @Override
            public void onSuccess(List<Internship> result) {

                internships = result;
                internshipsList.setValue(internships);
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

                progressDetailsList.setValue(result);
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

                applicationsList.setValue(result);
            }
        });

    }
}