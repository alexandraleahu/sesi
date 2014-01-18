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
