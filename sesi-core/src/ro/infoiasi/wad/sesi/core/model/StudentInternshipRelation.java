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

    private String internshipId;
    private String studentId;
    private String id;
    private Status status;

    public String getInternshipId() {
        return internshipId;
    }

    public void setInternshipId(String internshipId) {
        this.internshipId = internshipId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
