package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class City extends OntologyExtraInfo {

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("extraInfoUrl='").append(getInfoUrl()).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", ontologyUri='").append(getOntologyUri()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
