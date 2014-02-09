package ro.infoiasi.wad.sesi.client.commonwidgets.widgetinterfaces;

import ro.infoiasi.wad.sesi.core.model.Resource;

public interface ResourceMainView<T extends Resource> {

    void switchViewMode();

    void switchEditMode();

    T getResource();
}
