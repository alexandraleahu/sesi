package ro.infoiasi.wad.sesi.client.students;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextArea;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.authentication.SigninService;
import ro.infoiasi.wad.sesi.client.commonwidgets.DoubleEditor;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ProjectEditor;
import ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces.ResourceWidgetEditor;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.core.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentEditor extends Composite implements ResourceWidgetEditor<Student>,
        ValueChangeHandler<KnowledgeLevel>, ClickHandler {
    @Override
    public void onClick(ClickEvent clickEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onValueChange(ValueChangeEvent<KnowledgeLevel> knowledgeLevelValueChangeEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Student save() {
        Student student = driver.flush();
        String studentId = Cookies.getCookie("currentUser");
        student.setId(studentId);
        //general skills
        student.setGeneralSkills(Arrays.asList(generalSkillsArea.getText().split(WidgetConstants.multipleSkillSeparator)));

        //technical skills
        List<String> rawTechnicalSkills = Arrays.asList(freebaseTechnicalSkillsBox.getText().split(WidgetConstants.multipleSkillSeparator));
        List<TechnicalSkill> technicalSkills = new ArrayList<TechnicalSkill>();
        for (String rrth : rawTechnicalSkills) {
            TechnicalSkill technicalSkill = apply(rrth);
            technicalSkills.add(technicalSkill);
        }
        student.setTechnicalSkills(technicalSkills);

        //studies
        University university = new University();
        String[] split = universityId.getText().split(WidgetConstants.dataSeparator);
        OntologyExtraInfo.fillWithOntologyExtraInfo(university, split[0], split[1]);
        Faculty faculty = new Faculty();
        split = facultyId.getText().split(WidgetConstants.dataSeparator);
        OntologyExtraInfo.fillWithOntologyExtraInfo(faculty, split[0], split[1]);
        faculty.setUniversity(university);
        Degree degree = new Degree();
        split = degreeId.getText().split(WidgetConstants.dataSeparator);
        OntologyExtraInfo.fillWithOntologyExtraInfo(degree, split[0], split[1]);
        Studies studies = new Studies();
        studies.setFaculty(faculty);
        studies.setDegree(degree);
        studies.setYearOfStudy(yearOfStudy.getValue().intValue());
        student.setStudies(studies);

        // taking the projects
        List<StudentProject> projects = Lists.newArrayList();
        for (int i = 0; i < projectsPanel.getWidgetCount(); i++) {
            ProjectEditor editor = (ProjectEditor) projectsPanel.getWidget(i);
            projects.add(editor.getValue());
        }
        student.setProjects(projects);

        return student;
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
    public void edit(Student bean) {
        driver.edit(bean);
        if (bean != null && bean.getProjects() != null && projectsPanel.getWidgetCount() == 0) {

            for (StudentProject project : bean.getProjects()) {
                ProjectEditor projectEditor = addProject(null);
                projectEditor.setValue(project);
            }
        }
    }

    interface StudentEditorUiBinder extends UiBinder<HTMLPanel, StudentEditor> {
    }

    private static StudentEditorUiBinder ourUiBinder = GWT.create(StudentEditorUiBinder.class);

    // Empty interface declaration, similar to UiBinder
    interface Driver extends SimpleBeanEditorDriver<Student, StudentEditor> {
    }

    // Create the Driver
    Driver driver = GWT.create(Driver.class);

    @UiField
    @Editor.Path("description")
    TextBox description;

    @UiField
    @Editor.Ignore
    TextArea generalSkillsArea;

    @UiField
    @Editor.Ignore
    TextBox freebaseTechnicalSkillsBox;

    @UiField
    @Path("name")
    TextBox name;
    @UiField
    @Ignore
    DoubleEditor yearOfStudy;
    @UiField
    @Ignore
    TextArea degreeId;
    @UiField
    @Ignore
    TextBox degree;
    @UiField
    @Ignore
    TextArea universityId;
    @UiField
    @Ignore
    TextBox university;
    @UiField
    com.github.gwtbootstrap.client.ui.Button addProjectButton;
    @UiField
    @Ignore
    HTMLPanel projectsPanel;
    @UiField
    @Ignore
    TextArea facultyId;
    @UiField
    @Ignore
    Button importProfile;

    public StudentEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));
        driver.initialize(this);
        if (Cookies.getCookie(WidgetConstants.STUDENT_IMPORT_PROFILE_COOKIE) != null) {
            Cookies.setCookie(WidgetConstants.STUDENT_IMPORT_PROFILE_COOKIE, null);
            if (Window.Location.getParameter("oauth_verifier") != null) {
                importProfile(null);
            }
        }
    }

    @UiHandler("addProjectButton")
    public ProjectEditor addProject(ClickEvent e) {
        ProjectEditor projectEditor = new ProjectEditor();
        projectEditor.setValue(new StudentProject());
        projectsPanel.add(projectEditor);

        return projectEditor;
    }

    @UiHandler("importProfile")
    public void importProfile(ClickEvent e) {
        String v = Window.Location.getParameter("oauth_verifier");
        if (v != null) {
            SigninService.App.getInstance().getProfile(v, new AsyncCallback<StudentLinkedinProfile>() {

                @Override
                public void onFailure(Throwable caught) {
                    //to be decided what to do
                }

                @Override
                public void onSuccess(final StudentLinkedinProfile profile) {
                    wireUiElements(profile);
                }
            });
        } else {
            SigninService.App.getInstance().getAuthenticateUrl("Linkedin", Window.Location.getHref(), new AsyncCallback<String>() {

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                @Override
                public void onSuccess(String result) {
                    Cookies.setCookie(WidgetConstants.STUDENT_IMPORT_PROFILE_COOKIE, "1");
                    Window.Location.replace(result);
                }
            });
        }
    }


    private void wireUiElements(StudentLinkedinProfile profile) {
        name.setText(profile.firstName + " " + profile.lastName);
        generalSkillsArea.setText(Joiner.on(", ").join(profile.skills));
        for (String project : profile.projects) {
            ProjectEditor editor = addProject(null);
            StudentProject sp = new StudentProject();
            sp.setName(project);
            editor.setValue(sp);
        }
        facultyId.setText(Joiner.on(",").join(profile.schools));
    }
}