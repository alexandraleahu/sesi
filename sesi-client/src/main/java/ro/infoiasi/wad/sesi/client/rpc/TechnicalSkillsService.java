package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("TechnicalSkillsService")
public interface TechnicalSkillsService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use TechnicalSkillsService.App.getInstance() to access static instance of TechnicalSkillsServiceAsync
     */
    public static class App {
        private static final TechnicalSkillsServiceAsync ourInstance = (TechnicalSkillsServiceAsync) GWT.create(TechnicalSkillsService.class);

        public static TechnicalSkillsServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
