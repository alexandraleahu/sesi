package ro.infoiasi.wad.sesi.client.ontologyextrainfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;


@RemoteServiceRelativePath("OntologyExtraInfoService")
public interface OntologyExtraInfoService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use OntologyExtraInfoService.App.getInstance() to access static instance of OntologyExtraInfoServiceAsync
     */
    public static class App {
        private static final OntologyExtraInfoServiceAsync ourInstance = (OntologyExtraInfoServiceAsync) GWT.create(OntologyExtraInfoService.class);

        public static OntologyExtraInfoServiceAsync getInstance() {
            return ourInstance;
        }
    }

    OntologyExtraInfo get(String ontologyClassName, String ontologyId);
}
