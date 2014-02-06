package ro.infoiasi.wad.sesi.client.schools;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;


@RemoteServiceRelativePath("SchoolsService")
public interface SchoolsService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use SchoolsService.App.getInstance() to access static instance of SchoolsServiceAsync
     */
    public static class App {
        private static final SchoolsServiceAsync ourInstance = (SchoolsServiceAsync) GWT.create(SchoolsService.class);

        public static SchoolsServiceAsync getInstance() {
            return ourInstance;
        }
    }

    List<String> getAllFacultyNames();
}
