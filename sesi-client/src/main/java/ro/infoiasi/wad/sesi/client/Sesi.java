package ro.infoiasi.wad.sesi.client;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.client.authentication.SigninService;
import ro.infoiasi.wad.sesi.client.companies.CompanyMainView;
import ro.infoiasi.wad.sesi.client.main.MainView;
import ro.infoiasi.wad.sesi.client.students.StudentEditor;
import ro.infoiasi.wad.sesi.client.students.StudentMainView;
import ro.infoiasi.wad.sesi.client.teachers.TeacherMainView;
import ro.infoiasi.wad.sesi.client.util.WidgetConstants;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.resources.SesiResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sesi implements EntryPoint {


    /**
     * Inject the CSS resource, the event bus, the login code generator service and load the main view.
     */
    private  HandlerManager eventBus;
    @Override
    public void onModuleLoad() {
        SesiResources.INSTANCE.style().ensureInjected();
        eventBus = new HandlerManager(null);

        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.setStyleName(SesiResources.INSTANCE.style().backgroundColor());
//        rootLayoutPanel.add(new MainView(eventBus));

        CompanyMainView companyMainView = new CompanyMainView();
        rootLayoutPanel.add(companyMainView);
//        freebase();
    }

    public void verifyOAuth(String v) {

        SigninService.App.getInstance().verify(v, new AsyncCallback<User>() {

            @Override
            public void onFailure(Throwable caught) {
//                home.error("Sign in verification failed: "+caught.getMessage());
            }

            @Override
            public void onSuccess(User result) {
                System.out.println("Result " + result);
            }
        });


    }


    // !!! This has to be called after the elements used inside it are attached to the document !!!
    public static native void freebase() /*-{

        function fbSuggest(suggestBoxId, resultBoxId, topic, append, levelBoxId, includeType) {
            $wnd.$("#" + suggestBoxId)
                .suggest({"key": "AIzaSyACLiHBsbLdFR5glh1j_rMtBV40R7Yp_0g",
                    filter: '(any ' + topic + ')'
                })
                .bind("fb-select", function (e, data) {
                    init = $wnd.$("#" + resultBoxId).val();
                    level = "";
                    if (levelBoxId != null && levelBoxId != undefined) {
                        level = ":" + $wnd.$("#" + levelBoxId).find(":selected").text();
                    }

                    var skill = data.name + ":"
                    if (includeType) {
                        var longType = data.notable.name;
                        skill += longType + ":";
                    }
                    skill += data.id + level;

                    if (init.length != 0 && append) {
                        $wnd.$("#" + resultBoxId).val(init + "\n" + skill);
                    } else {
                        $wnd.$("#" + resultBoxId).val(skill);
                    }
                }
            )
        }

        fbSuggest("freebasePreferredTechnicalSkills", "preferredTechnicalSkillId",
            "type:/computer/software type:/computer/programming_language", true, "preferredLevelList", true);
        fbSuggest("freebaseAcquiredTechnicalSkills", "acquiredTechnicalSkillId",
            "type:/computer/software type:/computer/programming_language", true, "acquiredLevelList", true);
        fbSuggest("freebaseCity", "cityId", "type:/location/citytown", false, null, false);


    }-*/;


    public static UserAccountType getCurrentUserType() {

        return UserAccountType.fromDescription(Strings.nullToEmpty(Cookies.getCookie(WidgetConstants.CURRENT_ROLE_COOKIE)));
    }

    public static String getCurrentUserId() {

        return Cookies.getCookie(WidgetConstants.CURRENT_USER_ID_COOKIE);
    }

    public static String getCurrentUsername() {

        return Cookies.getCookie(WidgetConstants.CURRENT_USER_NAME_COOKIE);
    }

    public static void setCurrentUserType(UserAccountType account) {

        Cookies.setCookie(WidgetConstants.CURRENT_ROLE_COOKIE, account.getDescription());
    }

    public static void setCurrentUserId(String id) {

        Cookies.setCookie(WidgetConstants.CURRENT_USER_ID_COOKIE, id);
    }

    public static void setCurrentUsername(String name) {

        Cookies.setCookie(WidgetConstants.CURRENT_USER_NAME_COOKIE, name);
    }

    public static void removeCurrentUserType() {

        Cookies.removeCookie(WidgetConstants.CURRENT_ROLE_COOKIE);
    }

    public static void removeCurrentUserId() {

         Cookies.removeCookie(WidgetConstants.CURRENT_USER_ID_COOKIE);
    }

    public static void removeCurrentUsername() {

        Cookies.removeCookie(WidgetConstants.CURRENT_USER_NAME_COOKIE);
    }


    public static void removeAllCookies() {

        removeCurrentUserId();
        removeCurrentUsername();
        removeCurrentUserType();
    }

    static Student newStudent(String name) {
        Student student = new Student();
        student.setId("ala");
        student.setName(name);
        List gs= new ArrayList();
        gs.add("funny");
        gs.add("outgoing");
        student.setGeneralSkills(Lists.newArrayList("funny", "outgoing"));

        City iasi = new City();
        iasi.setOntologyUri("http://rdf.freebase.com/ns/m.01fhg3");
        iasi.setName("Iasi");
        iasi.setInfoUrl("http://www.freebase.com/m/01fhg3");

        University university = new University();
        university.setSiteUrl("http://uaic.ro/uaic/bin/view/Main/WebHome");
        university.setName("Some Random University");
        university.setInfoUrl("http://www.freebase.com/m/0945q0");
        university.setOntologyUri("http://rdf.freebase.com/ns/m.0945q0");
        university.setCity(iasi);
        Faculty faculty = new Faculty();
        faculty.setName("Informatica");
        faculty.setUniversity(university);

        Studies studies = new Studies();
        studies.setName("OC");
        studies.setYearOfStudy(1);
        studies.setFaculty(faculty);
        studies.setLabel("mystudieslabel");
        Degree degree = new Degree();
        degree.setInfoUrl("http://www.freebase.com/m/016t_3");
        degree.setOntologyUri("http://rdf.freebase.com/ns/m.016t_3");
        degree.setName("degree name");
        studies.setDegree(degree);

        ProgrammingLanguage java = new ProgrammingLanguage();
        java.setName("Java");
        java.setInfoUrl("http://www.freebase.com/m/07sbkfb");
        java.setOntologyUri("http://rdf.freebase.com/ns/m.07sbkfb");

        TechnicalSkill javaIntermediate = new TechnicalSkill();
        javaIntermediate.setLevel(KnowledgeLevel.Intermediate);
        javaIntermediate.setProgrammingLanguage(java);

        Technology android = new Technology();
        android.setName("Android");
        android.setInfoUrl("http://www.freebase.com/m/02wxtgw");
        android.setOntologyUri("http://rdf.freebase.com/ns/m.02wxtgw");

        TechnicalSkill androidAdvanced = new TechnicalSkill();
        androidAdvanced.setLevel(KnowledgeLevel.Advanced);
        androidAdvanced.setTechnology(android);
        List ts = new ArrayList();
        ts.add(androidAdvanced);
        ts.add(javaIntermediate);
        student.setTechnicalSkills(ts);

        student.setStudies(studies);

        StudentProject project = new StudentProject();
        project.setName("SESI PROJECT");
        project.setDescription("internship application for students");
        List projects = new ArrayList();
        project.addProgrammingLanguage(java);
        projects.add(project);

        student.setProjects(projects);

        return student;
    }
}
