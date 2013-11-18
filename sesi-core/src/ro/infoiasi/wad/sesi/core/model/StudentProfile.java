package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 15:11
 */
//todo will be further refined for a smooth integration with linkedin
@XmlRootElement
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentProfile that = (StudentProfile) o;

        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (internships != null ? !internships.equals(that.internships) : that.internships != null) return false;
        if (languages != null ? !languages.equals(that.languages) : that.languages != null) return false;
        if (projects != null ? !projects.equals(that.projects) : that.projects != null) return false;
        if (skills != null ? !skills.equals(that.skills) : that.skills != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = summary != null ? summary.hashCode() : 0;
        result = 31 * result + (projects != null ? projects.hashCode() : 0);
        result = 31 * result + (internships != null ? internships.hashCode() : 0);
        result = 31 * result + (languages != null ? languages.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StudentProfile{" +
                "summary='" + summary + '\'' +
                ", projects=" + projects +
                ", internships=" + internships +
                ", languages=" + languages +
                ", skills=" + skills +
                ", education=" + education +
                '}';
    }
}