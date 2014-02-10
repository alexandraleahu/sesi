package ro.infoiasi.wad.sesi.client.teachers;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.Sesi;
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
    @UiField
    Icon loadingResultsIcon;
    @UiField
            @Editor.Ignore
    Label errorLabel;

    private TeacherProfileView profileView;
    private TeacherProfileEditor profileEditor;

    @UiHandler("editProfileBtn")
    public void editProfile(ClickEvent event) {

        switchEditMode();
    }

    @UiHandler("saveProfileBtn")
    public void saveProfile(ClickEvent event) {

        switchViewMode();
        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        teacher.setId(Sesi.getCurrentUserId());
        TeachersService.App.getInstance().updateTeacher(teacher, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
                errorLabel.setVisible(true);
                errorLabel.setText("Could not update profile!");
                System.out.println("Could not update teacher because: " + caught);

            }

            @Override
            public void onSuccess(Boolean result) {
                loadingResultsIcon.setVisible(false);
                if (result) {
                    errorLabel.setVisible(false);
                    editProfileBtn.setVisible(true);
                    saveProfileBtn.setVisible(true);
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Could not update profile!");
                }
            }
        });
    }

    public TeacherMainView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        String teacherId = Sesi.getCurrentUserId();
        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        TeachersService.App.getInstance().getTeacherById(teacherId, new AsyncCallback<Teacher>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
                errorLabel.setVisible(true);
                System.out.println("Could not load teacher because: " + caught);
            }

            @Override
            public void onSuccess(Teacher result) {
                loadingResultsIcon.setVisible(false);
                if (result != null) {
                    errorLabel.setVisible(false);
                    teacher = result;
                    editProfileBtn.setVisible(true);
                    saveProfileBtn.setVisible(true);
                    switchViewMode();
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Profile not found!");
                }


            }
        });


    }
}