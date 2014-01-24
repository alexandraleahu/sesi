package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.widgetinterfaces.ResourceWidgetViewer;
import ro.infoiasi.wad.sesi.core.model.Internship;


public class InternshipView extends Composite implements ResourceWidgetViewer<Internship> {
    interface InternshipViewUiBinder extends UiBinder<HTMLPanel, InternshipView> {

    }
    private static InternshipViewUiBinder ourUiBinder = GWT.create(InternshipViewUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Internship, InternshipView> {}

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Path("name")
    Label internshipTitleHeader;

    @UiField
    Anchor companyLink;

    @UiField
    @Path("openings")
    Label openingsLabel;

    @UiField
    Label startDateLabel;

    @UiField
    Label endDateLabel;

    @UiField
    @Path("applicationsCount")
    Label applicationsLabel;

    @UiField
    @Path("category")
    Label categoryLabel;

    @UiField
    @Path("description")
    Label descriptionLabel;

    @UiField
    Label preferredGeneralSkillsLabel;

    @UiField
    Label relocationLabel;

    @UiField
    @Path("salaryCurrency.name")
    Label salaryCurrencyLabel;

    @UiField
    @Path("salaryValue")
    Label salaryValueLabel;

    @UiField
    Label acquiredGeneralSkillsLabel;

    @UiField
    Anchor cityLink;

    // Create the Driver

    public InternshipView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    @Override
    public void edit(Internship internship) {
        driver.edit(internship);
        if (internship.getCompany() != null)
            companyLink.setText(internship.getCompany().getName());

        companyLink.setText("VirtualComp");
        companyLink.setHref("http://google.com");

        startDateLabel.setText(internship.getStartDate().toString());
        endDateLabel.setText(internship.getEndDate().toString());
        relocationLabel.setText(Boolean.toString(internship.isOfferingRelocation()));

        preferredGeneralSkillsLabel.setText(Joiner.on(", ").join(internship.getPreferredGeneralSkills()));
        acquiredGeneralSkillsLabel.setText(Joiner.on(", ").join(internship.getAcquiredGeneralSkills()));

    }


}