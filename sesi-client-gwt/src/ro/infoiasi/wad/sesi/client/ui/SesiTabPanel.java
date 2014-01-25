package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class SesiTabPanel extends TabLayoutPanel {

    private static final String[] tabTitles = {"Home", "Students", "Companies", "Teachers"};
    
    public SesiTabPanel() {
        // Create a tab panel
        super(2.5, Unit.EM);
        getElement().getStyle().setMarginBottom(10.0, Unit.PX);

        for (String tab : tabTitles) {
            add(getContentWidget(tab), tab);
        }

        // Return the content
        selectTab(0);
        ensureDebugId("cwTabPanel");
    }

    private static Widget getContentWidget(String tab) {
        if (tab.equals(tabTitles[0]))
            return getHomeWidget();
        if (tab.equals(tabTitles[1]))
            return getStudentsWidget();
        if (tab.equals(tabTitles[2]))
            return getCompaniesWidget();
        if (tab.equals(tabTitles[3]))
            return getTeachersWidget();
        return null;
    }

    private static Widget getTeachersWidget() {
        return new TeachersPanel();
    }

    private static Widget getCompaniesWidget() {
        return new CompaniesPanel();
    }

    private static Widget getStudentsWidget() {
        return new StudentsPanel();
    }

    private static Widget getHomeWidget() {
        return new HomePanel();
    }
}
