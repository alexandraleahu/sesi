package ro.infoiasi.wad.sesi.core.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentLinkedinProfile implements Serializable {
    public String name;
    public String lastName;
    public String location;
    public List<String> skills;
    public List<String> schools;

    public StudentLinkedinProfile(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        skills = new ArrayList<>();
        schools = new ArrayList<>();
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public void addSchool(String school) {
        schools.add(school);
    }
}
