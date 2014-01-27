package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContentBuilder {
    
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
        return new SesiTabPanel();
    }

    private static Widget getUserPanel() {
        return new UserPanel();
    }
}
