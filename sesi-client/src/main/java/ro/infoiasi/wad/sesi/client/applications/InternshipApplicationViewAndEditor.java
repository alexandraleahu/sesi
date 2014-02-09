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
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import java.io.IOException;


public class InternshipApplicationViewAndEditor extends Composite implements ResourceWidgetEditor<InternshipApplication> {
    @Override
    public InternshipApplication save() {

        InternshipApplication flush = driver.flush();
        statusView.setText(flush.getStatus().toString());
        return flush;
    }

    @Override
    public void edit(InternshipApplication bean) {
        if (bean != null) {
            driver.edit(bean);
            publishedAtLabel.setText(bean.getPublishedAt().toString());
        }


    }

    interface ApplicationViewAndEditorUiBinder extends UiBinder<HTMLPanel, InternshipApplicationViewAndEditor> {
    }

    private static ApplicationViewAndEditorUiBinder ourUiBinder = GWT.create(ApplicationViewAndEditorUiBinder.class);

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

    private InternshipApplication bean;

    public InternshipApplicationViewAndEditor(String id) {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        // TODO call the service
        bean = new InternshipApplication();
        bean.setId(id);

        wireUiElements();

        edit(bean);
    }

    private void wireUiElements() {
        statusBox.setValue(StudentInternshipRelation.Status.pending);
        statusBox.setAcceptableValues(InternshipApplication.POSSIBLE_STATUSES);

        candidateLink.setText(bean.getStudent().getName());
        candidateLink.setTargetHistoryToken(bean.getStudent().getRelativeUri());

        internshipLink.setText(bean.getInternship().getName());
        internshipLink.setTargetHistoryToken(bean.getInternship().getRelativeUri());
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
        bean = save();
        feedbackView.setVisible(true);
        feedbackEdit.setVisible(false);

        editFeedbackBtn.setVisible(false);
        saveFeedbackBtn.setVisible(true);

        edit(bean);


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
        bean = save();
        statusView.setVisible(true);
        statusBox.setVisible(false);

        editStatusBtn.setVisible(false);
        saveStatusBtn.setVisible(true);

        edit(bean);


    }
}