package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import java.util.List;


@RemoteServiceRelativePath("InternshipsService")
public interface InternshipsService extends RemoteService {


    /**
     * Utility/Convenience class.
     * Use InternshipsService.App.getInstance() to access static instance of InternshipsServiceAsync
     */
    public static class App {
        private static final InternshipsServiceAsync ourInstance = (InternshipsServiceAsync) GWT.create(InternshipsService.class);

        public static InternshipsServiceAsync getInstance() {
            return ourInstance;
        }
    }

    Internship getInternshipById(String internshipId);

    int getApplicationsCount(String internshipId);

    List<Internship> getAllInternshipsByCategory(Internship.Category category);

    List<Internship> getAllInternships();

    List<InternshipApplication> getInternshipApplications(String internshipId);

    List<InternshipProgressDetails> getInternshipProgressDetails(String internshipId);

    void save(Internship internship);

    public static final String RESOURCE_PATH = "internships";
}
