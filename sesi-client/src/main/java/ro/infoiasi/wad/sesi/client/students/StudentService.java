package ro.infoiasi.wad.sesi.client.students;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;

import java.util.List;

@RemoteServiceRelativePath("StudentService")
public interface StudentService extends RemoteService {
    boolean registerStudent(String username, String password);

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

    List<Student> getAllStudents();

    List<InternshipApplication> getStudentApplications(String studentId);

    List<InternshipProgressDetails> getStudentInternshipProgressDetails(String studentId);

    String updateStudent(Student student);
}
