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
public class Company extends BaseExtraInfo implements Resource {
    private String description;
    private String id;
    private boolean active;
    private String siteUrl;
    private int communityRating;

    private Map<Internship, List<InternshipApplication>> availableInternships;

    private Map<Internship, List<InternshipProgressDetails>> onGoingAndFinishedInternships;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Map<Internship, List<InternshipApplication>> getAvailableInternships() {
        return availableInternships;
    }

    public Set<Internship> getAvailableInternshipsWithoutApplications() {
        return Sets.newHashSet(availableInternships.keySet());
    }

    public void setAvailableInternships(Map<Internship, List<InternshipApplication>> availableInternships) {
        this.availableInternships = availableInternships;
    }

    public void addInternship(Internship internship) {
        availableInternships.put(internship, Lists.<InternshipApplication>newArrayList());
    }

    public void addApplication(Internship internship, InternshipApplication application) {
        List<InternshipApplication> applications = availableInternships.get(internship);
        applications.add(application);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRelativeUri() {
        return "/companies/" + getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCommunityRating() {
        return communityRating;
    }


    public void setCommunityRating(int communityRating) {
        this.communityRating = communityRating;
    }

    public Map<Internship, List<InternshipProgressDetails>> getOnGoingAndFinishedInternships() {
        return onGoingAndFinishedInternships;
    }

    public void setOnGoingAndFinishedInternships(Map<Internship, List<InternshipProgressDetails>> onGoingAndFinishedInternships) {
        this.onGoingAndFinishedInternships = onGoingAndFinishedInternships;
    }

    @Override
    public String toString() {
        return "Company{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", active=" + active +
                ", communityRating=" + communityRating +
                ", availableInternships=" + availableInternships +
                ", onGoingAndFinishedInternships=" + onGoingAndFinishedInternships +
                '}';
    }
}
