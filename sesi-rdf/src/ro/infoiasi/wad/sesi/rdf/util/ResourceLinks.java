package ro.infoiasi.wad.sesi.rdf.util;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.net.URI;

public class ResourceLinks implements Serializable {

    private URI resourceUri;
    private String sesiRelativeUrl;

    public ResourceLinks(URI resourceUri, String sesiRelativeUrl) {
        this.resourceUri = resourceUri;
        this.sesiRelativeUrl = sesiRelativeUrl;
    }

    public ResourceLinks() {}

    public URI getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(URI resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String getSesiRelativeUrl() {
        return sesiRelativeUrl;
    }

    public void setSesiRelativeUrl(String sesiRelativeUrl) {
        this.sesiRelativeUrl = sesiRelativeUrl;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("resourceUri", resourceUri)
                .add("sesiRelativeUrl", sesiRelativeUrl)
                .toString();
    }
}
