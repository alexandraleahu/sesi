package ro.infoiasi.wad.sesi.client.teachers;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Teacher;

import java.util.List;

public interface TeachersServiceAsync {
    void getProgressDetailsForTeacher(String teacherId, AsyncCallback<List<InternshipProgressDetails>> async);

    void getAllTeachers(AsyncCallback<List<Teacher>> async);

    void getTeacherByID(String teacherID, AsyncCallback<Teacher> async);

    void registerTeacher(String username, String password, String name, AsyncCallback<Boolean> async);
}
