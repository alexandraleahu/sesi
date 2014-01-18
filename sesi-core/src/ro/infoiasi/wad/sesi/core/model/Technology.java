package ro.infoiasi.wad.sesi.core.model;

import java.net.URL;
import java.util.List;

public class Technology extends OntologyExtraInfo {

    private String description;
    private URL repository;
    private List<String> programmingLanguages;

    private List<Technology> technologies;

    private String version;

    private Person developedBy;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getRepository() {
        return repository;
    }

    public void setRepository(URL repository) {
        this.repository = repository;
    }

    public List<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(List<String> programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Person getDevelopedBy() {
        return developedBy;
    }

    public void setDevelopedBy(Person developedBy) {
        this.developedBy = developedBy;
    }
}
