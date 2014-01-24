package ro.infoiasi.wad.sesi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import ro.infoiasi.wad.sesi.client.compositewidgets.InternshipView;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsService;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsServiceAsync;
import ro.infoiasi.wad.sesi.core.model.Internship;
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
        InternshipsServiceAsync instance = InternshipsService.App.getInstance();
        RootLayoutPanel.get().setStyleName(SesiResources.INSTANCE.style().backgroundColor());

        instance.getInternshipById("003", new AsyncCallback<Internship>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Internship internship) {
                final InternshipView internshipView = new InternshipView();
                internshipView.edit(internship);
                RootLayoutPanel.get().add(internshipView);
            }
        });
    }
}
