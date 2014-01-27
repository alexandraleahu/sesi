package ro.infoiasi.wad.sesi.client.compositewidgets;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ro.infoiasi.wad.sesi.client.rpc.InternshipsService;
import ro.infoiasi.wad.sesi.core.model.Internship;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class InternshipListView extends VerticalPanel {

    private List<Internship> internships = Collections.EMPTY_LIST;
    
    public InternshipListView() {
        setStyleName("internship_list");
        InternshipsAsync ia = new InternshipsAsync();
        InternshipsService.App.getInstance().getAllInternships(ia);
        internships.addAll(ia.getInternships());
    }

    public InternshipListView filterCompany(String companyId) {
        List<Internship> filtered = new LinkedList<Internship>();
        for(Internship internship : internships) {
            if(internship.getCompany().getId().equals(companyId)) {
                filtered.add(internship);
            }
        }
        internships = filtered;
        return this;
    }

    public void display() {
        for (Internship internship : internships) {
            add(getInternshipWidget(internship));
        }
    }

    private Widget getInternshipWidget(Internship internship) {
        HorizontalPanel hp = new HorizontalPanel();
        hp.setHeight("25 em");
        HTML html = new HTML();
        html.setText(internship.getCompany().getName());
        Hyperlink h = new Hyperlink();
        h.setText(internship.getName());
        hp.add(html);
        hp.add(h);
        hp.setCellWidth(html, "25%");
        hp.setCellWidth(h, "75%");
        hp.setStyleName("small_internship");
        return hp;
    }
    
    class InternshipsAsync implements AsyncCallback<List<Internship>> {
        private volatile List<Internship> internships = Collections.EMPTY_LIST;
        
        public void onFailure(Throwable arg0) {
            arg0.printStackTrace();
        }

        public void onSuccess(List<Internship> arg0) {
            internships = arg0;
        }
        
        public synchronized List<Internship> getInternships() {
            return internships;
        }
    }
}
