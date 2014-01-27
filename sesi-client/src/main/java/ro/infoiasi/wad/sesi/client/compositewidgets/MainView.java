package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;


public class MainView {
    interface MainViewUiBinder extends UiBinder<HTMLPanel, MainView> {
    }

    private static MainViewUiBinder ourUiBinder = GWT.create(MainViewUiBinder.class);

    public MainView() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);

    }
}