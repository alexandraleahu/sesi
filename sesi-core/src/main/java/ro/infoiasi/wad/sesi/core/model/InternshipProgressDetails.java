package ro.infoiasi.wad.sesi.core.model;

import java.util.EnumSet;

public class InternshipProgressDetails extends StudentInternshipRelation {

    private Teacher teacher;

    @Override
    public EnumSet<Status> getPossibleStatus() {
        return EnumSet.of(Status.inProgress, Status.finished);
    }

    @Override
    public String getDescription() {
        return getFeedback();
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InternshipProgressDetails{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", internship='").append(getInternship()).append('\'');
        sb.append(", student='").append(getStudent()).append('\'');
        sb.append(", status='").append(getStatus()).append('\'');
        sb.append(", description='").append(getDescription()).append('\'');
        sb.append(", teacher='").append(getTeacher()).append('\'');
        sb.append(", feedback='").append(getFeedback()).append('\'');

        return sb.toString();
    }

    @Override
    public String getRelativeUri() {
        return "/internshipsProgressDetails/" + getId();
    }


}
