package ro.infoiasi.wad.sesi.client.widgetinterfaces;

import ro.infoiasi.wad.sesi.core.model.Resource;

public interface ResourceWidgetEditor<T extends Resource> extends ResourceWidgetViewer<T> {

    T save();
}
