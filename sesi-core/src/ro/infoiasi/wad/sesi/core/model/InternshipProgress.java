package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

import java.util.EnumSet;

public class InternshipProgress extends StudentInternshipRelation {

    private Teacher mentor;
    private int currentGrade;
    private String companyFeedback;

    @Override
    public EnumSet<Status> getPossibleStatus() {
        return EnumSet.of(Status.InProgress, Status.Finished);
    }

    @Override
    public String getDescription() {
        return getCompanyFeedback();
    }

    public Teacher getMentor() {
        return mentor;
    }

    public void setMentor(Teacher mentor) {
        this.mentor = mentor;
    }

    public int getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(int currentGrade) {
        this.currentGrade = currentGrade;
    }

    public String getCompanyFeedback() {
        return companyFeedback;
    }

    public void setCompanyFeedback(String companyFeedback) {
        this.companyFeedback = companyFeedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InternshipProgress that = (InternshipProgress) o;
        if (getId() != that.getId()) return false;
        if (!getInternship().equals(that.getInternship())) return false;
        if (!getStudent().equals(that.getStudent())) return false;
        if (!getStatus().equals(that.getStatus())) return false;
        if (currentGrade != that.currentGrade) return false;
        if (companyFeedback != null ? !companyFeedback.equals(that.companyFeedback) : that.companyFeedback != null)
            return false;
        if (mentor != null ? !mentor.equals(that.mentor) : that.mentor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mentor != null ? mentor.hashCode() : 0;
        result = 31 * result + currentGrade;
        result = 31 * result + getId();
        result = 31 * result + getStudent().hashCode();
        result = 31 * result + getInternship().hashCode();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + (companyFeedback != null ? companyFeedback.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", getId())
                .add("internship", getInternship())
                .add("student", getStudent())
                .add("status", getStatus())
                .add("mentor", mentor)
                .add("currentGrade", currentGrade)
                .add("companyFeedback", companyFeedback)
                .toString();
    }
}
