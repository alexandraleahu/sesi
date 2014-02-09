package ro.infoiasi.wad.sesi.client.students;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
import ro.infoiasi.wad.sesi.core.model.Student;

public class StudentMainView extends Composite implements ResourceMainView<Student> {

    @Override
    public void switchViewMode() {
        if (profileEditor != null) {
            student = profileEditor.save();
            profilePanel.remove(profileEditor);
        }
        if (profileView == null) {
            profileView = new StudentView();
        }
        profilePanel.remove(saveProfileBtn);
        profilePanel.add(profileView);
        profilePanel.add(editProfileBtn);

        profileView.edit(student);
    }

    @Override
    public void switchEditMode() {
        if (profileView != null) {
            profilePanel.remove(profileView);
        }

        if (profileEditor == null) {
            profileEditor = new StudentEditor();
        }

        profilePanel.remove(editProfileBtn);
        profilePanel.add(profileEditor);
        profilePanel.add(saveProfileBtn);

        profileEditor.edit(student);
    }

    @Override
    public Student getResource() {
        return student;
    }

    interface StudentMainViewUiBinder extends UiBinder<TabPanel, StudentMainView> {
    }

    private static StudentMainViewUiBinder ourUiBinder = GWT.create(StudentMainViewUiBinder.class);
    private Student student;
    @UiField
    Button editProfileBtn;
    @UiField
    Button saveProfileBtn;
    @UiField
    HTMLPanel profilePanel;

    private StudentView profileView;
    private StudentEditor profileEditor;

    @UiHandler("editProfileBtn")
    public void editProfile(ClickEvent event) {
        // TODO call the service
        switchEditMode();
    }

    @UiHandler("saveProfileBtn")
    public void saveProfile(ClickEvent event) {
        // TODO call the service
        switchViewMode();
    }

    //only for testing purposes, TODO remove
    public StudentMainView(Student student) {
        initWidget(ourUiBinder.createAndBindUi(this));
        // TODO make a call and init student
        this.student = student;
        switchViewMode();
    }

    public StudentMainView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        // TODO make a call and init student
        student = new Student();
        switchViewMode();
    }
}