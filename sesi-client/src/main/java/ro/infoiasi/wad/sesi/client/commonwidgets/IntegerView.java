package ro.infoiasi.wad.sesi.client.commonwidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;


public class IntegerView extends Composite implements LeafValueEditor<Integer> {


    @Override
    public void setValue(Integer value) {
        numberLabel.setText(value.toString());
    }

    @Override
    public Integer getValue() {

        return Integer.parseInt(numberLabel.getText());
    }

    interface NumberViewUiBinder extends UiBinder<Label, IntegerView> {
    }

    private static NumberViewUiBinder ourUiBinder = GWT.create(NumberViewUiBinder.class);

    @UiField
    InlineLabel numberLabel;

    public IntegerView() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }
}