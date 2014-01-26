package ro.infoiasi.wad.sesi.client.compositewidgets;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.base.HasPlaceholder;
import com.github.gwtbootstrap.client.ui.base.HasSize;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;

public class DoubleEditor extends Composite implements LeafValueEditor<Double>, HasSize, HasPlaceholder {
    @Override
    public void setValue(Double value) {
       numberBox.setText(value.toString());
    }

    @Override
    public Double getValue() {
        return Double.parseDouble(numberBox.getText());
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

    interface DoubleEditorUiBinder extends UiBinder<TextBox, DoubleEditor> {
    }

    private static DoubleEditorUiBinder ourUiBinder = GWT.create(DoubleEditorUiBinder.class);
    @UiField
    TextBox numberBox;

    public DoubleEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }
}