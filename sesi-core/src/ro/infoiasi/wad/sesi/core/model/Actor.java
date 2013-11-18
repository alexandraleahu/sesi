package ro.infoiasi.wad.sesi.core.model;

import java.io.Serializable;

public interface Actor extends Serializable {

    Role getType();
    int getId();
}
