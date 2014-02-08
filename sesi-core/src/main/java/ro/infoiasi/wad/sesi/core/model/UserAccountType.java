package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasDescription;

import java.util.HashMap;
import java.util.Map;

public enum UserAccountType implements HasDescription {
    STUDENT_ACCOUNT("student"),
    TEACHER_ACCOUNT("teacher"),
    COMPANY_ACCOUNT("company");

    private static final Map<String, UserAccountType> FROM_DESCRIPTION = buildMappings();

    private static Map<String, UserAccountType> buildMappings() {

        Map<String, UserAccountType> mapping = new HashMap<String, UserAccountType>();
        for (UserAccountType account : values()) {
            mapping.put(account.getDescription(), account);
        }

        return mapping;
    }
    public static UserAccountType fromDescription(String description) {
        return FROM_DESCRIPTION.get(description);
    }

    private final String description;


    UserAccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
