package ro.infoiasi.wad.sesi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import ro.infoiasi.wad.sesi.client.authentication.SigninService;
import ro.infoiasi.wad.sesi.client.main.MainView;
import ro.infoiasi.wad.sesi.core.model.StudentLinkedinProfile;
import ro.infoiasi.wad.sesi.core.model.User;
import ro.infoiasi.wad.sesi.resources.SesiResources;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sesi implements EntryPoint {


    /**
     * Inject the CSS resource, the event bus, the login code generator service and load the main view.
     */
    @Override
    public void onModuleLoad() {
        SesiResources.INSTANCE.style().ensureInjected();
//        InternshipsServiceAsync instance = InternshipsService.App.getInstance();
//
//        String v = Window.Location.getParameter("oauth_verifier");
//        if (v != null) {
//            verifyOAuth(v);
//            return;
//        }
//
        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.setStyleName(SesiResources.INSTANCE.style().backgroundColor());
        rootLayoutPanel.add(new MainView());


//        instance.getInternshipById("003", new AsyncCallback<Internship>() {
//            @Override
//            public void onFailure(Throwable caught) {
//            }
//
//            @Override
//            public void onSuccess(Internship internship) {
//                final InternshipView internshipView = new InternshipView();
//                internshipView.edit(internship);
//                RootLayoutPanel.get().add(internshipView);
//            }
//        });

//        InternshipEditor widget = new InternshipEditor();
//        RootLayoutPanel.get().add(widget);
//        widget.edit(new Internship());

//        freebase();
    }

    public void verifyOAuth(String v) {
        final MainView home = new MainView();
        RootLayoutPanel.get().clear();
        RootLayoutPanel.get().add(home);

        SigninService.Util.getInstance().verify(v, new AsyncCallback<User>() {

            @Override
            public void onFailure(Throwable caught) {
                System.out.println(caught.toString());
//                home.error("Sign in verification failed: "+caught.getMessage());
            }

            @Override
            public void onSuccess(User result) {
                System.out.println("user: " + result);
            }
        });

        SigninService.Util.getInstance().getProfile(v, new AsyncCallback<StudentLinkedinProfile>() {

            @Override
            public void onFailure(Throwable caught) {
                System.out.println(caught.toString());
//                home.error("Sign in verification failed: "+caught.getMessage());
            }

            @Override
            public void onSuccess(StudentLinkedinProfile result) {
                System.out.println("user: " + result);
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


    public static String getCurrentUserType() {

        return Cookies.getCookie("currentUserRole");
    }
}
