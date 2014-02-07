package ro.infoiasi.wad.sesi.client.reports;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.shared.ReportResult;

import java.util.List;


@RemoteServiceRelativePath("ReportsService")
public interface ReportsService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use ReportsService.App.getInstance() to access static instance of ReportsServiceAsync
     */
    public static class App {
        private static final ReportsServiceAsync ourInstance = (ReportsServiceAsync) GWT.create(ReportsService.class);

        public static ReportsServiceAsync getInstance() {
            return ourInstance;
        }
    }


    List<ReportResult> getReportResult(ReportBean bean);

}
