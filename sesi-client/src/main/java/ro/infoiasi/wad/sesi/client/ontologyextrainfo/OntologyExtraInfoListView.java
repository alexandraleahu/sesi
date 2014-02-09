package ro.infoiasi.wad.sesi.client.ontologyextrainfo;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;

import java.util.List;


public class OntologyExtraInfoListView<T extends OntologyExtraInfo> extends Composite implements LeafValueEditor<List<T>> {

    private List<T> value;
    @Override
    public void setValue(List<T> value) {
        this.value = value;
        resTable.setRowData(this.value);
    }

    @Override
    public List<T> getValue() {

        return value;
    }




    interface OntologyExtraInfoListViewUiBinder extends UiBinder<HTMLPanel, OntologyExtraInfoListView> {
    }

    private static OntologyExtraInfoListViewUiBinder ourUiBinder = GWT.create(OntologyExtraInfoListViewUiBinder.class);

    @UiField (provided = true)
    CellTable<T> resTable = new CellTable<T>(5);

    public OntologyExtraInfoListView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        OntologyExtraInfoColumn<T> techCol = new OntologyExtraInfoColumn<T>(new OntologyExtraInfoViewCell()) {
            @Override
            public OntologyExtraInfo getValue(T object) {
                return object;
            }
        };

        resTable.addColumn(techCol);



    }


}