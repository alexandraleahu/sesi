package ro.infoiasi.wad.sesi.client.applications;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import java.io.IOException;
import java.util.Date;


public class InternshipApplicationViewAndEditor extends Composite implements ResourceWidgetEditor<InternshipApplication> {
    @Override
    public InternshipApplication save() {

        ourBean = driver.flush();
        if (ourBean != null) {

            statusView.setText(ourBean.getStatus().toString());
        }
        return ourBean;
    }

    @Override
    public void edit(InternshipApplication bean) {
        this.ourBean = bean;
        if (bean != null) {
            driver.edit(ourBean);
            Student student = ourBean.getStudent();
            if (student != null) {

                candidateLink.setText(student.getName());
                candidateLink.setTargetHistoryToken(student.getRelativeUri());
            }

            Internship internship = ourBean.getInternship();
            if (internship != null) {

                internshipLink.setText(internship.getName());
                internshipLink.setTargetHistoryToken(internship.getRelativeUri());
            }


            Date publishedAt = bean.getPublishedAt();
            if (publishedAt != null) {

                publishedAtLabel.setText(publishedAt.toString());
            }

        }


    }

    interface InternshipApplicationViewAndEditorUiBinder extends UiBinder<HTMLPanel, InternshipApplicationViewAndEditor> {
    }

    private static InternshipApplicationViewAndEditorUiBinder ourUiBinder = GWT.create(InternshipApplicationViewAndEditorUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<InternshipApplication, InternshipApplicationViewAndEditor> {}

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    Hyperlink candidateLink;
    @UiField
    Button editFeedbackBtn;
    @UiField
    Button saveFeedbackBtn;
    @UiField
    @Path("feedback")
    TextBox feedbackEdit;

    @UiField(provided = true)
    @Path("status")
    ValueListBox<StudentInternshipRelation.Status> statusBox =
            new ValueListBox<StudentInternshipRelation.Status>(new Renderer<StudentInternshipRelation.Status>() {
        @Override
        public String render(StudentInternshipRelation.Status account) {
            return account == null ? "" : account.toString();
        }

        @Override
        public void render(StudentInternshipRelation.Status account, Appendable appendable) throws IOException {
            if (account != null)
                appendable.append(account.toString());
        }
    });
    @UiField
    Button editStatusBtn;
    @UiField
    Button saveStatusBtn;
    @UiField
    @Ignore
    Label statusView;
    @UiField
    @Path("feedback")
    Label feedbackView;
    @UiField
    @Path("motivation")
    Label motivationView;
    @UiField
    @Ignore
    Label publishedAtLabel;
    @UiField
    Hyperlink internshipLink;

    private InternshipApplication ourBean;

    public InternshipApplicationViewAndEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();

    }

    private void wireUiElements() {
        statusBox.setValue(StudentInternshipRelation.Status.pending);
        statusBox.setAcceptableValues(InternshipApplication.POSSIBLE_STATUSES);


    }

    @UiHandler("editFeedbackBtn")
    public void editFeedback(ClickEvent e) {
        feedbackView.setVisible(false);
        feedbackEdit.setVisible(true);

        editFeedbackBtn.setVisible(false);
        saveFeedbackBtn.setVisible(true);



    }

    @UiHandler("saveFeedbackBtn")
    public void saveFeedback(ClickEvent e) {
        ourBean = save();
        feedbackView.setVisible(true);
        feedbackEdit.setVisible(false);

        editFeedbackBtn.setVisible(false);
        saveFeedbackBtn.setVisible(true);

        edit(ourBean);


    }

    @UiHandler("editStatusBtn")
    public void editStatus(ClickEvent e) {
        statusView.setVisible(false);
        statusBox.setVisible(true);

        editStatusBtn.setVisible(false);
        saveStatusBtn.setVisible(true);



    }

    @UiHandler("saveStatusBtn")
    public void saveStatus(ClickEvent e) {
        ourBean = save();
        statusView.setVisible(true);
        statusBox.setVisible(false);

        editStatusBtn.setVisible(false);
        saveStatusBtn.setVisible(true);

        edit(ourBean);


    }
}