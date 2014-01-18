package ro.infoiasi.wad.sesi.core.model;

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
    private Map<Internship, InternshipProgress> inProgressOrFinishedInternships;

    private String name;
    private String description;
    private List<Technology> projects;
    private List<String> skills;

    private Map<School, String> education; //school + a small description
    private String id;

    @Override
    public String getId() {
        return id;
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

    public Map<Internship, InternshipProgress> getInProgressOrFinishedInternships() {
        return inProgressOrFinishedInternships;
    }

    public void setInProgressOrFinishedInternships(Map<Internship, InternshipProgress> inProgressOrFinishedInternships) {
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


    public List<Technology> getProjects() {
        return projects;
    }

    public void setProjects(List<Technology> projects) {
        this.projects = projects;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Map<School, String> getEducation() {
        return education;
    }

    public void setEducation(Map<School, String> education) {
        this.education = education;
    }
}
