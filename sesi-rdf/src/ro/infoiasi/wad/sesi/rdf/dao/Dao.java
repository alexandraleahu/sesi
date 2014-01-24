package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.Values;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.OWL;

public interface Dao {

    String getOntClassName();
    URI OWL_NAMED_INDIVIDUAL = Values.uri(OWL.NAMESPACE, "NamedIndividual");
}
