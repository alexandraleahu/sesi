package ro.infoiasi.wad.sesi.client.internships;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Modal;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.applications.InternshipApplicationsService;
import ro.infoiasi.wad.sesi.client.commonwidgets.DoubleView;
import ro.infoiasi.wad.sesi.client.commonwidgets.IntegerView;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetViewer;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoView;
import ro.infoiasi.wad.sesi.client.technicalskills.TechnicalSkillView;
import ro.infoiasi.wad.sesi.core.model.*;


public class InternshipView extends Composite implements ResourceWidgetViewer<Internship> {
    interface InternshipViewUiBinder extends UiBinder<HTMLPanel, InternshipView> {

    }

    private static InternshipViewUiBinder ourUiBinder = GWT.create(InternshipViewUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Internship, InternshipView> {
    }

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Path("name")
    Label internshipTitleHeader;

    @UiField
    Hyperlink companyLink;

    @UiField
    @Path("openings")
    IntegerView openingsLabel;

    @UiField
    @Ignore
    Label startDateLabel;

    @UiField
    @Ignore
    Label endDateLabel;

    @UiField
    @Path("applicationsCount")
    IntegerView applicationsLabel;

    @UiField
    @Ignore
    Label categoryLabel;

    @UiField
    @Path("description")
    Label descriptionLabel;

    @UiField
    @Ignore
    Label preferredGeneralSkillsLabel;

    @UiField
    @Ignore
    Label relocationLabel;

    @UiField
    @Path("salaryCurrency")
    OntologyExtraInfoView<Currency> salaryCurrencyLabel;

    @UiField
    @Path("salaryValue")
    DoubleView salaryValueLabel;

    @UiField
    @Ignore
    Label acquiredGeneralSkillsLabel;

    @UiField
    @Path("city")
    OntologyExtraInfoView<City> cityLink;

    @UiField
    @Path("preferredTechnicalSkills")
    TechnicalSkillView preferredTechnicalSkillsTable;

    @UiField
    @Path("acquiredTechnicalSkills")
    TechnicalSkillView acquiredTechnicalSkillsTable;
    @UiField
    Button applyBtn;
    @UiField
    @Ignore
    TextArea motivationArea;
    @UiField
    Button submitButton;
    @UiField
    Modal motivationModal;
    @UiField
    Icon loadingResultsIcon;
    @UiField
    @Ignore
    com.github.gwtbootstrap.client.ui.Label errorLabel;

    // Create the Driver
   private Internship internship;
    public InternshipView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    @Override
    public void edit(Internship internship) {
        this.internship = internship;
        driver.edit(internship);

        if (internship.getCompany() != null)
            companyLink.setText(internship.getCompany().getName());

        startDateLabel.setText(internship.getStartDate().toString());
        endDateLabel.setText(internship.getEndDate().toString());
        relocationLabel.setText(internship.isOfferingRelocation() ? "yes" : "no");

        preferredGeneralSkillsLabel.setText(Joiner.on(", ").join(internship.getPreferredGeneralSkills()));
        acquiredGeneralSkillsLabel.setText(Joiner.on(", ").join(internship.getAcquiredGeneralSkills()));
        categoryLabel.setText(internship.getCategory().getDescription());

        if (!UserAccountType.STUDENT_ACCOUNT.equals(Sesi.getCurrentUserType())) {

             applyBtn.removeFromParent();
        }

    }

    @UiHandler("applyBtn")
    public void appyToInternship(ClickEvent e) {
         motivationModal.show();
    }

    @UiHandler("submitButton")
    public void submitApplication(ClickEvent e) {
        String studentId = Sesi.getCurrentUserId();
        String internshipId = internship.getId();
        String motivation = motivationArea.getText();
        motivationModal.hide();
        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        InternshipApplicationsService.App.getInstance().createApplication(studentId, internshipId, motivation, new AsyncCallback<InternshipApplication>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(true);
                errorLabel.setVisible(true);
            }

            @Override
            public void onSuccess(InternshipApplication result) {
                loadingResultsIcon.setVisible(true);
                errorLabel.setVisible(false);
                History.newItem(result.getRelativeUri());
            }
        });
    }


}