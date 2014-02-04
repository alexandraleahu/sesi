package ro.infoiasi.wad.sesi.client.reports;

import com.github.gwtbootstrap.client.ui.ValueListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.client.commonwidgets.IntegerEditor;
import ro.infoiasi.wad.sesi.shared.ComparisonOperator;

import java.io.IOException;
import java.util.Arrays;


public class NumericRestrictionEditor extends Composite implements Editor<NumericRestriction> {

    interface NumericRestrictionEditorUiBinder extends UiBinder<HTMLPanel, NumericRestrictionEditor> {
    }

    private static NumericRestrictionEditorUiBinder ourUiBinder = GWT.create(NumericRestrictionEditorUiBinder.class);
    @UiField
    @Path("limit")
    IntegerEditor numericalValueEditor;

    @UiField(provided=true)
    @Path("op")
    ValueListBox<ComparisonOperator> comparisonOperatorBox = new ValueListBox<ComparisonOperator>(new Renderer<ComparisonOperator>() {
        @Override
        public String render(ComparisonOperator operator) {
            return operator == null ? "" : operator.getDescription();
        }
        @Override
        public void render(ComparisonOperator operator, Appendable appendable) throws IOException {
            if (operator != null)
                appendable.append(operator.getDescription());
        }
    });

    public NumericRestrictionEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));

        comparisonOperatorBox.setValue(ComparisonOperator.eq);
        comparisonOperatorBox.setAcceptableValues(Arrays.asList(ComparisonOperator.values()));
    }
}