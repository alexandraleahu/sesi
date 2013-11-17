package ro.infoiasi.wad.sesi.core.model;

import org.joda.time.Duration;
import org.joda.time.Period;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement // used for XML/JSON (de)serialization
public class Internship {
    private String name;
    private String description;
    private String city;
    private Period period;
    private int openings;
    private String requirements;
    private List<String> skills;
    private boolean offeringRelocation;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getOpenings() {
        return openings;
    }

    public void setOpenings(int openings) {
        this.openings = openings;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public boolean isOfferingRelocation() {
        return offeringRelocation;
    }

    public void setOfferingRelocation(boolean offeringRelocation) {
        this.offeringRelocation = offeringRelocation;
    }
}
