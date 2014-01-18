package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasExtraInfo;

import java.io.Serializable;
import java.net.URL;

public class BaseExtraInfo implements Serializable, HasExtraInfo {

    private String name;

    private URL infoUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setInfoUrl(URL url) {
        this.infoUrl = url;
    }

    @Override
    public URL getInfoUrl() {
        return infoUrl;
    }
}
