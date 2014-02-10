package ro.infoiasi.wad.sesi.client.applications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;

import java.util.List;

@RemoteServiceRelativePath("InternshipApplicationService")
public interface InternshipApplicationsService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use InternshipApplicationsService.App.getInstance() to access static instance of InternshipApplicationServiceAsync
     */
    public static class App {
        private static final InternshipApplicationsServiceAsync ourInstance = (InternshipApplicationsServiceAsync) GWT.create(InternshipApplicationsService.class);

        public static InternshipApplicationsServiceAsync getInstance() {
            return ourInstance;
        }
    }

    InternshipApplication getApplicationById(String companyId);

    List<InternshipApplication> getAllApplications();

    public static final String RESOURCE_PATH = "applications";
}