package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 14:59
 */
@XmlRootElement
public class Company {
    private String name;
    private String description;

    private List<Internship> availableInternships;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Internship> getAvailableInternships() {
        return availableInternships;
    }

    public void setAvailableInternships(List<Internship> availableInternships) {
        this.availableInternships = availableInternships;
    }
}
