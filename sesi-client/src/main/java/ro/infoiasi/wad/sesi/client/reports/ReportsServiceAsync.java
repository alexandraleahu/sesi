package ro.infoiasi.wad.sesi.client.reports;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.shared.ReportResult;

import java.util.List;

public interface ReportsServiceAsync {


    void getReportResult(ReportBean bean, AsyncCallback<List<ReportResult>> async);
}
