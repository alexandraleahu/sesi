package ro.infoiasi.wad.sesi.core.model;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 14:59
 */
@XmlRootElement
public class Student implements Resource, Person {
    private Map<Internship, InternshipApplication> appliedToInternships;
    private Map<Internship, InternshipProgressDetails> inProgressOrFinishedInternships;

    private String name;
    private String description;
    private List<StudentProject> projects;
    private List<String> generalSkills;
    private List<TechnicalSkill> technicalSkills;

    private Studies studies;
    private String id;

    public Student() {
        projects = Lists.newArrayList();
        generalSkills = Lists.newArrayList();
        technicalSkills = Lists.newArrayList();
        projects = Lists.newArrayList();
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


    public Map<Internship, InternshipApplication> getAppliedToInternships() {
        return appliedToInternships;
    }

    public void setAppliedToInternships(Map<Internship, InternshipApplication> appliedToInternships) {
        this.appliedToInternships = appliedToInternships;
    }

    public Map<Internship, InternshipProgressDetails> getInProgressOrFinishedInternships() {
        return inProgressOrFinishedInternships;
    }

    public void setInProgressOrFinishedInternships(Map<Internship, InternshipProgressDetails> inProgressOrFinishedInternships) {
        this.inProgressOrFinishedInternships = inProgressOrFinishedInternships;
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
}
