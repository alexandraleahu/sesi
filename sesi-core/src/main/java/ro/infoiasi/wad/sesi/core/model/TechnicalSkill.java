package ro.infoiasi.wad.sesi.core.model;

import java.io.Serializable;

public class TechnicalSkill implements Serializable {

    private ProgrammingLanguage programmingLanguage;

    private Technology technology;

    private KnowledgeLevel level;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TechnicalSkill{");
        sb.append("programmingLanguage=").append(programmingLanguage);
        sb.append(", technology=").append(technology);
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public KnowledgeLevel getLevel() {
        return level;
    }

    public void setLevel(KnowledgeLevel level) {
        this.level = level;
    }
}
