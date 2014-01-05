package ro.infoiasi.wad.sesi.core.model;

public class Teacher implements User, Resource {

    private School school;
    private int id;
    private String description;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return null;
    }

    @Override
    public Role getRole() {
        return Role.TEACHER;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (id != teacher.id) return false;
        if (description != null ? !description.equals(teacher.description) : teacher.description != null) return false;
        if (school != null ? !school.equals(teacher.school) : teacher.school != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = school != null ? school.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "school=" + school +
                ", id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
