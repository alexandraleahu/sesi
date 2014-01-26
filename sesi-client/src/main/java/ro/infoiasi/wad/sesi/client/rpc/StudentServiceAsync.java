package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.Student;

import java.util.List;

public interface StudentServiceAsync {
    void getStudentById(String studentId, AsyncCallback<Student> async);

    void getAllStudents(AsyncCallback<List<Student>> async);
}
