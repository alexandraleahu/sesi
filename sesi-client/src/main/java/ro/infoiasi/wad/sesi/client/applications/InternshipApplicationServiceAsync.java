package ro.infoiasi.wad.sesi.client.applications;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;

import java.util.List;

public interface InternshipApplicationServiceAsync {
    void getApplicationById(String companyId, AsyncCallback<InternshipApplication> async);

    void getAllApplications(AsyncCallback<List<InternshipApplication>> async);
}
