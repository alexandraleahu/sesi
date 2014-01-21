package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.EnumSet;

@XmlRootElement
public class InternshipApplication extends StudentInternshipRelation {

    private String description;

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
        return Objects.toStringHelper(this)
                .add("id", getId())
                .add("internship", getInternshipId())
                .add("student", getStudentId())
                .add("status", getStatus())
                .add("description", description)
                .toString();
    }

    @Override
    public String getRelativeUri() {
        return "/applications/" + getId();
    }
}
