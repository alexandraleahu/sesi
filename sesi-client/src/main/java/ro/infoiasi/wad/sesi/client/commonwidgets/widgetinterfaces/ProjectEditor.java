package ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;
import ro.infoiasi.wad.sesi.core.model.ProgrammingLanguage;
import ro.infoiasi.wad.sesi.core.model.StudentProject;
import ro.infoiasi.wad.sesi.core.model.Technology;

import java.util.Arrays;
import java.util.List;

public class ProjectEditor extends Composite implements LeafValueEditor<StudentProject> {
    private StudentProject value;

    @Override
    public void setValue(StudentProject value) {
        if (value != null) {

            this.value = value;

            projectName.setText(value.getName());
            projectDescription.setText(value.getDescription());
            repository.setText(value.getRepository());
            projectUrl.setText(value.getInfoUrl());
            if(value.getProgrammingLanguages() != null) {
                List langs = Lists.newArrayList();
                for (ProgrammingLanguage programmingLanguage : value.getProgrammingLanguages()) {
                    langs.add(programmingLanguage.getName());
                }
                programmingLanguagesIdArea.setText(Joiner.on(",").join(langs));
            }
            if(value.getTechnologies() != null) {
                List langs = Lists.newArrayList();
                for (Technology technology : value.getTechnologies()) {
                    langs.add(technology.getName());
                }
                technologiesIdArea.setText(Joiner.on(",").join(langs));
            }
        }
    }

    @Override
    public StudentProject getValue() {
        if (value != null) {
            value.setName(projectName.getText());
            value.setInfoUrl(projectUrl.getText());
            value.setRepository(repository.getText());
            value.setDescription(projectDescription.getText());

            //programming languages
            List<String> rawProgrammingLanguages = Arrays.asList(programmingLanguagesIdArea.getText().split(WidgetConstants.multipleSkillSeparator));
            List<ProgrammingLanguage> programmingLanguages = Lists.transform(rawProgrammingLanguages, new WidgetConstants.ProgrammingLanguageFunction());
            value.setProgrammingLanguages(programmingLanguages);

            //technologies
            List<String> rawTechnologies = Arrays.asList(technologiesIdArea.getText().split(WidgetConstants.multipleSkillSeparator));
            List<Technology> technologies = Lists.transform(rawTechnologies, new WidgetConstants.TechnologyFunction());
            value.setTechnologies(technologies);
        }
        return value;
    }

    interface ProjectEditorUiBinder extends UiBinder<HTMLPanel, ProjectEditor> {
    }

    private static ProjectEditorUiBinder ourUiBinder = GWT.create(ProjectEditorUiBinder.class);

    @UiField
    Button deleteProject;
    @UiField
    TextBox repository;
    @UiField
    TextBox projectUrl;
    @UiField
    TextBox projectDescription;
    @UiField
    TextBox projectName;
    @UiField
    @Ignore
    TextArea technologiesIdArea;
    @UiField
    TextBox technologies;
    @UiField
    @Ignore
    TextArea programmingLanguagesIdArea;
    @UiField
    TextBox programmingLanguage;

    public ProjectEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("deleteProject")
    public void removeEditor(ClickEvent e) {
        this.removeFromParent();
    }
}