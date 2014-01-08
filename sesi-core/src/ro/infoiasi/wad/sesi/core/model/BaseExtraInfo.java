package ro.infoiasi.wad.sesi.core.model;

import java.io.Serializable;
import java.net.URL;

public class BaseExtraInfo implements Serializable, HasExtraInfo {

    private String name;

    private URL url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public URL getInfoUrl() {
        return url;
    }
}
