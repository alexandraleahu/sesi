package ro.infoiasi.wad.sesi.client.internships;

import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.LabelType;
import com.google.common.collect.Maps;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import ro.infoiasi.wad.sesi.client.commonwidgets.ResourceListVew;
import ro.infoiasi.wad.sesi.core.model.Internship;
import sun.plugin.viewer.IExplorerPluginContext;

import java.util.List;
import java.util.Map;


public class InternshipsByCategoryView implements IsWidget {
    @Override
    public Widget asWidget() {
        return root;
    }



    interface InternshipsByCategoryViewUiBinder extends UiBinder<TabPanel, InternshipsByCategoryView> {
    }

    private static InternshipsByCategoryViewUiBinder ourUiBinder = GWT.create(InternshipsByCategoryViewUiBinder.class);
    private static final Map<Internship.Category, IconType> ICONS = buildIcons();

    private static Map<Internship.Category, IconType> buildIcons() {

        Map<Internship.Category, IconType> icons = Maps.newHashMap();
        icons.put(Internship.Category.ArtificialIntelligence, IconType.ADN);
        icons.put(Internship.Category.CloudComputing, IconType.CLOUD);
        icons.put(Internship.Category.Mobile, IconType.ANDROID);
        icons.put(Internship.Category.Embedded, IconType.HDD);
        icons.put(Internship.Category.WebDev, IconType.HTML5);
        icons.put(Internship.Category.Gaming, IconType.GAMEPAD);
        icons.put(Internship.Category.BigData, IconType.DASHBOARD);
        icons.put(Internship.Category.Management, IconType.BUILDING);
        icons.put(Internship.Category.QualityAssurance, IconType.BUG);
        icons.put(Internship.Category.Databases, IconType.ARCHIVE);
        icons.put(Internship.Category.BusinessSoftware, IconType.LAPTOP);

        return icons;
    }

    @UiField
    TabPanel categoriesTabPanel;

    private TabPanel root;
    public InternshipsByCategoryView() {
       root = ourUiBinder.createAndBindUi(this);
       fillWithCategories();
    }



    private void fillWithCategories() {
        InternshipsServiceAsync service = InternshipsService.App.getInstance();
        for (Internship.Category category : Internship.Category.values()) {
            final Tab tab = new Tab();
            tab.setHeading(category.getDescription());
            tab.setIcon(ICONS.get(category));
            categoriesTabPanel.add(tab);
            tab.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    tab.asTabLink().setTargetHistoryToken("");
                }
            });

            service.getAllInternshipsByCategory(category, new AsyncCallback<List<Internship>>() {
                @Override
                public void onFailure(Throwable caught) {

                    tab.add(new Label(LabelType.IMPORTANT, "Could not load internships!"));
                }

                @Override
                public void onSuccess(List<Internship> result) {

                    ResourceListVew<Internship> resourceListVew = new ResourceListVew<Internship>();
                    tab.add(resourceListVew);

                    resourceListVew.setValue(result);
                }
            });
        }
    }


}