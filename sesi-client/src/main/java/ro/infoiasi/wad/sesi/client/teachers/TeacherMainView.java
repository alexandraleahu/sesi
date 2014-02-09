package ro.infoiasi.wad.sesi.client.teachers;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
import ro.infoiasi.wad.sesi.client.reports.ReportEditor;
import ro.infoiasi.wad.sesi.core.model.Teacher;


public class TeacherMainView extends Composite implements ResourceMainView<Teacher> {


    @Override
    public void switchViewMode() {

        if (profileEditor != null) {
            teacher = profileEditor.save();
            profilePanel.remove(profileEditor);
        }

        if (profileView == null) {
            profileView = new TeacherProfileView();
        }

        profilePanel.remove(saveProfileBtn);
        profilePanel.add(profileView);
        profilePanel.add(editProfileBtn);

        profileView.edit(teacher);

    }

    @Override
    public void switchEditMode() {
        if (profileView != null) {
            profilePanel.remove(profileView);
        }

        if (profileEditor == null) {
            profileEditor = new TeacherProfileEditor();
        }

        profilePanel.remove(editProfileBtn);
        profilePanel.add(profileEditor);
        profilePanel.add(saveProfileBtn);

        profileEditor.edit(teacher);
    }

    @Override
    public Teacher getResource() {
        return teacher;
    }


    interface TeacherViewUiBinder extends UiBinder<TabPanel, TeacherMainView> {
    }

    private static TeacherViewUiBinder ourUiBinder = GWT.create(TeacherViewUiBinder.class);

    private Teacher teacher;

    @UiField
    @Editor.Ignore
    ReportEditor reportEditor;


    @UiField
    Button editProfileBtn;
    @UiField
    Button saveProfileBtn;
    @UiField
    HTMLPanel profilePanel;

    private TeacherProfileView profileView;
    private TeacherProfileEditor profileEditor;

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

    public TeacherMainView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        // TODO make a call and init teacher
        teacher = new Teacher();
        switchViewMode();


    }
}