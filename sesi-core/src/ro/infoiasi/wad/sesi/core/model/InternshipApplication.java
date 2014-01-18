package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.EnumSet;

@XmlRootElement
public class InternshipApplication extends StudentInternshipRelation {

    private String description;

    @Override
    public EnumSet<Status> getPossibleStatus() {
        return EnumSet.of(Status.Accepted, Status.Rejected, Status.Pending);
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
                .add("internship", getInternship())
                .add("student", getStudent())
                .add("status", getStatus())
                .add("description", description)
                .toString();
    }
}
