package ro.infoiasi.wad.sesi.client.teachers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetViewer;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoListView;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoView;
import ro.infoiasi.wad.sesi.core.model.Course;
import ro.infoiasi.wad.sesi.core.model.Faculty;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.core.model.University;


public class TeacherProfileView extends Composite implements ResourceWidgetViewer<Teacher> {

    @Override
    public void edit(Teacher bean) {
       driver.edit(bean);
       siteLabel.setText(bean.getSiteUrl());
       siteLabel.setHref(bean.getSiteUrl());
    }

    interface TeacherProfileViewUiBinder extends UiBinder<HTMLPanel, TeacherProfileView> {
    }

    private static TeacherProfileViewUiBinder ourUiBinder = GWT.create(TeacherProfileViewUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Teacher, TeacherProfileView> {
    }

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Path("name")
    Label nameLabel;
    @UiField
    @Path("title")
    Label titleLabel;
    @UiField
    @Path("faculty")
    OntologyExtraInfoView<Faculty> facultyLink;
    @UiField
    @Path("faculty.university")
    OntologyExtraInfoView<University> universityLink;
    @UiField
    @Ignore
    Anchor siteLabel;
    @UiField
    @Path("courses")
    OntologyExtraInfoListView<Course> coursesTable;

    public TeacherProfileView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);

    }
}