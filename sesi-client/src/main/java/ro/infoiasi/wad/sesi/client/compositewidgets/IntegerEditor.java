package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;


public class IntegerEditor extends Composite implements LeafValueEditor<Integer> {
    @Override
    public void setValue(Integer value) {
        numberBox.setText(value.toString());

    }

    @Override
    public Integer getValue() {
        return Integer.parseInt(numberBox.getText());
    }

    interface IntegerEditorUiBinder extends UiBinder<TextBox, IntegerEditor> {
    }

    private static IntegerEditorUiBinder ourUiBinder = GWT.create(IntegerEditorUiBinder.class);
    @UiField
    TextBox numberBox;

    public IntegerEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }
}