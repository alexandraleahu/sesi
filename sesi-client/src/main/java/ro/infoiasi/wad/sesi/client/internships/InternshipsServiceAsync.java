package ro.infoiasi.wad.sesi.client.internships;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;

import java.util.List;

public interface InternshipsServiceAsync {
    void getInternshipById(String internshipId, AsyncCallback<Internship> async);

    void getApplicationsCount(String internshipId, AsyncCallback<Integer> async);

    void getAllInternshipsByCategory(Internship.Category category, AsyncCallback<List<Internship>> async);

    void save(Internship internship, AsyncCallback<Void> async);

    void getAllInternships(AsyncCallback<List<Internship>> async);

    void getInternshipProgressDetails(String internshipId, AsyncCallback<List<Internship>> async);

    void getInternshipApplications(String internshipId, AsyncCallback<List<InternshipApplication>> async);
}
