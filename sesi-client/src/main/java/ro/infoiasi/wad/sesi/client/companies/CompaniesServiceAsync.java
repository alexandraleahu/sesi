package ro.infoiasi.wad.sesi.client.companies;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import java.util.List;

public interface CompaniesServiceAsync {
    void getCompanyById(String companyId, AsyncCallback<Company> async);

    void getCompanyApplications(String companyId, AsyncCallback<List<InternshipApplication>> async);

    void getAllCompanies(AsyncCallback<List<Company>> async);

    void getCompanyInternships(String companyId, AsyncCallback<List<Internship>> async);

    void getCompanyProgressDetails(String companyId, AsyncCallback<List<InternshipProgressDetails>> async);

    void getAllCompaniesNames(AsyncCallback<List<String>> async);

    void registerCompany(String username, String password, String name, AsyncCallback<Boolean> async);

    void updateCompany(Company company, AsyncCallback<Boolean> async);
}
