package ro.infoiasi.wad.sesi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import ro.infoiasi.wad.sesi.resources.SesiResources;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sesi implements EntryPoint {


    /**
     * Inject the CSS resource, the event bus, the rpc code generator service and load the main view.
     */
    @Override
    public void onModuleLoad() {
        SesiResources.INSTANCE.style().ensureInjected();
        HandlerManager eventBus = new HandlerManager(null);

    }
}
