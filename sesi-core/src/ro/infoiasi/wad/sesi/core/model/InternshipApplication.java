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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InternshipApplication that = (InternshipApplication) o;
        if (getId() != that.getId()) return false;
        if (!getInternship().equals(that.getInternship())) return false;
        if (!getStudent().equals(that.getStudent())) return false;
        if (!getStatus().equals(that.getStatus())) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + getId();
        result = 31 * result + getStudent().hashCode();
        result = 31 * result + getInternship().hashCode();
        result = 31 * result + getStatus().hashCode();

        return result;
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
