package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;


public class IntegerEditor {
    interface IntegerEditorUiBinder extends UiBinder<HTMLPanel, IntegerEditor> {
    }

    private static IntegerEditorUiBinder ourUiBinder = GWT.create(IntegerEditorUiBinder.class);

    public IntegerEditor() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);

    }
}