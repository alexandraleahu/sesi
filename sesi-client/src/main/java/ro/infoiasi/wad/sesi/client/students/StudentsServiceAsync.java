package ro.infoiasi.wad.sesi.client.students;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;

import java.util.List;

public interface StudentsServiceAsync {
    void getStudentById(String studentId, AsyncCallback<Student> async);

    void getAllStudents(AsyncCallback<List<Student>> async);

    void getStudentApplications(String studentId, AsyncCallback<List<InternshipApplication>> async);

    void getStudentInternshipProgressDetails(String studentId, AsyncCallback<List<InternshipProgressDetails>> async);

    void registerStudent(String username, String password, String name, AsyncCallback<Boolean> async);

    void updateStudent(Student student, AsyncCallback<Boolean> async);

    void getRecommendedInternships(String studentId, AsyncCallback<List<Internship>> async);
}
