package ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.core.model.StudentProject;

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
        }
    }

    @Override
    public StudentProject getValue() {
        if (value != null) {
            value.setName(projectName.getText());
            value.setInfoUrl(projectUrl.getText());
            value.setRepository(repository.getText());
            value.setDescription(projectDescription.getText());
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

    public ProjectEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiHandler("deleteProject")
    public void removeEditor(ClickEvent e) {
        this.removeFromParent();
    }
}