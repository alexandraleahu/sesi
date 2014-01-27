package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;

public class TeachersPanel extends Tab {

    public TeachersPanel() {
        menuPanel.add(new BrowseStudentsLink());
    }

    class BrowseStudentsLink extends Hyperlink {
        public BrowseStudentsLink() {
            super("Browse students", "");
            this.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent arg0) {
                    TeachersPanel.this.leftPanel.clear();
                    // TeachersPanel.this.leftPanel.add(new BrowseStudentsView());
                }
            });
        }
    }
}
