package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.Internship;

import java.util.List;

public interface InternshipsServiceAsync {
    void getInternshipById(String internshipId, AsyncCallback<Internship> async);

    void getApplicationsCount(String internshipId, AsyncCallback<Integer> async);

    void getAllinternshipsByCategory(Internship.Category category, AsyncCallback<List<Internship>> async);
}
