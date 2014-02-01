package ro.infoiasi.wad.sesi.client.applications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;

import java.util.List;

@RemoteServiceRelativePath("InternshipApplicationService")
public interface InternshipApplicationService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use InternshipApplicationService.App.getInstance() to access static instance of InternshipApplicationServiceAsync
     */
    public static class App {
        private static final InternshipApplicationServiceAsync ourInstance = (InternshipApplicationServiceAsync) GWT.create(InternshipApplicationService.class);

        public static InternshipApplicationServiceAsync getInstance() {
            return ourInstance;
        }
    }

    InternshipApplication getApplicationById(String companyId);

    List<InternshipApplication> getAllApplications();

    public static final String RESOURCE_PATH = "applications";
}
