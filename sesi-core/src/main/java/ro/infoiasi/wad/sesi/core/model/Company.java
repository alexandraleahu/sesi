package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;

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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Company{");
        sb.append("description='").append(description).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", active=").append(active);
        sb.append(", siteUrl='").append(siteUrl).append('\'');
        sb.append(", communityRating=").append(communityRating);
        sb.append('}');
        return sb.toString();
    }
}
