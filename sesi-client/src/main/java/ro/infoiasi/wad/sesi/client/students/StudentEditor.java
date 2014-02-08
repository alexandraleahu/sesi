package ro.infoiasi.wad.sesi.client.students;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.core.model.KnowledgeLevel;
import ro.infoiasi.wad.sesi.core.model.Student;

public class StudentEditor extends Composite implements ResourceWidgetEditor<Student>,
        ValueChangeHandler<KnowledgeLevel>, ClickHandler {
    @Override
    public void onClick(ClickEvent clickEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onValueChange(ValueChangeEvent<KnowledgeLevel> knowledgeLevelValueChangeEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Student save() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void edit(Student bean) {
        driver.edit(bean);
    }

    interface StudentEditorUiBinder extends UiBinder<HTMLPanel, StudentEditor> {
    }

    private static StudentEditorUiBinder ourUiBinder = GWT.create(StudentEditorUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Student, StudentEditor> {
    }

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Editor.Path("description")
    TextBox description;

    @UiField
    @Editor.Ignore
    TextArea generalSkillsArea;

    @UiField
    @Editor.Ignore
    TextBox freebaseTechnicalSkillsBox;

    @UiField
    @Path("name")
    TextBox name;
    @UiField
    @Ignore
    Object yearOfStudy;
    @UiField
    @Ignore
    TextArea degreeId;
    @UiField
    @Ignore
    TextBox degree;
    @UiField
    @Ignore
    TextArea universityId;
    @UiField
    @Ignore
    TextBox university;
    @UiField
    @Ignore
    TextBox repository;
    @UiField
    @Ignore
    TextBox projectUrl;
    @UiField
    @Ignore
    TextBox projectDescription;
    @UiField
    @Ignore
    TextBox projectName;
//    @UiField
//    @Ignore
//    Button addProject;
//    @UiField
//    Button deleteProject;
//    @UiField
//    ProjectsView projects;

    public StudentEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);
    }
}