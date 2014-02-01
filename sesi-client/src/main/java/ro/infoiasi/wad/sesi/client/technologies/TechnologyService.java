package ro.infoiasi.wad.sesi.client.technologies;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;


@RemoteServiceRelativePath("TechnologyService")
public interface TechnologyService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use TechnologyService.App.getInstance() to access static instance of TechnologyServiceAsync
     */
    public static class App {
        private static final TechnologyServiceAsync ourInstance = (TechnologyServiceAsync) GWT.create(TechnologyService.class);

        public static TechnologyServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
