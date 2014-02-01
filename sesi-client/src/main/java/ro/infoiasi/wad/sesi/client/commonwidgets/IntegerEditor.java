package ro.infoiasi.wad.sesi.client.commonwidgets;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.base.HasPlaceholder;
import com.github.gwtbootstrap.client.ui.base.HasSize;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;


public class IntegerEditor extends Composite implements LeafValueEditor<Integer>, HasSize, HasPlaceholder {
    @Override
    public void setValue(Integer value) {
        numberBox.setText(value.toString());

    }

    @Override
    public Integer getValue() {
        return Integer.parseInt(numberBox.getText());
    }

    @Override
    public void setSize(int size) {
        numberBox.setSize(size);
    }

    @Override
    public void setPlaceholder(String placeholder) {
        numberBox.setPlaceholder(placeholder);
    }

    @Override
    public String getPlaceholder() {
        return numberBox.getPlaceholder();
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