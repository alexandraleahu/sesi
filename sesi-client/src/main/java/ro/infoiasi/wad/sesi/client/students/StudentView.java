package ro.infoiasi.wad.sesi.client.students;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.commonwidgets.ProjectsView;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetViewer;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoView;
import ro.infoiasi.wad.sesi.client.technicalskills.TechnicalSkillView;
import ro.infoiasi.wad.sesi.core.model.Student;

public class StudentView extends Composite implements ResourceWidgetViewer<Student> {
    interface StudentViewUiBinder extends UiBinder<HTMLPanel, StudentView> {
    }

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Student, StudentView> {
    }

    // Create the Driver
    Driver driver = GWT.create(Driver.class);
    private static StudentViewUiBinder ourUiBinder = GWT.create(StudentViewUiBinder.class);

    @UiField
    @Path("name")
    Label studentNameHeader;

    @UiField
    @Path("description")
    Label descriptionLabel;

    @UiField
    @Ignore
    Label generalSkillsLabel;
    @UiField
    @Path("technicalSkills")
    TechnicalSkillView technicalSkillsTable;

    @UiField
    @Ignore
    Label faculty;

    @UiField
    @Ignore
    OntologyExtraInfoView university;

    @UiField
    @Ignore
    OntologyExtraInfoView degree;

    @UiField
    @Ignore
    Label yearOfStudy;
    @UiField
    @Path("projects")
    ProjectsView projects;

    public StudentView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    @Override
    public void edit(Student student) {
        driver.edit(student);

        studentNameHeader.setText(student.getName());
        descriptionLabel.setText(student.getDescription());
        generalSkillsLabel.setText(Joiner.on(",").join(student.getGeneralSkills()));
        technicalSkillsTable.setValue(student.getTechnicalSkills());
        faculty.setText(student.getStudies().getFaculty().getName());
        degree.setValue(student.getStudies().getDegree());
        yearOfStudy.setText(student.getStudies().getYearOfStudy().toString());
        university.setValue(student.getStudies().getFaculty().getUniversity());
    }

}