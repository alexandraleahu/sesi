package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 15:00
 */
@XmlRootElement
public class School {
    private String name;
    private String description;

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
}
