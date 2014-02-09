package ro.infoiasi.wad.sesi.client.authentication;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.StudentLinkedinProfile;
import ro.infoiasi.wad.sesi.core.model.User;


@RemoteServiceRelativePath("SigninService")
public interface SigninService extends RemoteService {
    StudentLinkedinProfile getProfile(String verifierToken) throws Exception;

    /**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class App {
		private static SigninServiceAsync instance;
		public static SigninServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(SigninService.class);
			}
			return instance;
		}
	}

	public String getAuthenticateUrl(String provider, String callbackUrl) throws Exception;
	public User verify(String verifier) throws Exception;
}
