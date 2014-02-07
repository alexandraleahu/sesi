package ro.infoiasi.wad.sesi.server.reports;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ro.infoiasi.wad.sesi.client.reports.ReportBean;
import ro.infoiasi.wad.sesi.client.reports.ReportsService;
import ro.infoiasi.wad.sesi.server.sparqlservice.SparqlService;
import ro.infoiasi.wad.sesi.shared.ReportResult;

import java.util.List;


public class ReportsServiceImpl extends RemoteServiceServlet implements ReportsService {

    @Override
    public List<ReportResult> getReportResult(ReportBean bean) {

        String query = ReportQueryBuilder.buildQuery(bean);

        SparqlService sparqlService = new SparqlService();
        return sparqlService.getReportResults(query);
    }
}