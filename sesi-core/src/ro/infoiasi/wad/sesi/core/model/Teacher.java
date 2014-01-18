package ro.infoiasi.wad.sesi.core.model;

import java.util.List;

public class Teacher implements Resource, Person {

    private School school;
    private String id;
    private String description;
    private List<InternshipProgressDetails> monitoringInternships;
    private String name;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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
                "school=" + school +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", monitoringInternships=" + monitoringInternships +
                '}';
    }
}
