package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;

public interface OntologyExtraInfoServiceAsync {

    void get(String ontologyClassName, String ontologyId, AsyncCallback<OntologyExtraInfo> async);
}
