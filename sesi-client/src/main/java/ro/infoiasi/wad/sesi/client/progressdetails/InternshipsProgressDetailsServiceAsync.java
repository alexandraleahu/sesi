package ro.infoiasi.wad.sesi.client.progressdetails;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import java.util.List;

public interface InternshipsProgressDetailsServiceAsync {
    void getAllProgressDetails(AsyncCallback<List<InternshipProgressDetails>> async);

    void getProgressDetailsById(String id, AsyncCallback<InternshipProgressDetails> async);
}
