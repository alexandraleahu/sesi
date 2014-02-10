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
        if(value != null) {

            numberLabel.setText(value.toString());
        }
    }

    @Override
    public Integer getValue() {

        String text = numberLabel.getText();
        return Integer.parseInt(text.isEmpty() ? "0" : text);
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