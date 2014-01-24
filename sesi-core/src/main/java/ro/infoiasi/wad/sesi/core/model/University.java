package ro.infoiasi.wad.sesi.core.model;

public class University  extends OntologyExtraInfo {
    private String siteUrl;
    private String label;
    private City city;

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("University{");
        sb.append("siteUrl='").append(siteUrl).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", city=").append(city);
        sb.append('}');
        return sb.toString();
    }
}
