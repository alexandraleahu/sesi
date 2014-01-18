package ro.infoiasi.wad.sesi.core.model;

import org.joda.time.DateTime;
import ro.infoiasi.wad.sesi.core.util.HasDescription;
import ro.infoiasi.wad.sesi.core.util.HasName;

import java.util.Date;

public interface Event extends HasDescription, HasName {

    Date getStartDate();
    Date getEndDate();
    City getCity();
}
