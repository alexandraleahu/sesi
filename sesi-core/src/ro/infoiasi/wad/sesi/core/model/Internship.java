package ro.infoiasi.wad.sesi.core.model;

import org.joda.time.Period;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement // used for XML/JSON (de)serialization
public class Internship implements Resource {

    private Company company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Internship that = (Internship) o;

        if (offeringRelocation != that.offeringRelocation) return false;
        if (openings != that.openings) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (requirements != null ? !requirements.equals(that.requirements) : that.requirements != null) return false;
        if (skills != null ? !skills.equals(that.skills) : that.skills != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + openings;
        result = 31 * result + (requirements != null ? requirements.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (offeringRelocation ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Internship{" +
                "company=" + company +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", period=" + period +
                ", openings=" + openings +
                ", requirements='" + requirements + '\'' +
                ", skills=" + skills +
                ", offeringRelocation=" + offeringRelocation +
                '}';
    }
}
