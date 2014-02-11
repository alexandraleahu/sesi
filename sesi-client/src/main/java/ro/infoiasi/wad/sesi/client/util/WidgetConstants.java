package ro.infoiasi.wad.sesi.client.util;

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
    public static final String STUDENT_IMPORT_PROFILE_COOKIE = "studentImportLinkedInProfile";


}
