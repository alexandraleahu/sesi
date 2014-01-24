package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;

import java.util.List;


@RemoteServiceRelativePath("CompaniesService")
public interface CompaniesService extends RemoteService {
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
}
