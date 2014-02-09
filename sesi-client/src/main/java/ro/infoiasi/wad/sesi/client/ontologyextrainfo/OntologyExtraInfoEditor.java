package ro.infoiasi.wad.sesi.client.ontologyextrainfo;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.HelpInline;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;


public class OntologyExtraInfoEditor<T extends OntologyExtraInfo> extends Composite implements LeafValueEditor<T> {

    private T value;

    @Override
    public void setValue(T value) {
        if (value != null) {

            this.value = value;

            urlBox.setText(value.getInfoUrl());
            nameBox.setText(value.getName());
        }
    }

    @Override
    public T getValue() {
        if (value != null) {
            value.setName(nameBox.getText());
            value.setInfoUrl(urlBox.getText());
        }
        return value;
    }

    interface OntologyExtraInfoEditorUiBinder extends UiBinder<HTMLPanel, OntologyExtraInfoEditor> {

    }
    private static OntologyExtraInfoEditorUiBinder ourUiBinder = GWT.create(OntologyExtraInfoEditorUiBinder.class);

    @UiField
    TextBox urlBox;


    @UiField
    TextBox nameBox;
    @UiField
    ControlGroup urlControlGroup;
    @UiField
    ControlGroup nameControlGroup;
    @UiField
    HelpInline nameHelpInline;
    @UiField
    HelpInline urlHelpInline;
    @UiField
    Button removeBtn;
    public OntologyExtraInfoEditor() {

        this(false);

    }

    public OntologyExtraInfoEditor(boolean withRemoveBtn) {
        initWidget(ourUiBinder.createAndBindUi(this));
        removeBtn.setVisible(withRemoveBtn);
    }

    @UiHandler("removeBtn")
    public void removeEditor(ClickEvent e) {
        this.removeFromParent();
    }
}