package ro.infoiasi.wad.sesi.server.deserializerinterfaces;

import com.hp.hpl.jena.ontology.OntModel;

public interface Deserializer<T> {

    T deserialize(OntModel m, String id);
}
