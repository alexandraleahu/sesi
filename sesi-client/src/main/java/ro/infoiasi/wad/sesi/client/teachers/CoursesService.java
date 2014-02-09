package ro.infoiasi.wad.sesi.client.teachers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.Course;

import java.util.List;


@RemoteServiceRelativePath("CoursesService")
public interface CoursesService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use CoursesService.App.getInstance() to access static instance of CoursesServiceAsync
     */
    public static class App {
        private static final CoursesServiceAsync ourInstance = (CoursesServiceAsync) GWT.create(CoursesService.class);

        public static CoursesServiceAsync getInstance() {
            return ourInstance;
        }
    }

    List<Course> getCoursesFromInfoiasiSite(List<String> coursesUrls);
}
