package ro.infoiasi.wad.sesi.client.reports;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.NamesListEditor;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.WidgetEditor;
import ro.infoiasi.wad.sesi.client.companies.CompaniesService;
import ro.infoiasi.wad.sesi.client.companies.CompaniesServiceAsync;
import ro.infoiasi.wad.sesi.client.schools.SchoolsService;
import ro.infoiasi.wad.sesi.client.schools.SchoolsServiceAsync;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ReportEditor extends Composite implements WidgetEditor<ReportBean>,
        ValueChangeHandler<ReportBean.MainResourceType>,
        ClickHandler {

    @Override
    public ReportBean save() {
        return driver.flush();
    }

    @Override
    public void edit(ReportBean bean) {
        driver.edit(bean);
    }




    interface ReportEditorUiBinder extends UiBinder<HTMLPanel, ReportEditor> {


    }

    private static ReportEditorUiBinder ourUiBinder = GWT.create(ReportEditorUiBinder.class);
    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<ReportBean, ReportEditor> {}

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField(provided = true)
    @Path("resourceType")
    ValueListBox<ReportBean.MainResourceType> mainResourceBox = new ValueListBox<ReportBean.MainResourceType>(new Renderer<ReportBean.MainResourceType>() {
        @Override
        public String render(ReportBean.MainResourceType resourceType) {
            return resourceType == null ? "" : resourceType.toString();
        }

        @Override
        public void render(ReportBean.MainResourceType resourceType, Appendable appendable) throws IOException {
            if (resourceType != null)
                appendable.append(resourceType.toString());
        }
    });

    @UiField
    Icon loadingIcon;

    @UiField
    @Ignore
    Label firstFilterLabel;

    @Path("companyNames")
    NamesListEditor companyNamesList = new NamesListEditor();

    @UiField
    WellForm mainForm;

    @UiField
    SimplePanel errorPanel;

    @UiField
    @Path("numericRestriction")
    NumericRestrictionEditor numericRestrictionEditor;

    @UiField (provided = true)
    @Path("studentInternshipRelation")
    ValueListBox<ReportBean.StudentInternshipRelationType> relationBox = new ValueListBox<ReportBean.StudentInternshipRelationType>(

            new Renderer<ReportBean.StudentInternshipRelationType>() {
                @Override
                public String render(ReportBean.StudentInternshipRelationType resourceType) {
                    return resourceType == null ? "" : resourceType.getDescription();
                }

                @Override
                public void render(ReportBean.StudentInternshipRelationType resourceType, Appendable appendable) throws IOException {
                    if (resourceType != null)
                        appendable.append(resourceType.getDescription());
                }
            }
    );

    @UiField
    @Path("statuses")
    NamesListEditor statusList;

    @UiField
    @Ignore
    Label secondFilterLabel;

    @Path("facultyNames")
    NamesListEditor facultyNamesList = new NamesListEditor();

    @UiField
    @Path("startDate")
    DateBox startDateBox;

    @UiField
    @Path("endDate")
    DateBox endDateBox;

    @UiField
    Button submitBtn;

    @UiField
    SimplePanel firstFilterNamesPanel;

    @UiField
    SimplePanel secondFilterNamesPanel;

    @UiField (provided = true)
    CellTable resultsTable = new CellTable(5);

    public ReportEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();


    }

    private volatile boolean failure;

    private void wireUiElements() {
        mainResourceBox.addValueChangeHandler(this);
        mainResourceBox.setAcceptableValues(Arrays.asList(ReportBean.MainResourceType.values()));

        relationBox.setAcceptableValues(Arrays.asList(ReportBean.StudentInternshipRelationType.values()));
        relationBox.addValueChangeHandler(new ValueChangeHandler<ReportBean.StudentInternshipRelationType>() {
            @Override
            public void onValueChange(ValueChangeEvent<ReportBean.StudentInternshipRelationType> event) {

                switch (event.getValue()) {
                   case Applications:

                       statusList.setValue(Lists.transform(InternshipApplication.POSSIBLE_STATUSES, new Function<StudentInternshipRelation.Status, String>() {
                           @Nullable
                           @Override
                           public String apply(@Nullable StudentInternshipRelation.Status input) {
                               return input.toString();
                           }
                       }));
                       break;
                   case InProgressInternships:

                       statusList.setValue(Lists.transform(InternshipProgressDetails.POSSIBLE_STATUSES, new Function<StudentInternshipRelation.Status, String>() {
                           @Nullable
                           @Override
                           public String apply(@Nullable StudentInternshipRelation.Status input) {
                               return input.toString();
                           }
                       }));
                       break;
                   default:
                       break;
                }
            }
        });

        submitBtn.addClickHandler(this);

    }

    @Override
    public void onClick(ClickEvent event) {

        ReportBean bean = save();
        System.out.println(bean);
    }

    @Override
    public void onValueChange(ValueChangeEvent<ReportBean.MainResourceType> event) {
        failure = false;
        if (event.getValue() != null) {
            loadingIcon.setVisible(true);

            errorPanel.setVisible(false);
            mainForm.setVisible(false);

            displayAllCompaniesNames(event.getValue());
            displayAllFacultiesNames(event.getValue());
        }

    }

    private void displayAllFacultiesNames(final ReportBean.MainResourceType resourceType) {

        // work with schools async service

        SchoolsServiceAsync schoolsServiceAsync = SchoolsService.App.getInstance();

        schoolsServiceAsync.getAllFacultyNames(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingIcon.setVisible(false);
                errorPanel.setVisible(true);
                mainForm.setVisible(false);
                failure = true;

            }

            @Override
            public void onSuccess(List<String> result) {
                if (failure) {
                    return;
                }
                loadingIcon.setVisible(false);
                mainForm.setVisible(true);
                facultyNamesList.setValue(result);



                if (resourceType == ReportBean.MainResourceType.Students) {

                    firstFilterNamesPanel.setWidget(facultyNamesList);
                    firstFilterLabel.setText("From schools");

                }   else {

                    secondFilterNamesPanel.setWidget(facultyNamesList);
                    secondFilterLabel.setText("of students from schools");
                }


            }
        });
    }

    private void displayAllCompaniesNames(final ReportBean.MainResourceType resourceType) {

        // work with companies async service

        CompaniesServiceAsync companiesServiceAsync = CompaniesService.App.getInstance();

        companiesServiceAsync.getAllCompaniesNames(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingIcon.setVisible(false);
                errorPanel.setVisible(true);
                mainForm.setVisible(false);
                failure = true;
            }

            @Override
            public void onSuccess(List<String> result) {
                if (failure) {
                    return;
                }
                loadingIcon.setVisible(false);
                mainForm.setVisible(true);
                companyNamesList.setValue(result);

                if (resourceType == ReportBean.MainResourceType.Internships) {

                    firstFilterNamesPanel.setWidget(companyNamesList);
                    firstFilterLabel.setText("From companies");


                }   else {

                    secondFilterNamesPanel.setWidget(companyNamesList);
                    secondFilterLabel.setText("to companies");


                }

            }
        });
    }

}