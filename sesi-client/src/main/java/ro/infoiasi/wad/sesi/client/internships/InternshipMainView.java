package ro.infoiasi.wad.sesi.client.internships;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Label;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceMainView;
import ro.infoiasi.wad.sesi.core.model.Internship;


public class InternshipMainView extends Composite implements ResourceMainView<Internship> {
    @Override
    public void switchViewMode() {

        if (internshipEditor != null) {
            internship = internshipEditor.save();
            internshipPanel.remove(internshipEditor);
        }

        if (internshipView == null) {
            internshipView = new InternshipView();
        }

        internshipPanel.remove(saveProfileBtn);
        internshipPanel.add(internshipView);
        internshipPanel.add(editProfileBtn);


        internshipView.edit(internship);

    }

    @Override
    public void switchEditMode() {
        if (internshipView != null) {
            internshipPanel.remove(internshipView);
        }

        if (internshipEditor == null) {
            internshipEditor = new InternshipEditor();
        }

        internshipPanel.remove(editProfileBtn);
        internshipPanel.add(internshipEditor);
        internshipPanel.add(saveProfileBtn);

        internshipEditor.edit(internship);
        Sesi.freebase();
    }


    @Override
    public Internship getResource() {
        return internship;
    }

    interface InternshipMainViewUiBinder extends UiBinder<HTMLPanel, InternshipMainView> {
    }

    private static InternshipMainViewUiBinder ourUiBinder = GWT.create(InternshipMainViewUiBinder.class);
    @UiField
    Icon loadingResultsIcon;
    @UiField
    Button editProfileBtn;
    @UiField
    Button saveProfileBtn;
    @UiField
    @Editor.Ignore
    Label errorLabel;
    @UiField
            
    HTMLPanel internshipPanel;
    
    private Internship internship;
    private InternshipView internshipView;
    private InternshipEditor internshipEditor;


    @UiHandler("editProfileBtn")
    public void editProfile(ClickEvent event) {

        switchEditMode();
    }

    @UiHandler("saveProfileBtn")
    public void saveProfile(ClickEvent event) {

        switchViewMode();
//        loadingResultsIcon.setVisible(true);
        errorLabel.setVisible(false);
        internship.setId(Sesi.getCurrentUserId());
//        InternshipsService.App.getInstance().postInternship(internship, new AsyncCallback<Boolean>() {
//            @Override
//            public void onFailure(Throwable caught) {
//                loadingResultsIcon.setVisible(false);
//                errorLabel.setVisible(true);
//                errorLabel.setText("Could not update profile!");
//                System.out.println("Could not update internship because: " + caught);
//
//            }
//
//            @Override
//            public void onSuccess(Boolean result) {
//                loadingResultsIcon.setVisible(false);
//                if (result) {
//                    errorLabel.setVisible(false);
//                    editProfileBtn.setVisible(true);
//                    saveProfileBtn.setVisible(true);
//                } else {
//                    errorLabel.setVisible(true);
//                    errorLabel.setText("Could not update profile!");
//                }
//            }
//        });
    }

    public InternshipMainView(Internship i) {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.internship = i;
        switchViewMode();



    }

}