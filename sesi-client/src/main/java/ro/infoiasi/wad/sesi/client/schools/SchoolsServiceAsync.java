package ro.infoiasi.wad.sesi.client.schools;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface SchoolsServiceAsync {
    void getAllFacultyNames(AsyncCallback<List<String>> async);
}
