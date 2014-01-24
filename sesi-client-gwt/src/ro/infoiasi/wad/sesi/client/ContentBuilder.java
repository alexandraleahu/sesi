package ro.infoiasi.wad.sesi.client;

import ro.infoiasi.wad.sesi.client.authentication.UserPanel;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class ContentBuilder {

    private static final String[] tabTitles = {"Home", "Students", "Companies", "Teachers"};
    
    public static void buildRootPanelContent() {
        RootPanel.get("mainPanel").add(getMainPanel());
    }

    private static Widget getMainPanel() {
        VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");
        Widget up = getUserPanel();
        vp.add(up);
        vp.setCellHorizontalAlignment(up, HorizontalAlignmentConstant.endOf(Direction.LTR));
        HTML title = new HTML();
        title.setHTML("<h1>Semantic Student Internships</h1>");
        vp.add(title);
        vp.add(getTabPanel());
        return vp;
    }

    private static Widget getTabPanel() {
        // Create a tab panel
        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
        tabPanel.setAnimationDuration(1000);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Unit.PX);

        for (String tab : tabTitles) {
            tabPanel.add(getContentWidget(tab), tab);
        }

        // Return the content
        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");
        
        return tabPanel;
        
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
        return new HorizontalPanel();
    }

    private static Widget getCompaniesWidget() {
        return new HorizontalPanel();
    }

    private static Widget getStudentsWidget() {
        return new HorizontalPanel();
    }

    private static Widget getHomeWidget() {
        return new HorizontalPanel();
    }

    private static Widget getUserPanel() {
        return new UserPanel();
    }
}
