package ro.infoiasi.wad.sesi.client.ontologyextrainfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;


public class OntologyExtraInfoView<T extends OntologyExtraInfo> extends Composite implements LeafValueEditor<T> {


    @Override
    public void setValue(T value) {
        if (value != null) {
            ontologyExtraInfoLink.setText(value.getName());
            String infoUrl = value.getInfoUrl();
            if (infoUrl != null) {

                if (!infoUrl.startsWith("http://")) {

                    ontologyExtraInfoLink.setHref("http://" + infoUrl);
                }
                else {
                    ontologyExtraInfoLink.setHref(infoUrl);
                }
            }
        }

    }

    @Override
    public T getValue() {
        return null; // it is just for viewing
    }

    interface OntologyExtraInfoViewUiBinder extends UiBinder<Anchor, OntologyExtraInfoView> {
    }

    private static OntologyExtraInfoViewUiBinder ourUiBinder = GWT.create(OntologyExtraInfoViewUiBinder.class);
    @UiField
    Anchor ontologyExtraInfoLink;

    public OntologyExtraInfoView() {
         initWidget(ourUiBinder.createAndBindUi(this));

    }
}