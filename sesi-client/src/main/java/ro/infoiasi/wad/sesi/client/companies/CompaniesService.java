package ro.infoiasi.wad.sesi.client.companies;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;

import java.util.List;


@RemoteServiceRelativePath("CompaniesService")
public interface CompaniesService extends RemoteService {
    boolean registerCompany(String username, String password, String name);

    /**
     * Utility/Convenience class.
     * Use CompaniesService.App.getInstance() to access static instance of CompaniesServiceAsync
     */
    public static class App {
        private static final CompaniesServiceAsync ourInstance = (CompaniesServiceAsync) GWT.create(CompaniesService.class);

        public static CompaniesServiceAsync getInstance() {
            return ourInstance;
        }
    }

    Company getCompanyById(String companyId);

    List<InternshipApplication> getCompanyApplications(String companyApplications);

    List<Company> getAllCompanies();

    List<Internship> getCompanyInternships(String companyId);

    List<InternshipProgressDetails> getCompanyProgressDetails(String companyId);

    List<String> getAllCompaniesNames();

    public static final String RESOURCE_PATH = "companies";
}
