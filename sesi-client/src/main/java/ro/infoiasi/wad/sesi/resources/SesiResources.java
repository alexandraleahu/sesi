package ro.infoiasi.wad.sesi.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface SesiResources extends ClientBundle {

    SesiResources INSTANCE = GWT.create(SesiResources.class);

    /**
     * An Sesi CSS resource.
     * @return the CSS implementing Style
     */
    @NotStrict
    @Source({"../../../../../../webapp/css/Sesi.css"})
    Style style();

    public interface Style extends CssResource {

        String dialogVPanel();

        @ClassName("gwt-DialogBox")
        String gwtDialogBox();

        String sendButton();

        String serverResponseLabelError();
    }
}
