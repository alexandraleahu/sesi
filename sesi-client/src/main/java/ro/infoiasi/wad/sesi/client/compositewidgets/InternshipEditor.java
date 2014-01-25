package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBox;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.core.model.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class InternshipEditor extends Composite implements ResourceWidgetEditor<Internship>,
                                                            ValueChangeHandler<KnowledgeLevel> {

    public static final String multipleSkillSeparator = "\n";
    public static final String dataSeparator = ":";

    @Override
    public Internship save() {

        Internship internship = driver.flush();

        // publisher company (we assume that the current logged in user is a company, because no one else can create an internship)
        String companyId = Cookies.getCookie("currentUser");
        Company company = new Company();
        company.setId(companyId);

        internship.setCompany(company);

        City city = new City();
        String[] split = cityIdArea.getText().split(dataSeparator);
        OntologyExtraInfo.fillWithOntologyExtraInfo(city, split[0], split[1]);
        internship.setCity(city);

        // acquired & preferred GeneralSkills
        internship.setAcquiredGeneralSkills(Arrays.asList(acquiredGeneralSkillsArea.getText().split(multipleSkillSeparator)));
        internship.setPreferredGeneralSkills(Arrays.asList(preferredGeneralSkillsArea.getText().split(multipleSkillSeparator)));

         // acquired & preferred technicalSkills
        List<String> rawAcquiredGeneralSkills = Arrays.asList(acquiredTechnicalSkillIdArea.getText().split(multipleSkillSeparator));
        List<TechnicalSkill> technicalSkills = Lists.transform(rawAcquiredGeneralSkills, new Function<String, TechnicalSkill>() {
            @Nullable
            @Override
            public TechnicalSkill apply(String input) {
                String[] rawSkills = input.split(dataSeparator);
                TechnicalSkill technicalSkill = new TechnicalSkill();
                KnowledgeLevel level = KnowledgeLevel.valueOf(rawSkills[2]);
                technicalSkill.setLevel(level);
                return technicalSkill;
            }
        });
        internship.setAcquiredGeneralSkills(rawAcquiredGeneralSkills);
        internship.setPreferredGeneralSkills(Arrays.asList(preferredGeneralSkillsArea.getText().split(multipleSkillSeparator)));

        return internship;
    }

    @Override
    public void edit(Internship resource) {
        // not used for editing, just for saving
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
    TextArea preferredTechnicalSkillIdArea;

    @UiField
    @Path("name")
    TextBox internshipNameBox;

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
    TextArea preferredGeneralSkillsArea;
    @UiField
    @Ignore
    TextArea acquiredGeneralSkillsArea;
    @UiField
    @Path("openings")
    IntegerEditor openingsBox;
    @UiField
    @Ignore
    TextBox cityBox;
    @UiField
    @Ignore
    TextArea cityIdArea;
    @UiField
    @Path("offeringRelocation")
    CheckBox offersRelocationCheckbox;
    @UiField
    @Path("salaryValue")
    DoubleEditor salaryValue;

    @UiField
    @Ignore
    ListBox currencyBox;

    @UiField(provided = true)
    @Ignore
    ValueListBox<KnowledgeLevel> preferredLevelList = new ValueListBox<KnowledgeLevel>(new Renderer<KnowledgeLevel>() {
        @Override
        public String render(KnowledgeLevel level) {
            return level == null ? "" : level.toString();
        }

        @Override
        public void render(KnowledgeLevel category, Appendable appendable) throws IOException {
            if (category != null)
                appendable.append(category.toString());
        }
    });

    @UiField(provided = true)
    @Ignore
    ValueListBox<KnowledgeLevel> acquiredLevelList = new ValueListBox<KnowledgeLevel>(new Renderer<KnowledgeLevel>() {
        @Override
        public String render(KnowledgeLevel level) {
            return level == null ? "" : level.toString();
        }

        @Override
        public void render(KnowledgeLevel category, Appendable appendable) throws IOException {
            if (category != null)
                appendable.append(category.toString());
        }
    });
    @UiField
    @Ignore
    TextArea acquiredTechnicalSkillIdArea;


    public InternshipEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();

    }

    private void wireUiElements() {

        categoryList.setValue(Internship.Category.WebDev);
        categoryList.setAcceptableValues(Arrays.asList(Internship.Category.values()));

        preferredLevelList.setValue(KnowledgeLevel.Basic);
        preferredLevelList.setAcceptableValues(Arrays.asList(KnowledgeLevel.values()));

        acquiredLevelList.setValue(KnowledgeLevel.Basic);
        acquiredLevelList.setAcceptableValues(Arrays.asList(KnowledgeLevel.values()));

        preferredLevelList.addValueChangeHandler(this);
        acquiredLevelList.addValueChangeHandler(this);

        currencyBox.addItem("RON");
        currencyBox.addItem("USD");
        currencyBox.addItem("EURO");
    }

    @Override
    public void onValueChange(ValueChangeEvent<KnowledgeLevel> event) {
        KnowledgeLevel level = event.getValue();
        TextArea technicalSkillsArea = null;
        if (event.getSource().equals(preferredLevelList)) {

            technicalSkillsArea = preferredTechnicalSkillIdArea;

        } else if (event.getSource().equals(acquiredLevelList)) {

            technicalSkillsArea = acquiredTechnicalSkillIdArea;
        }

        String text = technicalSkillsArea.getText();
        if (!(text == null || text.isEmpty())) {
            int lastIndexOf = text.lastIndexOf(dataSeparator);
            technicalSkillsArea.setText(text.substring(0, lastIndexOf) + dataSeparator + level.toString());

        }
    }


}