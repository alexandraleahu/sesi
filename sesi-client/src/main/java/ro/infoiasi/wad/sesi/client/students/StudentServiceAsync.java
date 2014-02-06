package ro.infoiasi.wad.sesi.client.students;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;

import java.util.List;

public interface StudentServiceAsync {
    void getStudentById(String studentId, AsyncCallback<Student> async);

    void getAllStudents(AsyncCallback<List<Student>> async);

    void getStudentApplications(String studentId, AsyncCallback<List<InternshipApplication>> async);

    void getStudentInternshipProgressDetails(String studentId, AsyncCallback<List<InternshipProgressDetails>> async);

    void updateStudent(Student student, AsyncCallback<String> async);

    void registerStudent(String username, String password, AsyncCallback<Boolean> async);
}
