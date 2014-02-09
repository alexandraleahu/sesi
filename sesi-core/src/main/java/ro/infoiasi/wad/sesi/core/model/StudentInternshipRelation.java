package ro.infoiasi.wad.sesi.core.model;

import java.util.EnumSet;

public abstract class StudentInternshipRelation implements Resource {

    public static enum Status {

        accepted,
        rejected,
        pending,
        inProgress,
        finished
    }

    private Internship internship;
    private Student student;
    private String id;
    private Status status;
    private String feedback;


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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String getName() {
        return getStudent().getId() + " - " + getInternship().getId();
    }

    public void setStatus(Status status) {
        if (!getPossibleStatus().contains(status))
            throw new IllegalArgumentException("Can only set the status to " + getPossibleStatus().toString());
    }
}
