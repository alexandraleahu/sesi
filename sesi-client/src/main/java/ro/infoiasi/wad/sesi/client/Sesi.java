package ro.infoiasi.wad.sesi.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import ro.infoiasi.wad.sesi.client.compositewidgets.InternshipEditor;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsService;
import ro.infoiasi.wad.sesi.client.rpc.InternshipsServiceAsync;
import ro.infoiasi.wad.sesi.resources.SesiResources;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sesi implements EntryPoint {


    /**
     * Inject the CSS resource, the event bus, the rpc code generator service and load the main view.
     */
    @Override
    public void onModuleLoad() {
        SesiResources.INSTANCE.style().ensureInjected();
        InternshipsServiceAsync instance = InternshipsService.App.getInstance();
        RootLayoutPanel.get().setStyleName(SesiResources.INSTANCE.style().backgroundColor());

//        instance.getInternshipById("003", new AsyncCallback<InternsskillFailure(Throwable caught) {
//
//            }
//
//            @Override
//            public void onSuccess(Internship internship) {
//                final InternshipView internshipView = new InternshipView();
//                internshipView.edit(internship);
//                RootLayoutPanel.get().add(internshipView);
//            }
//        });

        RootLayoutPanel.get().add(new InternshipEditor());
        freebase();
    }


    // !!! This has to be called after the elements used inside it are attached to the document !!!
    public static native void freebase() /*-{

        function fbSuggest(suggestBoxId, resultBoxId, topic, append, levelBoxId) {
            $wnd.$("#" + suggestBoxId)
                .suggest({"key": "AIzaSyACLiHBsbLdFR5glh1j_rMtBV40R7Yp_0g",
                           filter:'(all type:' + topic + ')'})
                .bind("fb-select", function(e, data) {
                    init = $wnd.$("#" + resultBoxId).val();
                    level = "";
                    if (levelBoxId != null && levelBoxId != undefined) {
                        level = ":" + $wnd.$("#" + levelBoxId).find(":selected").text();
                    }

                    var skill = data.name + ":" + data.id + level;

                    if (init.length != 0 && append) {
                        $wnd.$("#" + resultBoxId).val(init + "\n"+ skill);
                    } else {
                        $wnd.$("#" + resultBoxId).val(skill);
                    }
                }
            )
        }

        fbSuggest("freebasePreferredTechnicalSkills", "preferredTechnicalSkillId", "/computer/software", true, "preferredLevelList");
        fbSuggest("freebaseAcquiredTechnicalSkills", "acquiredTechnicalSkillId", "/computer/software", true, "acquiredLevelList");
        fbSuggest("freebaseCity", "cityId", "/location/citytown", false);


    }-*/;


}
