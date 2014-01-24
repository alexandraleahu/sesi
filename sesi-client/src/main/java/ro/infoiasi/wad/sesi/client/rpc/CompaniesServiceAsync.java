package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;

import java.util.List;

public interface CompaniesServiceAsync {
    void getCompanyById(String companyId, AsyncCallback<Company> async);

    void getCompanyApplications(String companyApplications, AsyncCallback<List<InternshipApplication>> async);
}
