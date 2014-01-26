package ro.infoiasi.wad.sesi.core.model;

public class StudentProject extends Technology {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StudentProject{");
        sb.append("description='").append(getDescription()).append('\'');
        sb.append(", repository='").append(getRepository()).append('\'');
        sb.append(", programmingLanguages=").append(getProgrammingLanguages());
        sb.append(", technologies=").append(getTechnologies());
        sb.append(", version='").append(getVersion()).append('\'');
        sb.append(", developedBy=").append(getDevelopedBy());
        sb.append(", extraInfoUrl='").append(getInfoUrl()).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", ontologyUri='").append(getOntologyUri()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
