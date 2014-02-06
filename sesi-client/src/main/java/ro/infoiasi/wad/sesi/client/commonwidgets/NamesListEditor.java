package ro.infoiasi.wad.sesi.client.commonwidgets;

import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.base.HasId;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;

import java.util.List;


public class NamesListEditor extends Composite implements LeafValueEditor<List<String>>, HasId {
    @Override
    public void setValue(List<String> names) {
        namesList.clear();
        if (names != null) {
            for (String name : names) {

                namesList.addItem(name);
            }
        }

    }

    @Override
    public List<String> getValue() {
        List<String> names = Lists.newArrayList();
        for (int i = 0; i < namesList.getItemCount(); i++) {

            if (namesList.isItemSelected(i)) {

                names.add(namesList.getValue(i));
            }
        }
        return names;
    }

    @Override
    public String getId() {
        return namesList.getId();
    }

    @Override
    public void setId(String id) {
        namesList.setId(id);
    }

    interface NamesListEditorUiBinder extends UiBinder<ListBox, NamesListEditor> {
    }

    private static NamesListEditorUiBinder ourUiBinder = GWT.create(NamesListEditorUiBinder.class);
    @UiField (provided = true)
    ListBox namesList = new ListBox(true);

    public NamesListEditor() {
        initWidget(ourUiBinder.createAndBindUi(this));

        namesList.setVisibleItemCount(4);
    }
}