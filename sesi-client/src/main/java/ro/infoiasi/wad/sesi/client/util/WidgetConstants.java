package ro.infoiasi.wad.sesi.client.util;

import com.google.common.base.Function;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.core.util.Constants;

import javax.annotation.Nullable;
import java.io.Serializable;

public class WidgetConstants implements Serializable {

    private WidgetConstants() {}

    public static final String multipleSkillSeparator = "\n";
    public static final String dataSeparator = ":";

    public static class TechnicalSkillFunction implements Function<String, TechnicalSkill> {

        @Nullable
        @Override
        public TechnicalSkill apply(String input) {
            String[] rawSkills = input.split(dataSeparator);
            TechnicalSkill technicalSkill = new TechnicalSkill();

            String progrType = Constants.PROGRAMMING_LANG_TITLE;

            if (rawSkills[1].equalsIgnoreCase(progrType)) {
                ProgrammingLanguage lang = new ProgrammingLanguage();
                OntologyExtraInfo.fillWithOntologyExtraInfo(lang, rawSkills[0], rawSkills[2]);
                technicalSkill.setProgrammingLanguage(lang);
            } else {
                Technology tech = new Technology();
                OntologyExtraInfo.fillWithOntologyExtraInfo(tech, rawSkills[0], rawSkills[2]);
                technicalSkill.setTechnology(tech);
            }
            KnowledgeLevel level = KnowledgeLevel.valueOf(rawSkills[3]);
            technicalSkill.setLevel(level);
            return technicalSkill;
        }
    }


}
