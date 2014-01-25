package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.EnumSet;

@XmlRootElement
public class InternshipApplication extends StudentInternshipRelation {

    private String description;
    private String motivation;

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    @Override
    public EnumSet<Status> getPossibleStatus() {
        return EnumSet.of(Status.accepted, Status.rejected, Status.pending);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", internship='").append(getInternship()).append('\'');
        sb.append(", student='").append(getStudent()).append('\'');
        sb.append(", status='").append(getStatus()).append('\'');
        sb.append(", description='").append(getDescription()).append('\'');
        sb.append(", motivation='").append(getMotivation()).append('\'');

        return sb.toString();
    }


    @Override
    public String getRelativeUri() {
        return "/applications/" + getId();
    }
}
