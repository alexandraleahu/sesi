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
    private List<Internship> acceptedTo;
    private List<Internship> pending;
    private StudentProfile studentProfile;
    private int id;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<Internship> getAcceptedTo() {
        return acceptedTo;
    }

    public void setAcceptedTo(List<Internship> acceptedTo) {
        this.acceptedTo = acceptedTo;
    }

    public List<Internship> getPending() {
        return pending;
    }

    public void setPending(List<Internship> pending) {
        this.pending = pending;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (acceptedTo != null ? !acceptedTo.equals(student.acceptedTo) : student.acceptedTo != null) return false;
        if (pending != null ? !pending.equals(student.pending) : student.pending != null) return false;
        if (studentProfile != null ? !studentProfile.equals(student.studentProfile) : student.studentProfile != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = acceptedTo != null ? acceptedTo.hashCode() : 0;
        result = 31 * result + (pending != null ? pending.hashCode() : 0);
        result = 31 * result + (studentProfile != null ? studentProfile.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "acceptedTo=" + acceptedTo +
                ", pending=" + pending +
                ", studentProfile=" + studentProfile +
                ", id=" + id +
                '}';
    }
}
