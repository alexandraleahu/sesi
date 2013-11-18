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
public class Student implements Actor, Resource {
    private String name;
    private School school;
    private List<Internship> acceptedTo;
    private List<Internship> signedUpFor;
    private StudentProfile studentProfile;
    private int id;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public Role getType() {
        return Role.STUDENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (acceptedTo != null ? !acceptedTo.equals(student.acceptedTo) : student.acceptedTo != null) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (school != null ? !school.equals(student.school) : student.school != null) return false;
        if (signedUpFor != null ? !signedUpFor.equals(student.signedUpFor) : student.signedUpFor != null) return false;
        if (studentProfile != null ? !studentProfile.equals(student.studentProfile) : student.studentProfile != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (acceptedTo != null ? acceptedTo.hashCode() : 0);
        result = 31 * result + (signedUpFor != null ? signedUpFor.hashCode() : 0);
        result = 31 * result + (studentProfile != null ? studentProfile.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", school=" + school +
                ", acceptedTo=" + acceptedTo +
                ", signedUpFor=" + signedUpFor +
                ", studentProfile=" + studentProfile +
                ", id=" + id +
                '}';
    }
}
