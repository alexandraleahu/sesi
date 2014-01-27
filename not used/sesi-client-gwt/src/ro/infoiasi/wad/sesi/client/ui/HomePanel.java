package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class HomePanel extends Tab {

    private static final String welcomeText = "<h3> Welcome to Sesi! </h3>"
            + "<p>Sesi is an website based on semantic Web made to "
            + "facilitate access for students to internship opportunities, "
            + "help companies better identify new interns "
            + "and also provides teachers the possibility to mentor their students in their first steps "
            + "of working inside a company. "
            + "</p>"
            ;
    
    public HomePanel() {
        leftPanel.add(getWelcomeText());
        leftPanel.add(getLatestInternships());
        rightPanel.add(getFeaturesCompanies());
    }
    
    public Widget getWelcomeText() {
        HTML html = new HTML();
        html.setHTML(welcomeText);
        return html;
    }
    
    public Widget getFeaturesCompanies() {
        return new HTML();
    }
    
    public Widget getLatestInternships() {
        return new HTML();
    }
}
