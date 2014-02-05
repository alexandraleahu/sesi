package ro.infoiasi.wad.sesi.client.teachers;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Teacher;

import java.util.List;

public interface TeachersServiceAsync {
    void getProgressDetailsForTeacher(String teacherId, AsyncCallback<List<InternshipProgressDetails>> async);

    void getAllTeachers(AsyncCallback<List<Teacher>> async);

    void getTeacherByID(String teacherID, AsyncCallback<Teacher> async);

    void registerStudent(String username, String password, AsyncCallback<Boolean> async);
}
