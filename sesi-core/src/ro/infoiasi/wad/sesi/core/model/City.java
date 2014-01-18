package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

public class City extends OntologyExtraInfo {

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("extraInfoUrl", getInfoUrl())
                .add("name", getName())
                .add("ontologyUri", getOntologyUri())
                .toString();
    }
}
