package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;


@RemoteServiceRelativePath("InternshipsService")
public interface InternshipsService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use InternshipsService.App.getInstance() to access static instance of InternshipsServiceAsync
     */
    public static class App {
        private static final InternshipsServiceAsync ourInstance = (InternshipsServiceAsync) GWT.create(InternshipsService.class);

        public static InternshipsServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
