package ro.infoiasi.wad.sesi.core.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentLinkedinProfile implements Serializable {
    public String name;
    public String lastName;
    public String location;
    public Set<String> skills;
    public Set<String> schools;

    public StudentLinkedinProfile() {
    }

    public StudentLinkedinProfile(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        skills = new HashSet();
        schools = new HashSet();
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public void addSchool(String school) {
        schools.add(school);
    }
}
