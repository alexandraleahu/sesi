package ro.infoiasi.wad.sesi.client.students;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;

import java.util.List;

@RemoteServiceRelativePath("StudentService")
public interface StudentsService extends RemoteService {
    boolean registerStudent(String username, String password, String name);

    /**
     * Utility/Convenience class.
     * Use StudentsService.App.getInstance() to access static instance of StudentServiceAsync
     */
    public static class App {
        private static final StudentsServiceAsync ourInstance = (StudentsServiceAsync) GWT.create(StudentsService.class);

        public static StudentsServiceAsync getInstance() {
            return ourInstance;
        }
    }

    Student getStudentById(String studentId);

    List<Student> getAllStudents();

    List<InternshipApplication> getStudentApplications(String studentId);

    List<InternshipProgressDetails> getStudentInternshipProgressDetails(String studentId);

    boolean updateStudent(Student student);

    public static final String RESOURCE_PATH = "students";

}
