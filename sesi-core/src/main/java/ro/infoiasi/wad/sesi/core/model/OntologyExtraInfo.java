package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.core.util.HasOntologyUri;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OntologyExtraInfo extends BaseExtraInfo implements HasOntologyUri {

    private String ontologyUri;

    @Override
    public String getOntologyUri() {
        return ontologyUri;
    }

    public void setOntologyUri(String ontologyUri) {
        this.ontologyUri = ontologyUri;
    }

    public static <T extends OntologyExtraInfo> void fillWithOntologyExtraInfo(T instance, String name, String freebaseId) {

        instance.setName(name);
        instance.setInfoUrl(Constants.FREEBASE_INFO + freebaseId.substring(1));
        instance.setOntologyUri(Constants.FREEBASE_NS + freebaseId.substring(1).replace('/', '.'));

    }
}
