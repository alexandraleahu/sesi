package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 14:59
 */
@XmlRootElement
public class Student implements Resource, Person {

    private String name;
    private String description;
    private List<StudentProject> projects;
    private List<String> generalSkills;
    private List<TechnicalSkill> technicalSkills;

    private Studies studies;
    private String id;

    public Student() {
        projects = new ArrayList<StudentProject>();
        generalSkills = new ArrayList<String>();
        technicalSkills = new ArrayList<TechnicalSkill>();
        projects = new ArrayList<StudentProject>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRelativeUri() {
        return "/students/" + getId();
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<StudentProject> getProjects() {
        return projects;
    }

    public void setProjects(List<StudentProject> projects) {
        this.projects = projects;
    }

    public List<String> getGeneralSkills() {
        return generalSkills;
    }

    public void setGeneralSkills(List<String> generalSkills) {
        this.generalSkills = generalSkills;
    }

    public void setTechnicalSkills(List<TechnicalSkill> technicalSkills) {

        this.technicalSkills = technicalSkills;
    }

    public List<TechnicalSkill> getTechnicalSkills() {
        return technicalSkills;
    }

    public Studies getStudies() {
        return studies;
    }

    public void setStudies(Studies studies) {
        this.studies = studies;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append(" name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", projects=").append(projects);
        sb.append(", generalSkills=").append(generalSkills);
        sb.append(", technicalSkills=").append(technicalSkills);
        sb.append(", studies=").append(studies);
        sb.append(", id='").append(id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
