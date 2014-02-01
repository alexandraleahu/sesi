package ro.infoiasi.wad.sesi.client.technicalskills;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import ro.infoiasi.wad.sesi.client.ontologyextrainfo.OntologyExtraInfoView;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;
import ro.infoiasi.wad.sesi.core.model.TechnicalSkill;

import java.util.List;


public class TechnicalSkillView extends Composite implements LeafValueEditor<List<TechnicalSkill>> {

    private List<TechnicalSkill> value;
    @Override
    public void setValue(List<TechnicalSkill> value) {
        this.value = value;
        skillsTable.setRowData(this.value);
    }

    @Override
    public List<TechnicalSkill> getValue() {

        return value;
    }




    interface TechnicalSkillViewUiBinder extends UiBinder<CellTable, TechnicalSkillView> {
    }

    private static TechnicalSkillViewUiBinder ourUiBinder = GWT.create(TechnicalSkillViewUiBinder.class);

    @UiField (provided = true)
    CellTable<TechnicalSkill> skillsTable = new CellTable<TechnicalSkill>(5);

    public TechnicalSkillView() {
        CellTable root = ourUiBinder.createAndBindUi(this);
        initWidget(root);
        TextColumn<TechnicalSkill> levelCol = new TextColumn<TechnicalSkill>() {
            @Override
            public String getValue(TechnicalSkill object) {
                return object.getLevel().toString();
            }
        };

        TechnologyColumn techCol = new TechnologyColumn(new OntologyExtraInfoViewCell());

        skillsTable.addColumn(techCol);
        skillsTable.addColumn(levelCol);



    }

    private static class TechnologyColumn extends Column<TechnicalSkill, OntologyExtraInfo> {

        /**
         * Construct a new Column with a given {@link com.google.gwt.cell.client.Cell}.
         *
         * @param cell the Cell used by this Column
         */
        public TechnologyColumn(Cell<OntologyExtraInfo> cell) {
            super(cell);

        }

        @Override
        public boolean isSortable() {
            return true;
        }

        @Override
        public OntologyExtraInfo getValue(TechnicalSkill object) {
            if (object.getProgrammingLanguage() != null)  {

                return object.getProgrammingLanguage();

            } else {
                return object.getTechnology();
            }
        }
    }

    private static class OntologyExtraInfoViewCell extends AbstractCell<OntologyExtraInfo> {

        @Override
        public void render(Context context, OntologyExtraInfo value, SafeHtmlBuilder sb) {

            OntologyExtraInfoView view = new OntologyExtraInfoView();
            view.setValue(value);
            sb.appendHtmlConstant(view.getElement().getString());

        }
    }


}