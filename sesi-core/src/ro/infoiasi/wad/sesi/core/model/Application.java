package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Application {

    private int id;
    private int internshipId;
    private int studentId;
    private boolean accepted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", internshipId=" + internshipId +
                ", studentId=" + studentId +
                ", accepted=" + accepted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (accepted != that.accepted) return false;
        if (id != that.id) return false;
        if (internshipId != that.internshipId) return false;
        if (studentId != that.studentId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + internshipId;
        result = 31 * result + studentId;
        result = 31 * result + (accepted ? 1 : 0);
        return result;
    }

    public int getInternshipId() {

        return internshipId;
    }

    public void setInternshipId(int internshipId) {
        this.internshipId = internshipId;
    }

    public int getStudentId() {

        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
