package ro.infoiasi.wad.sesi.core.model;

import java.io.Serializable;

public interface Actor extends Serializable {

    Role getRole();
    int getId();
}
