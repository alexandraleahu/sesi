package ro.infoiasi.wad.sesi.client.teachers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Teacher;

import java.util.List;

@RemoteServiceRelativePath("TeachersService")
public interface TeachersService extends RemoteService {
    boolean registerTeacher(String username, String password, String name);

    /**
     * Utility/Convenience class.
     * Use TeachersService.App.getInstance() to access static instance of TeachersServiceAsync
     */
    public static class App {
        private static final TeachersServiceAsync ourInstance = (TeachersServiceAsync) GWT.create(TeachersService.class);

        public static TeachersServiceAsync getInstance() {
            return ourInstance;
        }
    }

    Teacher getTeacherById(String teacherID);

    Teacher importTeacherProfile(String url);

    List<Teacher> getAllTeachers();

    List<InternshipProgressDetails> getProgressDetailsForTeacher(String teacherId);

    boolean updateTeacher(Teacher teacher);

    public static final String RESOURCE_PATH = "teachers";


}
