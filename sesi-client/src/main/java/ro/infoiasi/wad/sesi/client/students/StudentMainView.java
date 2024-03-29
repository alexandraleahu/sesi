package ro.infoiasi.wad.sesi.client.students;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.client.ui.constants.LabelType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.commonwidgets.ResourceListVew;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;

import java.util.List;

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
    @UiField
    Icon loadingResultsIcon;
    @UiField
    @Editor.Ignore
    Label errorLabel;
    @UiField
    HTMLPanel recommendedPanel;
    @UiField
    HTMLPanel progressDetailsPanel;
    @UiField
    HTMLPanel applicationsPanel;
    @UiField
    Tab progressDetailsTab;
    @UiField
    Tab recommendedInternshipsTab;
    @UiField
    Tab applicationsTab;
    @UiField
    ResourceListVew<Internship> recommendedList;
    @UiField
    ResourceListVew<InternshipProgressDetails> progressDetailsList;
    @UiField
    ResourceListVew<InternshipApplication> applicationsList;

    private StudentView profileView;
    private StudentEditor profileEditor;

    @UiHandler("editProfileBtn")
    public void editProfile(ClickEvent event) {

        switchEditMode();
    }

    @UiHandler("saveProfileBtn")
    public void saveProfile(ClickEvent event) {
        // TODO call the service
        switchViewMode();
    }


    public StudentMainView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        String studentId = Sesi.getCurrentUserId();
        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        recommendedInternshipsTab.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                initRecommendedInternships();
            }
        });
        applicationsTab.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                initApplicationsPanel();
            }
        });
        progressDetailsTab.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                initProgressDetailsTab();
            }
        });
        StudentsService.App.getInstance().getStudentById(studentId, new AsyncCallback<Student>() {
            @Override
            public void onFailure(Throwable caught) {
                loadingResultsIcon.setVisible(false);
                errorLabel.setVisible(true);
                System.out.println("Could not load student because: " + caught);
            }

            @Override
            public void onSuccess(Student result) {
                loadingResultsIcon.setVisible(false);
                if (result != null) {
                    errorLabel.setVisible(false);
                    student = result;
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

    private void initRecommendedInternships() {
        if(student == null) {
            return;
        }
        StudentsService.App.getInstance().getRecommendedInternships(student.getId(), new AsyncCallback<List<Internship>>() {
            @Override
            public void onFailure(Throwable caught) {

                recommendedPanel.add(new Label(LabelType.IMPORTANT, "Could not load recommended internships!"));
            }

            @Override
            public void onSuccess(List<Internship> result) {


                recommendedList.setValue(result);
            }
        });
    }

    private void initProgressDetailsTab() {
        if(student == null) {
            return;
        }
        StudentsService.App.getInstance().getStudentInternshipProgressDetails(student.getId(), new AsyncCallback<List<InternshipProgressDetails>>() {
            @Override
            public void onFailure(Throwable caught) {

                progressDetailsPanel.add(new Label(LabelType.IMPORTANT, "Could not load internship progress details!"));
            }

            @Override
            public void onSuccess(List<InternshipProgressDetails> result) {


                progressDetailsList.setValue(result);
            }
        });
    }

    private void initApplicationsPanel() {
        if(student == null) {
            return;
        }
        StudentsService.App.getInstance().getStudentApplications(student.getId(), new AsyncCallback<List<InternshipApplication>>() {
            @Override
            public void onFailure(Throwable caught) {

                applicationsPanel.add(new Label(LabelType.IMPORTANT, "Could not load internship applications!"));
            }

            @Override
            public void onSuccess(List<InternshipApplication> result) {


                applicationsList.setValue(result);
            }
        });

    }
}