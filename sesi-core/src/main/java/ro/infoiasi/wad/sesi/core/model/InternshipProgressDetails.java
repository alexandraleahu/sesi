package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@XmlRootElement
public class InternshipProgressDetails extends StudentInternshipRelation {

    public static final List<Status> POSSIBLE_STATUSES = Arrays.asList(Status.inProgress, Status.finished);
    private Teacher teacher;

    @Override
    public Set<Status> getPossibleStatus() {
        return EnumSet.copyOf(POSSIBLE_STATUSES);
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
