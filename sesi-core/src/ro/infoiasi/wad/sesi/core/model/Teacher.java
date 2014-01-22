package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

import java.util.List;

public class Teacher implements Resource, Person {

    private Faculty faculty;
    private String id;
    private String title;
    private List<InternshipProgressDetails> monitoringInternships;
    private String name;
    private String siteUrl;


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
        return Objects.toStringHelper(this)
                .add("faculty", faculty)
                .add("id", id)
                .add("title", title)
                .add("monitoringInternships", monitoringInternships)
                .add("name", name)
                .add("siteUrl", siteUrl)
                .toString();
    }
}
