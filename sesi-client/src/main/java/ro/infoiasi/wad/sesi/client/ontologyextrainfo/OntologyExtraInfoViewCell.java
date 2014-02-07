package ro.infoiasi.wad.sesi.client.ontologyextrainfo;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;

public class OntologyExtraInfoViewCell extends AbstractCell<OntologyExtraInfo> {

    @Override
    public void render(Context context, OntologyExtraInfo value, SafeHtmlBuilder sb) {

        OntologyExtraInfoView view = new OntologyExtraInfoView();
        view.setValue(value);
        sb.appendHtmlConstant(view.getElement().getString());

    }
}
