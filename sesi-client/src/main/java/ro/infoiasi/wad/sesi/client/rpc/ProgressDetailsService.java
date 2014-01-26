package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;

import java.util.List;

@RemoteServiceRelativePath("ProgressDetailsService")
public interface ProgressDetailsService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use ProgressDetailsService.App.getInstance() to access static instance of ProgressDetailsServiceAsync
     */
    public static class App {
        private static final ProgressDetailsServiceAsync ourInstance = (ProgressDetailsServiceAsync) GWT.create(ProgressDetailsService.class);

        public static ProgressDetailsServiceAsync getInstance() {
            return ourInstance;
        }
    }

    InternshipProgressDetails getProgressDetailsById(String id);

    List<InternshipProgressDetails> getAllProgressDetails();

    public static final String RESOURCE_PATH = "internshipProgressDetails";
}
