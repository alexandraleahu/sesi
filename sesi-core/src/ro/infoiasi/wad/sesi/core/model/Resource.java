package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasDescription;

import java.io.Serializable;

public interface Resource extends Serializable, HasDescription {
    String getId();
}
