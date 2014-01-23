package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;


public class InternshipView {
    interface InternshipViewUiBinder extends UiBinder<DivElement, InternshipView> {
    }

    private static InternshipViewUiBinder ourUiBinder = GWT.create(InternshipViewUiBinder.class);

    public InternshipView() {
        DivElement rootElement = ourUiBinder.createAndBindUi(this);

    }
}