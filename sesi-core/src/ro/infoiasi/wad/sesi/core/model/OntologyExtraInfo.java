package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasOntologyUri;

public class OntologyExtraInfo extends BaseExtraInfo implements HasOntologyUri {

    private String ontologyUri;

    @Override
    public String getOntologyUri() {
        return ontologyUri;
    }

    public void setOntologyUri(String ontologyUri) {
        this.ontologyUri = ontologyUri;
    }
}
