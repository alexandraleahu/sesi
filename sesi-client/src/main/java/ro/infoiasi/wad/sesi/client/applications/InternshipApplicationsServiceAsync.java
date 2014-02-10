package ro.infoiasi.wad.sesi.client.applications;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import java.util.List;

public interface InternshipApplicationsServiceAsync {
    void getApplicationById(String companyId, AsyncCallback<InternshipApplication> async);

    void getAllApplications(AsyncCallback<List<InternshipApplication>> async);

    void createApplication(String studentId, String internshipId, String motivation, AsyncCallback<InternshipApplication> async);


    void updateStatus(String appId, StudentInternshipRelation.Status newStatus, AsyncCallback<Boolean> async);

    void updateFeedback(String appId, String newFeedback, AsyncCallback<Boolean> async);
}
