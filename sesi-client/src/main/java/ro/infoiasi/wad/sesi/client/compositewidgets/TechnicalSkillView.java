package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.github.gwtbootstrap.client.ui.DataGrid;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;


public class TechnicalSkillView {
    interface TechnicalSkillViewUiBinder extends UiBinder<DataGrid, TechnicalSkillView> {
    }

    private static TechnicalSkillViewUiBinder ourUiBinder = GWT.create(TechnicalSkillViewUiBinder.class);
    @UiField
    DataGrid skillsTable;

    public TechnicalSkillView() {
        ourUiBinder.createAndBindUi(this);

    }
}