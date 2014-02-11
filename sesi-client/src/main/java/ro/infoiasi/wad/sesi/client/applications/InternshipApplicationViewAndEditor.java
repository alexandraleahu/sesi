package ro.infoiasi.wad.sesi.client.applications;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.core.model.*;

import java.io.IOException;
import java.util.Date;


public class InternshipApplicationViewAndEditor extends Composite implements ResourceWidgetEditor<InternshipApplication> {
    @Override
    public InternshipApplication save() {

       return driver.flush();
    }

    @Override
    public void edit(InternshipApplication bean) {
        if (bean != null) {
            driver.edit(bean);
            Student student = bean.getStudent();
            if (student != null) {

                candidateLink.setText(student.getName());
                candidateLink.setTargetHistoryToken(student.getRelativeUri());
            }

            Internship internship = bean.getInternship();
            if (internship != null) {

                internshipLink.setText(internship.getName());
                internshipLink.setTargetHistoryToken(internship.getRelativeUri());
            }


            Date publishedAt = bean.getPublishedAt();
            if (publishedAt != null) {

                publishedAtLabel.setText(publishedAt.toString());
            }

            StudentInternshipRelation.Status status = bean.getStatus();

            if (status != null && statusView != null && statusView.isVisible()) {
                statusView.setText(status.toString());
            }

            String feedback = bean.getFeedback();
            if (feedback != null && feedbackView != null && feedbackView.isVisible()) {
                feedbackView.setText(feedback);
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
    @Path("feedback")
    TextArea feedbackEdit;

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
    @Ignore
    Label statusView;
    @UiField
    @Ignore
    Label feedbackView;
    @UiField
    @Path("motivation")
    Label motivationView;
    @UiField
    @Ignore
    Label publishedAtLabel;
    @UiField
    Hyperlink internshipLink;
    @UiField
    Button saveBtn;
    @UiField
            @Ignore
    Label result;

    public InternshipApplicationViewAndEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();

    }

    private void wireUiElements() {
        statusBox.setValue(StudentInternshipRelation.Status.pending);
        statusBox.setAcceptableValues(InternshipApplication.POSSIBLE_STATUSES);


        if (UserAccountType.COMPANY_ACCOUNT.equals(Sesi.getCurrentUserType())) {
            statusBox.setVisible(true);
            statusView.removeFromParent();

            feedbackEdit.setVisible(true);
            feedbackView.removeFromParent();

            saveBtn.setVisible(true);
        } else {
            statusBox.removeFromParent();
            statusView.setVisible(true);

            feedbackEdit.removeFromParent();
            feedbackView.setVisible(true);

            saveBtn.removeFromParent();
        }

    }


    @UiHandler("saveBtn")
    public void saveApplications(ClickEvent e) {
        InternshipApplication save = save();
        InternshipApplicationsService.App.getInstance().updateStatus(save.getId(), save.getStatus(), new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Boolean r) {
                 result.setText("Successfully updated!");
            }
        });
        InternshipApplicationsService.App.getInstance().updateFeedback(save.getId(), save.getFeedback(), new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Boolean r) {
                result.setText("Successfully updated!");
            }
        });

    }

    
}