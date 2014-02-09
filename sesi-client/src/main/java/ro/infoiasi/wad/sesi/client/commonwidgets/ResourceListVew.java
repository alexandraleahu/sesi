package ro.infoiasi.wad.sesi.client.commonwidgets;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.core.model.Resource;

import java.util.List;


public class ResourceListVew<T extends Resource> extends Composite implements LeafValueEditor<List<T>> {

    private List<T> value;
    @Override
    public void setValue(List<T> value) {
        this.value = value;
        resourcesTable.setRowData(this.value);
    }

    @Override
    public List<T> getValue() {
        return value;
    }

    interface ResourceListVewUiBinder extends UiBinder<HTMLPanel, ResourceListVew> {
    }

    private static ResourceListVewUiBinder ourUiBinder = GWT.create(ResourceListVewUiBinder.class);
    @UiField(provided = true)
    CellTable<T> resourcesTable = new CellTable<T>(5);

    public ResourceListVew() {
        initWidget(ourUiBinder.createAndBindUi(this));

        ResourceColumn<T, T> column = new ResourceColumn<T, T>(new ResourceViewCell<T>()) {
            @Override
            public T getValue(T object) {
                return object;
            }
        };

        resourcesTable.addColumn(column);

    }


}