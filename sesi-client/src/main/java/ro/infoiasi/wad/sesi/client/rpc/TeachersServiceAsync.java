package ro.infoiasi.wad.sesi.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Teacher;

import java.util.List;

public interface TeachersServiceAsync {
    void getProgressDetailsForTeacher(String teacherId, AsyncCallback<List<InternshipProgressDetails>> async);

    void getAllTeachers(AsyncCallback<List<Teacher>> async);

    void getTeacherByID(String teacherID, AsyncCallback<Teacher> async);
}
