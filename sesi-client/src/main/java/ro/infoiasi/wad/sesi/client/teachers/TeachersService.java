package ro.infoiasi.wad.sesi.client.teachers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Teacher;

import java.util.List;

@RemoteServiceRelativePath("TeachersService")
public interface TeachersService extends RemoteService {
    boolean registerStudent(String username, String password);

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

    Teacher getTeacherByID(String teacherID);

    List<Teacher> getAllTeachers();

    List<InternshipProgressDetails> getProgressDetailsForTeacher(String teacherId);
}
