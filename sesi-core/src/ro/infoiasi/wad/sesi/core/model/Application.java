package ro.infoiasi.wad.sesi.core.model;

public class Application {

    private int id;
    private Internship internship;
    private int studentId;
    private StudentProfile studentProfile;
    private boolean accepted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Internship getInternship() {
        return internship;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
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
                ", studentId=" + studentId +
                ", studentProfile=" + studentProfile +
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
        if (studentId != that.studentId) return false;
        if (internship != null ? !internship.equals(that.internship) : that.internship != null) return false;
        if (studentProfile != null ? !studentProfile.equals(that.studentProfile) : that.studentProfile != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (internship != null ? internship.hashCode() : 0);
        result = 31 * result + studentId;
        result = 31 * result + (studentProfile != null ? studentProfile.hashCode() : 0);
        result = 31 * result + (accepted ? 1 : 0);
        return result;
    }

    public int getStudentId() {

        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }
}
