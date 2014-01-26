package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Err {

    public static void show(String message) {
        final PopupPanel pp = new PopupPanel();
        VerticalPanel vp = new VerticalPanel();
        HTML html = new HTML();
        html.setText("<font color=\"red\">" + message + "</font>");
        Button ok = new Button("OK");
        ok.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                pp.hide();
            }});

        vp.add(html);
        vp.add(ok);
        pp.add(vp);
        pp.center();
    }
}
