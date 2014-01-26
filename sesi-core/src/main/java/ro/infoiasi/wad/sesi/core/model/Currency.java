package ro.infoiasi.wad.sesi.core.model;

public class Currency extends OntologyExtraInfo {

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Currency{");
        sb.append("extraInfoUrl='").append(getInfoUrl()).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", ontologyUri='").append(getOntologyUri()).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
