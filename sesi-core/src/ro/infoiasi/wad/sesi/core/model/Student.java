package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Alexandra Leahu
 * Date: 17.11.2013
 * Time: 14:59
 */
@XmlRootElement
public class Student implements User, Resource {
    private Map<Internship, InternshipApplication> appliedToInternships;
    private Map<Internship, InternshipProgress> inProgressOrFinishedInternships;

    private StudentProfile studentProfile;
    private int id;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Map<Internship, InternshipApplication> getAppliedToInternships() {
        return appliedToInternships;
    }

    public void setAppliedToInternships(Map<Internship, InternshipApplication> appliedToInternships) {
        this.appliedToInternships = appliedToInternships;
    }

    public Map<Internship, InternshipProgress> getInProgressOrFinishedInternships() {
        return inProgressOrFinishedInternships;
    }

    public void setInProgressOrFinishedInternships(Map<Internship, InternshipProgress> inProgressOrFinishedInternships) {
        this.inProgressOrFinishedInternships = inProgressOrFinishedInternships;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (appliedToInternships != null ? !appliedToInternships.equals(student.appliedToInternships) : student.appliedToInternships != null)
            return false;
        if (inProgressOrFinishedInternships != null ? !inProgressOrFinishedInternships.equals(student.inProgressOrFinishedInternships) : student.inProgressOrFinishedInternships != null)
            return false;
        if (studentProfile != null ? !studentProfile.equals(student.studentProfile) : student.studentProfile != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appliedToInternships != null ? appliedToInternships.hashCode() : 0;
        result = 31 * result + (inProgressOrFinishedInternships != null ? inProgressOrFinishedInternships.hashCode() : 0);
        result = 31 * result + (studentProfile != null ? studentProfile.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public String getDescription() {
        return studentProfile.getSummary();
    }

    @Override
    public String toString() {
        return "Student{" +
                "appliedToInternships=" + appliedToInternships +
                ", inProgressOrFinishedInternships=" + inProgressOrFinishedInternships +
                ", studentProfile=" + studentProfile +
                ", id=" + id +
                '}';
    }

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }
}
