package ro.infoiasi.wad.sesi.client.ontologyextrainfo;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;

public abstract class OntologyExtraInfoColumn<T> extends Column<T, OntologyExtraInfo> {

    /**
     * Construct a new Column with a given {@link com.google.gwt.cell.client.Cell}.
     *
     * @param cell the Cell used by this Column
     */
    public OntologyExtraInfoColumn(Cell<OntologyExtraInfo> cell) {
        super(cell);

    }

    @Override
    public boolean isSortable() {
        return true;
    }


}
