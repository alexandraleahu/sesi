package ro.infoiasi.wad.sesi.client.progressdetails;

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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.core.model.*;

import java.io.IOException;
import java.util.Date;


public class InternshipProgressDetailsViewerAndEditor extends Composite implements ResourceWidgetEditor<InternshipProgressDetails> {
    @Override
    public InternshipProgressDetails save() {
        return driver.flush();
    }

    @Override
    public void edit(InternshipProgressDetails bean) {
        if (bean != null) {
            driver.edit(bean);
            Student student = bean.getStudent();
            if (student != null) {

                attendeeLink.setText(student.getName());
                attendeeLink.setTargetHistoryToken(student.getRelativeUri());
            }

            Internship internship = bean.getInternship();
            if (internship != null) {

                internshipLink.setText(internship.getName());
                internshipLink.setTargetHistoryToken(internship.getRelativeUri());

                Date startDate = internship.getStartDate();
                Date endDate = internship.getEndDate();

                if (startDate != null) {
                    startDateLabel.setText(startDate.toString());
                }

                if (endDate != null) {
                    endDateLabel.setText(endDate.toString());

                }
            }

            Teacher teacher = bean.getTeacher();
            if (teacher != null) {

                teacherLink.setText(teacher.getName());
                teacherLink.setTargetHistoryToken(teacher.getRelativeUri());
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

    interface InternshipProgressDetailsViewerAndEditorUiBinder extends UiBinder<HTMLPanel, InternshipProgressDetailsViewerAndEditor> {
    }

    private static InternshipProgressDetailsViewerAndEditorUiBinder ourUiBinder = GWT.create(InternshipProgressDetailsViewerAndEditorUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<InternshipProgressDetails, InternshipProgressDetailsViewerAndEditor> {}

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Ignore
    Label startDateLabel;
    @UiField
    @Ignore
    Label endDateLabel;
    @UiField
    Hyperlink attendeeLink;
    @UiField
    Hyperlink internshipLink;
    @UiField
    Hyperlink teacherLink;
    @UiField
    @Ignore
    Label feedbackView;
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
    Button saveBtn;

    public InternshipProgressDetailsViewerAndEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();

    }

    private void wireUiElements() {
        statusBox.setValue(StudentInternshipRelation.Status.inProgress);
        statusBox.setAcceptableValues(InternshipProgressDetails.POSSIBLE_STATUSES);


        if (UserAccountType.COMPANY_ACCOUNT.equals(Sesi.getCurrentUserType()) ||
            UserAccountType.TEACHER_ACCOUNT.equals(Sesi.getCurrentUserType())) {

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
        // TODO calling the server with the updates

        System.out.println(save());
    }
}