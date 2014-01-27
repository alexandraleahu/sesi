package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Hyperlink;
import ro.infoiasi.wad.sesi.client.Sesi;
import ro.infoiasi.wad.sesi.client.compositewidgets.InternshipEditor;
import ro.infoiasi.wad.sesi.client.compositewidgets.InternshipView;

public class CompaniesPanel extends Tab {

    public CompaniesPanel() {
        this.menuPanel.add(new CreateInternshipLink());
        this.menuPanel.add(new MyInternshipsLink());
    }

    class MyInternshipsLink extends Hyperlink {
        public MyInternshipsLink() {
            super("My internships", "");
            this.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent arg0) {
                    CompaniesPanel.this.leftPanel.clear();
                    CompaniesPanel.this.leftPanel.add(new InternshipView());
                }
            });
        }
    }
    
    class CreateInternshipLink extends Hyperlink {
        public CreateInternshipLink() {
            super("Create internship", "");
            this.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent arg0) {
                    CompaniesPanel.this.leftPanel.clear();
                    CompaniesPanel.this.leftPanel.add(new InternshipEditor());
                    Sesi.freebase();
                }
            });
        }
    }
}
