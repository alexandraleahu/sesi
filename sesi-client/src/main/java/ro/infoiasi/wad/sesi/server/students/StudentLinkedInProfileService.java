package ro.infoiasi.wad.sesi.server.students;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.SearchParameter;
import com.google.common.collect.Maps;
import ro.infoiasi.wad.sesi.client.authentication.SigninRecord;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.server.util.APIKeys;

import java.util.HashMap;

//todo could move this somewhere else
public class StudentLinkedInProfileService {
    public static Student getStudentProfile(String studentID, SigninRecord signinRecord) {
        Student student = new Student();


        final LinkedInApiClientFactory factory = LinkedInApiClientFactory.newInstance(APIKeys.LINKEDIN_API, APIKeys.LINKEDIN_SECRET);
        final LinkedInApiClient client = factory.createLinkedInApiClient(signinRecord.token.getToken(), signinRecord.token.getSecret());

        HashMap<SearchParameter,String> map = Maps.newHashMap();
        map.put(SearchParameter.FIRST_NAME, "Alexandra");
        client.getProfileById(studentID);


        return student;
    }
}
