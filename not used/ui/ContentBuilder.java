package ro.infoiasi.wad.sesi.client.ui;

import com.github.gwtbootstrap.client.ui.Hero;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import ro.infoiasi.wad.sesi.resources.SesiResources;

public class ContentBuilder {
    
    public static void buildRootPanelContent() {
        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.setStyleName(SesiResources.INSTANCE.style().backgroundColor());
        rootLayoutPanel.add(getMainPanel());
    }

    private static Widget getMainPanel() {
        VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");

        Hero hero = new Hero();
        Label title = new Label("Semantic Student Internships");
        title.setStyleName(SesiResources.INSTANCE.style().bigLabel());
        hero.add(title);
        vp.add(hero);
        Widget up = getUserPanel();
        vp.add(up);
        vp.setCellHorizontalAlignment(up, HorizontalAlignmentConstant.endOf(Direction.LTR));
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
