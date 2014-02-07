package ro.infoiasi.wad.sesi.client.commonwidgets;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;
import ro.infoiasi.wad.sesi.core.model.Resource;

public abstract class ResourceColumn<T, C extends Resource> extends Column<T, C> {


    /**
     * Construct a new Column with a given {@link com.google.gwt.cell.client.Cell}.
     *
     * @param cell the Cell used by this Column
     */
    public ResourceColumn(Cell<C> cell) {
        super(cell);
    }


    @Override
    public boolean isSortable() {
        return true;
    }

}
