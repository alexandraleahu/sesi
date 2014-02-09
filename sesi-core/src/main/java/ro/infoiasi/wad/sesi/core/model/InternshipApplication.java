package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@XmlRootElement
public class InternshipApplication extends StudentInternshipRelation {

    public static final List<Status> POSSIBLE_STATUSES = Arrays.asList(Status.accepted, Status.pending, Status.rejected);

    private String motivation;
    private Date publishedAt;

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public InternshipApplication() {
        setStatus(Status.pending);
    }

    @Override
    public EnumSet<Status> getPossibleStatus() {
        return EnumSet.copyOf(POSSIBLE_STATUSES);
    }

    @Override
    public String getDescription() {
        return getFeedback();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InternshipApplication{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", internship='").append(getInternship()).append('\'');
        sb.append(", student='").append(getStudent()).append('\'');
        sb.append(", status='").append(getStatus()).append('\'');
        sb.append(", feedback='").append(getFeedback()).append('\'');
        sb.append(", motivation='").append(getMotivation()).append('\'');
        sb.append(", publishedAt='").append(getPublishedAt()).append('\'');

        return sb.toString();
    }


    @Override
    public String getRelativeUri() {
        return "/applications/" + getId();
    }

    @Override
    public String getName() {
        if (getInternship() != null && getStudent() != null) {

            return getStudent().getId() + " - " + getInternship().getId();
        }

        return getRelativeUri();
    }
}
