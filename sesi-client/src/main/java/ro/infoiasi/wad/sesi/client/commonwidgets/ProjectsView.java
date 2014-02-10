package ro.infoiasi.wad.sesi.client.commonwidgets;

import com.github.gwtbootstrap.client.ui.Accordion;
import com.github.gwtbootstrap.client.ui.AccordionGroup;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoView;
import ro.infoiasi.wad.sesi.core.model.ProgrammingLanguage;
import ro.infoiasi.wad.sesi.core.model.StudentProject;

import java.util.List;

public class ProjectsView extends Composite implements LeafValueEditor<List<StudentProject>> {
    private List<StudentProject> value;

    @Override
    public void setValue(List<StudentProject> projects) {
        value = projects;
        for (StudentProject project : value) {
            AccordionGroup group = new AccordionGroup();
            group.setHeading(project.getName());
            Paragraph description = new Paragraph();
            description.setText(project.getDescription());
            group.add(description);
            Paragraph infourl = new Paragraph();
            description.setText(project.getInfoUrl());
            group.add(infourl);
            if (project.getProgrammingLanguages() != null) {
                for (ProgrammingLanguage programmingLanguage : project.getProgrammingLanguages()) {
                    OntologyExtraInfoView extraInfoView = new OntologyExtraInfoView();
                    extraInfoView.setValue(programmingLanguage);
                    group.add(extraInfoView);
                }
            }
            if (project.getRepository() != null) {
                Paragraph repo = new Paragraph();
                repo.setText(project.getRepository());
                group.add(repo);
            }
            accordion.add(group);
        }

    }

    @Override
    public List<StudentProject> getValue() {
        return value;
    }

    interface ProjectsViewUiBinder extends UiBinder<Accordion, ProjectsView> {
    }

    private static ProjectsViewUiBinder ourUiBinder = GWT.create(ProjectsViewUiBinder.class);

    @UiField(provided = true)
    Accordion accordion = new Accordion();


    public ProjectsView() {
        Accordion root = ourUiBinder.createAndBindUi(this);
        initWidget(root);
    }

    public void addProject(StudentProject project) {
        AccordionGroup group = new AccordionGroup();
        group.setHeading(project.getName());
        Paragraph description = new Paragraph();
        description.setText(project.getDescription());
        group.add(description);
        Paragraph infourl = new Paragraph();
        infourl.setText(project.getInfoUrl());
        group.add(infourl);
        if (project.getProgrammingLanguages() != null) {
            for (ProgrammingLanguage programmingLanguage : project.getProgrammingLanguages()) {
                OntologyExtraInfoView extraInfoView = new OntologyExtraInfoView();
                extraInfoView.setValue(programmingLanguage);
                group.add(extraInfoView);
            }
        }
        if (project.getRepository() != null) {
            Paragraph repo = new Paragraph();
            repo.setText(project.getRepository());
            group.add(repo);
        }
        accordion.add(group);
    }
}