package ro.infoiasi.wad.sesi.client.widgetinterfaces;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;

public interface WidgetViewer<T>  extends Editor<T>, IsWidget {

    void edit(T bean);
}
