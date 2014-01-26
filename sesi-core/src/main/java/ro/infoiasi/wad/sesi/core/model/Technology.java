package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasDescription;
import ro.infoiasi.wad.sesi.core.util.HasName;

import java.util.List;

public class Technology extends OntologyExtraInfo implements HasDescription {

    private String description;
    private String repository;
    private List<String> programmingLanguages;

    private List<Technology> technologies;

    private String version;

    private HasName developedBy;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
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

    public HasName getDevelopedBy() {
        return developedBy;
    }

    public void setDevelopedBy(Person developedBy) {
        this.developedBy = developedBy;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Technology{");
        sb.append("description='").append(description).append('\'');
        sb.append(", repository='").append(repository).append('\'');
        sb.append(", programmingLanguages=").append(programmingLanguages);
        sb.append(", technologies=").append(technologies);
        sb.append(", version='").append(version).append('\'');
        sb.append(", developedBy=").append(developedBy);
        sb.append(", extraInfoUrl='").append(getInfoUrl()).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", ontologyUri='").append(getOntologyUri()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
