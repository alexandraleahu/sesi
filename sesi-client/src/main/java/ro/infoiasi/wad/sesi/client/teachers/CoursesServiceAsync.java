package ro.infoiasi.wad.sesi.client.teachers;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ro.infoiasi.wad.sesi.core.model.Course;

import java.util.List;

public interface CoursesServiceAsync {

    void getCoursesFromInfoiasiSite(List<String> coursesUrls, AsyncCallback<List<Course>> async);
}
