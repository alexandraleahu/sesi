package ro.infoiasi.wad.sesi.core.model;

import java.util.List;

public class Teacher implements Resource, Person {

    private Faculty faculty;
    private String id;
    private String description;
    private List<InternshipProgressDetails> monitoringInternships;
    private String name;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRelativeUri() {
        return "/teachers/" + getId();
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
        return "Teacher{" +
                "faculty=" + faculty +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", monitoringInternships=" + monitoringInternships +
                '}';
    }
}
