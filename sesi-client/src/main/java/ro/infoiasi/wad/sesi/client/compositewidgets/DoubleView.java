package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;


public class DoubleView extends Composite implements LeafValueEditor<Double> {
    @Override
    public void setValue(Double value) {
        numberLabel.setText(value.toString());
    }

    @Override
    public Double getValue() {

        return Double.parseDouble(numberLabel.getText());
    }

    interface DoubleViewUiBinder extends UiBinder<Label, DoubleView> {
    }

    private static DoubleViewUiBinder ourUiBinder = GWT.create(DoubleViewUiBinder.class);
    @UiField
    Label numberLabel;

    public DoubleView() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }
}