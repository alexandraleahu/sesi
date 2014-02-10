package ro.infoiasi.wad.sesi.client.progressdetails;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import java.util.List;

public interface InternshipsProgressDetailsServiceAsync {
    void getAllProgressDetails(AsyncCallback<List<InternshipProgressDetails>> async);

    void getProgressDetailsById(String id, AsyncCallback<InternshipProgressDetails> async);

    void updateStatus(String appId, StudentInternshipRelation.Status newStatus, AsyncCallback<Boolean> async);

    void updateFeedback(String appId, String newFeedback, AsyncCallback<Boolean> async);
}
