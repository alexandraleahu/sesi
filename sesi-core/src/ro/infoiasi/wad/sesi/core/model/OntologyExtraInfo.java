package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasOntologyUri;

import java.net.URI;

public class OntologyExtraInfo extends BaseExtraInfo implements HasOntologyUri {

    private URI ontologyUri;

    @Override
    public URI getOntologyUri() {
        return ontologyUri;
    }

    public void setOntologyUri(URI ontologyUri) {
        this.ontologyUri = ontologyUri;
    }
}
