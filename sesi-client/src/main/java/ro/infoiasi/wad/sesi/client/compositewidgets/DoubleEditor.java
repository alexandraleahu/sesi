package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;


public class DoubleEditor {
    interface DoubleEditorUiBinder extends UiBinder<HTMLPanel, DoubleEditor> {
    }

    private static DoubleEditorUiBinder ourUiBinder = GWT.create(DoubleEditorUiBinder.class);

    public DoubleEditor() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);

    }
}