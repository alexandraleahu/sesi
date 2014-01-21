package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

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
        return Objects.toStringHelper(this)
                .add("extraInfoUrl", getInfoUrl())
                .add("name", getName())
                .add("ontologyUri", getOntologyUri())
                .toString();
    }
}
