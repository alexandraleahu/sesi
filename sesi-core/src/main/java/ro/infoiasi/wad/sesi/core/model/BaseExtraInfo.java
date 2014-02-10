package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasExtraInfo;
import ro.infoiasi.wad.sesi.core.util.HasName;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class BaseExtraInfo implements Serializable, HasExtraInfo, HasName {

    private String name;

    private String infoUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setInfoUrl(String url) {
        this.infoUrl = url;
    }

    @Override
    public String getInfoUrl() {
        return infoUrl;
    }
}
