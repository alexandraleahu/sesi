package ro.infoiasi.wad.sesi.client.authentication.users;

public class Student extends User {

    private String university = null;
    
    public Student(String id, String password, String email, String university) {
        super(id, password, email);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
