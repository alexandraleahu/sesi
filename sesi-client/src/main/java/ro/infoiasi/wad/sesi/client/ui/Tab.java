package ro.infoiasi.wad.sesi.client.ui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import ro.infoiasi.wad.sesi.resources.SesiResources;

public class Tab extends VerticalPanel {

    protected final VerticalPanel leftPanel = new VerticalPanel();
    protected final VerticalPanel rightPanel = new VerticalPanel();
    protected final HorizontalPanel menuPanel = new HorizontalPanel();

    private final HorizontalPanel mainPanel = new HorizontalPanel();

    public Tab() {
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        mainPanel.setCellWidth(leftPanel, "75%");
        mainPanel.setCellWidth(rightPanel, "25%");

        setCellHeight(menuPanel, "5 em");
        menuPanel.setStyleName(SesiResources.INSTANCE.style().menuPanel());
        menuPanel.setSpacing(5);
        add(menuPanel);

        add(mainPanel);
    }
}
