package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Teacher implements Resource, Person {

    private Faculty faculty;
    private String id;
    private String title;
    private List<InternshipProgressDetails> monitoringInternships;
    private String name;
    private String siteUrl;
    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Teacher() {
        courses = new ArrayList<Course>() ;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return title;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRelativeUri() {
        return "/teachers/" + getId();
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public List<InternshipProgressDetails> getMonitoringInternships() {
        return monitoringInternships;
    }

    public void setMonitoringInternships(List<InternshipProgressDetails> monitoringInternships) {
        this.monitoringInternships = monitoringInternships;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Teacher{");
        sb.append("faculty=").append(faculty);
        sb.append(", id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", monitoringInternships=").append(monitoringInternships);
        sb.append(", name='").append(name).append('\'');
        sb.append(", courses='").append(courses).append('\'');
        sb.append(", siteUrl='").append(siteUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
