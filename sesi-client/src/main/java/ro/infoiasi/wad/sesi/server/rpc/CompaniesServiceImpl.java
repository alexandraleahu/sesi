package ro.infoiasi.wad.sesi.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ro.infoiasi.wad.sesi.client.rpc.CompaniesService;
import ro.infoiasi.wad.sesi.core.model.Company;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;

import java.util.List;


public class CompaniesServiceImpl extends RemoteServiceServlet implements CompaniesService {
    @Override
    public Company getCompanyById(String companyId) {
        return null;
    }

    @Override
    public List<InternshipApplication> getCompanyApplications(String companyApplications) {
        return null;
    }
}