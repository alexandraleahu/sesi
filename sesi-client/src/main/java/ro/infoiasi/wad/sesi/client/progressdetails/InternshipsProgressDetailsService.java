package ro.infoiasi.wad.sesi.client.progressdetails;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import java.util.List;

@RemoteServiceRelativePath("ProgressDetailsService")
public interface InternshipsProgressDetailsService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use InternshipsProgressDetailsService.App.getInstance() to access static instance of ProgressDetailsServiceAsync
     */
    public static class App {
        private static final InternshipsProgressDetailsServiceAsync ourInstance = (InternshipsProgressDetailsServiceAsync) GWT.create(InternshipsProgressDetailsService.class);

        public static InternshipsProgressDetailsServiceAsync getInstance() {
            return ourInstance;
        }
    }

    InternshipProgressDetails getProgressDetailsById(String id);

    List<InternshipProgressDetails> getAllProgressDetails();

    boolean updateStatus(String appId, StudentInternshipRelation.Status newStatus);
    boolean updateFeedback(String appId, String newFeedback);


    public static final String RESOURCE_PATH = "internshipProgressDetails";
}
