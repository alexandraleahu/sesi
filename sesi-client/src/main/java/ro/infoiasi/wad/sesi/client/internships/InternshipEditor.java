package ro.infoiasi.wad.sesi.client.internships;

import com.github.gwtbootstrap.client.ui.*;
import com.github.gwtbootstrap.datetimepicker.client.ui.DateTimeBox;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.commonwidgets.DoubleEditor;
import ro.infoiasi.wad.sesi.client.commonwidgets.IntegerEditor;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.core.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InternshipEditor extends Composite implements ResourceWidgetEditor<Internship>,
                                                            ValueChangeHandler<KnowledgeLevel>{

    @Override
    public Internship save() {

        Internship internship = driver.flush();

        // publisher company (we assume that the current logged in user is a company, because no one else can create an internship)
        Company company = new Company();

        company.setId(Sesi.getCurrentUserId());

        internship.setCompany(company);

        City city = new City();
        String[] split = cityIdArea.getText().split(WidgetConstants.dataSeparator);
        OntologyExtraInfo.fillWithOntologyExtraInfo(city, split[0], split[1]);
        internship.setCity(city);

        // acquired & preferred generalSkills
        internship.setAcquiredGeneralSkills(Arrays.asList(acquiredGeneralSkillsArea.getText().split(WidgetConstants.multipleSkillSeparator)));
        internship.setPreferredGeneralSkills(Arrays.asList(preferredGeneralSkillsArea.getText().split(WidgetConstants.multipleSkillSeparator)));

         // acquired & preferred technicalSkills
        List<String> rawAcquiredTechnicalSkills = Arrays.asList(acquiredTechnicalSkillIdArea.getText().split(WidgetConstants.multipleSkillSeparator));

        List<TechnicalSkill> ts = new ArrayList<TechnicalSkill>();
        for (String rrth : rawAcquiredTechnicalSkills) {
            TechnicalSkill technicalSkill = apply(rrth);
            ts.add(technicalSkill);
        }

        internship.setAcquiredTechnicalSkills(ts);

        List<String> rawPreferredGeneralSkills = Arrays.asList(preferredTechnicalSkillIdArea.getText().split(WidgetConstants.multipleSkillSeparator));
        List<TechnicalSkill> pts = new ArrayList<TechnicalSkill>();
        for (String rrth : rawPreferredGeneralSkills) {
            TechnicalSkill technicalSkill = apply(rrth);
            pts.add(technicalSkill);
        }
        internship.setPreferredTechnicalSkills(pts);

        List<Internship.Category> cats = new ArrayList<Internship.Category>();
        for(int i = 0; i < categoryList.getItemCount(); ++i) {
            if (categoryList.isItemSelected(i)) {
                cats.add(Internship.Category.fromDescription(categoryList.getItemText(i)));
            }
        }
        internship.setCategories(cats);
        // id and published date will be set on the backend


        return internship;
    }

    public TechnicalSkill apply(String input) {
        String[] rawSkills = input.split(WidgetConstants.dataSeparator);
        TechnicalSkill technicalSkill = new TechnicalSkill();

        String progrType = Constants.PROGRAMMING_LANG_TITLE;

        if (rawSkills[1].equalsIgnoreCase(progrType)) {
            ProgrammingLanguage lang = new ProgrammingLanguage();
            OntologyExtraInfo.fillWithOntologyExtraInfo(lang, rawSkills[0], rawSkills[2]);
            technicalSkill.setProgrammingLanguage(lang);
        } else {
            Technology tech = new Technology();
            OntologyExtraInfo.fillWithOntologyExtraInfo(tech, rawSkills[0], rawSkills[2]);
            technicalSkill.setTechnology(tech);
        }
        KnowledgeLevel level = KnowledgeLevel.valueOf(rawSkills[3]);
        technicalSkill.setLevel(level);
        return technicalSkill;
    }



    @Override
    public void edit(Internship resource) {
        driver.edit(resource);
        for(int i = 0; i < categoryList.getItemCount(); ++i) {
            if (resource.getCategories().contains(Internship.Category.fromDescription(categoryList.getItemText(i)))) {
                categoryList.setItemSelected(i, true);
            }
        }
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
    @Ignore
    ListBox categoryList = new ListBox(true);

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

    @UiField(provided = true)
    @Path("salaryCurrency.name")
    ValueListBox<String> currencyBox = new ValueListBox<String>(new Renderer<String>() {
        @Override
        public String render(String currency) {
            return currency == null ? "" : currency;
        }

        @Override
        public void render(String currency, Appendable appendable) throws IOException {
            if (currency != null)
                appendable.append(currency);
        }
    });

    @UiField(provided = true)
    @Ignore
    ValueListBox<KnowledgeLevel> preferredLevelList = new ValueListBox<KnowledgeLevel>(new Renderer<KnowledgeLevel>() {
        @Override
        public String render(KnowledgeLevel level) {
            return level == null ? "" : level.toString();
        }

        @Override
        public void render(KnowledgeLevel level, Appendable appendable) throws IOException {
            if (level != null)
                appendable.append(level.toString());
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
//    @UiField
//    Button publishBtn;


    public InternshipEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

        wireUiElements();

    }

    private void wireUiElements() {

        for(Internship.Category cat : Internship.Category.values()) {

            categoryList.addItem(cat.getDescription());
        }

        preferredLevelList.setValue(KnowledgeLevel.Basic);
        preferredLevelList.setAcceptableValues(Arrays.asList(KnowledgeLevel.values()));

        acquiredLevelList.setValue(KnowledgeLevel.Basic);
        acquiredLevelList.setAcceptableValues(Arrays.asList(KnowledgeLevel.values()));

        preferredLevelList.addValueChangeHandler(this);
        acquiredLevelList.addValueChangeHandler(this);

        currencyBox.setValue("RON");
        currencyBox.setAcceptableValues(Lists.newArrayList("RON", "EURO", "USD"));

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
            int lastIndexOf = text.lastIndexOf(WidgetConstants.dataSeparator);
            technicalSkillsArea.setText(text.substring(0, lastIndexOf) + WidgetConstants.dataSeparator + level.toString());

        }
    }


}