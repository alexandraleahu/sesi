package ro.infoiasi.wad.sesi.core.model;

import java.util.EnumSet;

public abstract class StudentInternshipRelation implements Resource {

    public static enum Status {

        Accepted,
        Rejected,
        Pending,
        InProgress,
        Finished
    }

    private Internship internship;
    private Student student;
    private String id;
    private Status status;

    public Internship getInternship() {
        return internship;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public abstract EnumSet<Status> getPossibleStatus();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (!getPossibleStatus().contains(status))
            throw new IllegalArgumentException("Can only set the status of " + getClass().getSimpleName() + " to " + getPossibleStatus().toString());
    }
}
