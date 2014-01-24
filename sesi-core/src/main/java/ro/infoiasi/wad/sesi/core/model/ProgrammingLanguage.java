package ro.infoiasi.wad.sesi.core.model;

public class ProgrammingLanguage extends OntologyExtraInfo {

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("extraInfoUrl='").append(getInfoUrl()).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", ontologyUri='").append(getOntologyUri()).append('\'');

        return sb.toString();
    }
}
