package ro.infoiasi.wad.sesi.client.students;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.commonwidgets.IntegerView;
import ro.infoiasi.wad.sesi.client.commonwidgets.ProjectsView;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetViewer;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoView;
import ro.infoiasi.wad.sesi.client.technicalskills.TechnicalSkillView;
import ro.infoiasi.wad.sesi.core.model.Degree;
import ro.infoiasi.wad.sesi.core.model.Faculty;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.University;

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
    @Path("studies.faculty")
    OntologyExtraInfoView<Faculty> faculty;

    @UiField
    @Path("studies.faculty.university")
    OntologyExtraInfoView<University> university;

    @UiField
    @Path("studies.degree")
    OntologyExtraInfoView<Degree> degree;

    @UiField
    @Path("studies.yearOfStudy")
    IntegerView yearOfStudy;
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

        generalSkillsLabel.setText(Joiner.on(",").join(student.getGeneralSkills()));
    }

}