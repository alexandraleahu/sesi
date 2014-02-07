package ro.infoiasi.wad.sesi.shared;

import ro.infoiasi.wad.sesi.core.model.Resource;

public class BasicResource implements Resource {

    private String id;
    private String relativeUri;
    private String description;
    private String name;


    public BasicResource() {}

    public BasicResource(String id, String relativeUri, String description, String name) {
        this.id = id;
        this.relativeUri = relativeUri;
        this.description = description;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelativeUri() {
        return relativeUri;
    }

    public void setRelativeUri(String relativeUri) {
        this.relativeUri = relativeUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BasicResource{");
        sb.append("id='").append(id).append('\'');
        sb.append(", relativeUri='").append(relativeUri).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
