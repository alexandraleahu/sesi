package ro.infoiasi.wad.sesi.client.commonwidgets;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Hyperlink;
import ro.infoiasi.wad.sesi.core.model.Resource;

public class ResourceViewCell<C extends Resource> extends AbstractCell<C> {


    @Override
    public void render(Context context, C resource, SafeHtmlBuilder sb) {
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setText(resource.getName());

        hyperlink.setTargetHistoryToken(resource.getRelativeUri());

        sb.appendHtmlConstant(hyperlink.getElement().getString());
    }
}
