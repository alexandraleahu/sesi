package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.github.gwtbootstrap.client.ui.constants.LabelType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsService;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsServiceAsync;
import ro.infoiasi.wad.sesi.core.model.Internship;

import java.util.List;


public class InternshipsByCategoryView implements IsWidget {
    @Override
    public Widget asWidget() {
        return root;
    }

    interface InternshipsByCategoryViewUiBinder extends UiBinder<TabPanel, InternshipsByCategoryView> {
    }

    private static InternshipsByCategoryViewUiBinder ourUiBinder = GWT.create(InternshipsByCategoryViewUiBinder.class);
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
            categoriesTabPanel.add(tab);

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