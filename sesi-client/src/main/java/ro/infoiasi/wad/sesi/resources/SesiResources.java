package ro.infoiasi.wad.sesi.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface SesiResources extends ClientBundle {

    SesiResources INSTANCE = GWT.create(SesiResources.class);

    /**
     * An Sesi CSS resource.
     * @return the CSS implementing Style
     */
    @NotStrict
    @Source({"Sesi.css"})
    Style style();

    public interface Style extends CssResource {

        String dialogVPanel();

        @ClassName("gwt-DialogBox")
        String gwtDialogBox();

        String sendButton();

        String serverResponseLabelError();

        String floatLeft();

        String withScroll();

        String bigText();

        String floatRight();

        String backgroundColor();

        String bigLabel();

        @ClassName("gwt-Anchor")
        String gwtAnchor();

        String smallMarginLeft();

        String standardMarginTop();

        String standardTextArea();

        String bigTextArea();

        String withBottomBorder();

        String smallMarginRight();

        @ClassName("gwt-Hyperlink")
        String gwtHyperlink();

        String standardMarginRight();

        String tabTopLeft();

        @ClassName("gwt-TabLayoutPanel")
        String gwtTabLayoutPanel();

        @ClassName("gwt-TabPanel")
        String gwtTabPanel();

        @ClassName("gwt-TabBarRest")
        String gwtTabBarRest();

        @ClassName("gwt-TabBarItem")
        String gwtTabBarItem();

        String tabTopLeftInner();

        @ClassName("gwt-TabBarItem-disabled")
        String gwtTabBarItemDisabled();

        String tabMiddleLeft();

        String tabTopRightInner();

        @ClassName("gwt-TabBarFirst")
        String gwtTabBarFirst();

        String tabTopCenter();

        String tabMiddleLeftInner();

        @ClassName("gwt-DecoratedTabBar")
        String gwtDecoratedTabBar();

        String tabMiddleRight();

        String tabTopRight();

        String tabMiddleRightInner();

        String tabMiddleCenter();

        @ClassName("gwt-TabPanelBottom")
        String gwtTabPanelBottom();

        @ClassName("gwt-TabLayoutPanelContent")
        String gwtTabLayoutPanelContent();

        String menuPanel();

        @ClassName("gwt-TabBarItem-selected")
        String gwtTabBarItemSelected();
    }
}
