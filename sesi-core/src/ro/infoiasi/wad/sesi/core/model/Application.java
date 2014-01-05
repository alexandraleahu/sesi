package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.EnumSet;

@XmlRootElement
public class Application extends StudentInternshipRelation {

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
}
