package ro.infoiasi.wad.sesi.core.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class StudentLinkedinProfile implements Serializable {
    public String firstName;
    public String lastName;
    public String location;
    public Set<String> skills;
    public Set<String> schools;
    public Set<String> projects;

    public StudentLinkedinProfile() {
    }

    public StudentLinkedinProfile(String name, String lastName) {
        this.firstName = name;
        this.lastName = lastName;
        skills = new HashSet();
        schools = new HashSet();
        projects = new HashSet();
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public void addSchool(String school) {
        schools.add(school);
    }

    public void addProject(String school) {
        schools.add(school);
    }


}
