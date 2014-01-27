package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;

public class StudentsPanel extends Tab {
    
    public StudentsPanel() {
        this.menuPanel.add(new MyApplicationsLink());
        this.menuPanel.add(new ViewInternshipsLink());
    }

class MyApplicationsLink extends Hyperlink {
    
    public MyApplicationsLink() {
        super("My applications", "");
        this.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                leftPanel.clear();
                //StudentsPanel.this.leftPanel.add(new ApplicationsView());
            }
        });
    }
}

class ViewInternshipsLink extends Hyperlink {
    public ViewInternshipsLink() {
        super("View internships", "");
        this.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                leftPanel.clear();
                //StudentsPanel.this.leftPanel.add(new InternshipsView());
            }
        });
    }
}
}