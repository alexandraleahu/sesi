package ro.infoiasi.wad.sesi.client.util;

import com.google.common.base.Function;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.core.util.Constants;

import javax.annotation.Nullable;
import java.io.Serializable;

public class WidgetConstants implements Serializable {

    private WidgetConstants() {
    }

    public static final String multipleSkillSeparator = "\n";
    public static final String dataSeparator = ":";
    public static final String VIEW_TOKEN = "view";
    public static final String EDIT_TOKEN = "edit";
    public static final String LOGIN_TOKEN = "login";
    public static final String LOGOUT_TOKEN = "logout";
    public static final String REGISTER_TOKEN = "register";
    public static final String CURRENT_ROLE_COOKIE = "currentUserRole";
    public static final String CURRENT_USER_NAME_COOKIE = "currentUsername";
    public static final String CURRENT_USER_ID_COOKIE = "currentUserId";


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


    public static class ProgrammingLanguageFunction implements Function<String, ProgrammingLanguage> {
        @Nullable
        @Override
        public ProgrammingLanguage apply(String input) {
            String[] raw = input.split(dataSeparator);
            ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
            OntologyExtraInfo.fillWithOntologyExtraInfo(programmingLanguage, raw[0], raw[1]);
            return programmingLanguage;
        }
    }

    public static class TechnologyFunction implements Function<String, Technology> {
        @Nullable
        @Override
        public Technology apply(String input) {
            String[] raw = input.split(dataSeparator);
            Technology technology = new Technology();
            OntologyExtraInfo.fillWithOntologyExtraInfo(technology, raw[0], raw[1]);
            return technology;
        }
    }
}
