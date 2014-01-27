package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasDescription;
import ro.infoiasi.wad.sesi.core.util.HasName;

import java.io.Serializable;

public interface Resource extends Serializable, HasDescription, HasName {
    String getId();

    String getRelativeUri();
}
