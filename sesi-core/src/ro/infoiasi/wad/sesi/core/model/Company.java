package ro.infoiasi.wad.sesi.core.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 14:59
 */
@XmlRootElement
public class Company extends BaseExtraInfo implements User, Resource {
    private String description;
    private int id;

    private Map<Internship, List<Application>> availableInternships;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Internship, List<Application>> getAvailableInternships() {
        return availableInternships;
    }

    public Set<Internship> getAvailableInternshipsWithoutApplications() {
        return Sets.newHashSet(availableInternships.keySet());
    }

    public void setAvailableInternships(Map<Internship, List<Application>> availableInternships) {
        this.availableInternships = availableInternships;
    }

    public void addInternship(Internship internship) {
        availableInternships.put(internship, Lists.<Application>newArrayList());
    }

    public void addApplication(Internship internship, Application application) {
        List<Application> applications = availableInternships.get(internship);
        applications.add(application);
    }

    @Override
    public Role getRole() {
        return Role.COMPANY;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        if (availableInternships != null ? !availableInternships.equals(company.availableInternships) : company.availableInternships != null)
            return false;
        if (description != null ? !description.equals(company.description) : company.description != null) return false;
        if ( getName() != null ? !getName().equals(company.getName()) : company.getName() != null) return false;
        if (getInfoUrl() != null ? !getInfoUrl().equals(company.getInfoUrl()) : company.getInfoUrl() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (getInfoUrl() != null ? getInfoUrl().hashCode() : 0);
        result = 31 * result + (availableInternships != null ? availableInternships.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "getName()='" + getName() + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", getInfoUrl()=" + getInfoUrl() +
                ", availableInternships=" + availableInternships +
                '}';
    }

}
