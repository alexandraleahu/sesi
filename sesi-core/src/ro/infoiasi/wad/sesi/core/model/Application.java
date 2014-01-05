package ro.infoiasi.wad.sesi.core.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Application {

    private int id;
    private Internship internship;
    private Student student;
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
                ", internship=" + internship +
                ", student=" + student +
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
        if (internship != that.internship) return false;
        if (student != that.student) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + internship.hashCode();
        result = 31 * result + student.hashCode();
        result = 31 * result + (accepted ? 1 : 0);
        return result;
    }

    public Internship getInternship() {

        return internship;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
    }

    public Student getStudent() {

        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
