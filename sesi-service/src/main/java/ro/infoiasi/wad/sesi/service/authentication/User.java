package ro.infoiasi.wad.sesi.service.authentication;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "user")
public class User implements Serializable
{
    private static final long serialVersionUID = -2442435525358001960L;

    @XmlAttribute(name = "id")
    private String id;
     
    @XmlAttribute(name="uri")
    private String uri;
         
    @XmlElement(name = "firstName")
    private String firstName;
     
    @XmlElement(name = "lastName")
    private String lastName;
     
    @XmlElement(name="last-modified")
    private Date lastModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    
}
