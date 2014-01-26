package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.widgetinterfaces.ResourceWidgetViewer;
import ro.infoiasi.wad.sesi.core.model.City;
import ro.infoiasi.wad.sesi.core.model.Currency;
import ro.infoiasi.wad.sesi.core.model.Internship;


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

        startDateLabel.setText(internship.getStartDate().toString());
        endDateLabel.setText(internship.getEndDate().toString());
        relocationLabel.setText(Boolean.toString(internship.isOfferingRelocation()));

        preferredGeneralSkillsLabel.setText(Joiner.on(", ").join(internship.getPreferredGeneralSkills()));
        acquiredGeneralSkillsLabel.setText(Joiner.on(", ").join(internship.getAcquiredGeneralSkills()));
        categoryLabel.setText(internship.getCategory().getDescription());

    }


}