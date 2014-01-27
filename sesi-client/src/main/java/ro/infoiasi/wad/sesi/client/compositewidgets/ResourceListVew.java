package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.common.base.Joiner;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;
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

    interface ResourceListVewUiBinder extends UiBinder<CellTable, ResourceListVew> {
    }

    private static ResourceListVewUiBinder ourUiBinder = GWT.create(ResourceListVewUiBinder.class);
    @UiField(provided = true)
    CellTable<T> resourcesTable = new CellTable<T>(5);

    public ResourceListVew() {
        initWidget(ourUiBinder.createAndBindUi(this));

        ResourceColumn<T> column = new ResourceColumn<T>(new ResourceViewCell<T>());

        resourcesTable.addColumn(column);

    }

    private static class ResourceColumn<T extends Resource> extends Column<T, T> {


        /**
         * Construct a new Column with a given {@link com.google.gwt.cell.client.Cell}.
         *
         * @param cell the Cell used by this Column
         */
        public ResourceColumn(Cell<T> cell) {
            super(cell);
        }

        @Override
        public T getValue(T object) {
            return object;
        }

        @Override
        public boolean isSortable() {
            return true;
        }
    }

    private static class ResourceViewCell<T extends Resource> extends AbstractCell<T> {


        @Override
        public void render(Context context, T value, SafeHtmlBuilder sb) {
            Hyperlink hyperlink = new Hyperlink();
            hyperlink.setText(value.getName());

            hyperlink.setTargetHistoryToken(Joiner.on(WidgetConstants.dataSeparator)
                                                  .join(WidgetConstants.VIEW_TOKEN, value.getId(), value.getClass().getName()));

            sb.appendHtmlConstant(hyperlink.getElement().getString());
        }
    }


}