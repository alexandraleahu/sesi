package ro.infoiasi.wad.sesi.core.model;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 15:11
 */
//todo will be further refined for a smooth integration with linkedin
public class StudentProfile {
    private String summary;
    private List<Project> projects;
    private List<Internship> internships;
    private List<Language> languages;
    private List<String> skills;
    private Map<School, String> education; //school + a small description

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Internship> getInternships() {
        return internships;
    }

    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
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