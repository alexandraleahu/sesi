package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 14:59
 */
@XmlRootElement
public class Student {
    private String name;
    private School school;
    private List<Internship> acceptedTo;
    private List<Internship> signedUpFor;
    private StudentProfile studentProfile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Internship> getAcceptedTo() {
        return acceptedTo;
    }

    public void setAcceptedTo(List<Internship> acceptedTo) {
        this.acceptedTo = acceptedTo;
    }

    public List<Internship> getSignedUpFor() {
        return signedUpFor;
    }

    public void setSignedUpFor(List<Internship> signedUpFor) {
        this.signedUpFor = signedUpFor;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }
}
