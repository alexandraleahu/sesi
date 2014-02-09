package ro.infoiasi.wad.sesi.client.teachers;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoEditor;
import ro.infoiasi.wad.sesi.core.model.*;

import java.util.List;


public class TeacherProfileEditor extends Composite implements ResourceWidgetEditor<Teacher> {
    @Override
    public Teacher save() {
        Teacher teacher = driver.flush();
        // taking the courses
        List<Course> courses = Lists.newArrayList();

        for (int i = 0; i < coursesPanel.getWidgetCount(); i++) {
            OntologyExtraInfoEditor<Course> editor = (OntologyExtraInfoEditor<Course>) (coursesPanel.getWidget(i));
            courses.add(editor.getValue());
        }

        teacher.setCourses(courses);

        return teacher;
    }

    @Override
    public void edit(Teacher bean) {
        driver.edit(bean);
        if (bean != null && bean.getCourses() != null && coursesPanel.getWidgetCount() == 0) {

            for (Course course : bean.getCourses()) {
                OntologyExtraInfoEditor<Course> courseEditor = addCourse(null);
                courseEditor.setValue(course);
            }
        }
    }

    interface TeacherEditorUiBinder extends UiBinder<HTMLPanel, TeacherProfileEditor> {
    }

    private static TeacherEditorUiBinder ourUiBinder = GWT.create(TeacherEditorUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Teacher, TeacherProfileEditor> {}

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Path("name")
    TextBox nameTextBox;
    @UiField
    @Path("title")
    TextBox titleTextBox;

    @UiField
    Button importProfileBtn;
    @UiField
    @Ignore
    TextBox siteUrlBox;
    @UiField
    Icon loadingResultsIcon;

    @UiField
    @Path("faculty")
    OntologyExtraInfoEditor<Faculty> facultyEditor;
    @UiField
    @Path("faculty.university")
    OntologyExtraInfoEditor<University> universityEditor;
    @UiField
    @Path("siteUrl")
    TextBox siteEditor;
    @UiField
    HTMLPanel coursesPanel;
    @UiField
    Button addCourseBtn;
    @UiField
    @Ignore
    Label errorLabel;

    public TeacherProfileEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));

        driver.initialize(this);

    }

    @UiHandler("addCourseBtn")
    public OntologyExtraInfoEditor<Course> addCourse(ClickEvent e) {
        OntologyExtraInfoEditor<Course> courseEditor = new OntologyExtraInfoEditor<Course>(true);
        courseEditor.setValue(new Course());
        coursesPanel.add(courseEditor);

        return courseEditor;
    }

    @UiHandler("importProfileBtn")
    public void importProfile(ClickEvent e) {

        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        TeachersService.App.getInstance().importTeacherProfile(siteUrlBox.getText(), new AsyncCallback<Teacher>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
                errorLabel.setVisible(true);
            }

            @Override
            public void onSuccess(Teacher result) {
                loadingResultsIcon.setVisible(false);
                errorLabel.setVisible(false);
                edit(result);
            }
        });
    }
}