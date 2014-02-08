package ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.ProjectsView;
import ro.infoiasi.wad.sesi.core.model.StudentProject;

import java.util.List;

public class ProjectEditor extends Composite implements LeafValueEditor<List<StudentProject>> {
    private List<StudentProject> value;

    @Override
    public void setValue(List<StudentProject> studentProjects) {
        projects.setValue(studentProjects);
    }

    @Override
    public List<StudentProject> getValue() {
       return value;
    }

    interface ProjectEditorUiBinder extends UiBinder<HTMLPanel, ProjectEditor> {
    }

    private static ProjectEditorUiBinder ourUiBinder = GWT.create(ProjectEditorUiBinder.class);
    @UiField
    @Editor.Ignore
    ProjectsView projects;
    @UiField
    @Editor.Ignore
    Button deleteProject;
    @UiField
    @Editor.Ignore
    Button addProject;
    @UiField
    @Editor.Ignore
    TextBox repository;
    @UiField
    @Editor.Ignore
    TextBox projectUrl;
    @UiField
    @Editor.Ignore
    TextBox projectDescription;
    @UiField
    @Editor.Ignore
    TextBox projectName;

    public ProjectEditor() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);

    }
}