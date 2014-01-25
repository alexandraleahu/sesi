package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.core.model.Internship;

import java.io.IOException;
import java.util.Arrays;


public class InternshipEditor extends Composite implements ResourceWidgetEditor<Internship> {
    @Override
    public Internship save() {
        return null;
    }

    @Override
    public void edit(Internship resource) {

    }

    interface InternshipEditorUiBinder extends UiBinder<HTMLPanel, InternshipEditor> {
    }

    private static InternshipEditorUiBinder ourUiBinder = GWT.create(InternshipEditorUiBinder.class);
    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Internship, InternshipEditor> {}

    // Create the Driver
    Driver driver = GWT.create(Driver.class);


    @UiField
    @Ignore
    TextArea preferredTechnicalSkillIdLabel;

    @UiField
    @Path("name")
    TextBox internshipNameLabel;

    @UiField
    @Path("startDate")
    DateTimeBox startDateBox;

    @UiField
    @Path("endDate")
    DateTimeBox endDateBox;

    @UiField(provided = true)
    @Path("category")
    ValueListBox<Internship.Category> categoryList = new ValueListBox<Internship.Category>(new Renderer<Internship.Category>() {
        @Override
        public String render(Internship.Category category) {
            return category == null ? "" : category.getDescription();
        }

        @Override
        public void render(Internship.Category category, Appendable appendable) throws IOException {
            if (category != null)
                appendable.append(category.getDescription());
        }
    });

    @UiField
    @Path("description")
    TextArea descriptionArea;
    @UiField
            @Ignore
    TextArea preferredGeneralSkillsField;
    @UiField
            @Ignore
    TextArea acquiredGeneralSkillsField;


    public InternshipEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();

    }

    private void wireUiElements() {

        categoryList.setAcceptableValues(Arrays.asList(Internship.Category.values()));
    }


}