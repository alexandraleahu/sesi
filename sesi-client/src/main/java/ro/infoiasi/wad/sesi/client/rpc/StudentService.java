package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import ro.infoiasi.wad.sesi.core.model.Student;

@RemoteServiceRelativePath("StudentService")
public interface StudentService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use StudentService.App.getInstance() to access static instance of StudentServiceAsync
     */
    public static class App {
        private static final StudentServiceAsync ourInstance = (StudentServiceAsync) GWT.create(StudentService.class);

        public static StudentServiceAsync getInstance() {
            return ourInstance;
        }
    }

    Student getStudentById(String studentId);
}
