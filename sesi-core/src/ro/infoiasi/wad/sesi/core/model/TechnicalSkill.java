package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

public class TechnicalSkill  {

    private ProgrammingLanguage programmingLanguage;

    private Technology technology;

    private KnowledgeLevel level;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("programmingLanguage", programmingLanguage)
                .add("technology", technology)
                .add("level", level)
                .toString();
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
