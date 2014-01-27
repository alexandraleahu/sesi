package ro.infoiasi.wad.sesi.client;

import ro.infoiasi.wad.sesi.client.authentication.users.User;
import ro.infoiasi.wad.sesi.client.ui.ContentBuilder;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sesi_client_gwt implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
//    private final GreetingServiceAsync greetingService = GWT
//            .create(GreetingService.class);
    
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        ContentBuilder.buildRootPanelContent();
    }

    public User getCurrentUser() {
        return User.UNKNOWN_USER;
    }
}
