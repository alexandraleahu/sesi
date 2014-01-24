package ro.infoiasi.wad.sesi.client.widgetinterfaces;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import ro.infoiasi.wad.sesi.core.model.Resource;

public interface ResourceWidgetViewer<T extends Resource> extends Editor<T>, IsWidget {

    void edit(T resource);
}
