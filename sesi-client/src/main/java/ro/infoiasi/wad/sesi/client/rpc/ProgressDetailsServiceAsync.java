package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import java.util.List;

public interface ProgressDetailsServiceAsync {
    void getAllProgressDetails(AsyncCallback<List<InternshipProgressDetails>> async);

    void getProgressDetailsById(String id, AsyncCallback<InternshipProgressDetails> async);
}
