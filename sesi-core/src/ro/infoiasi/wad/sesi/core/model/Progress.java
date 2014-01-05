package ro.infoiasi.wad.sesi.core.model;

import java.util.EnumSet;

public class Progress extends StudentInternshipRelation {

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


}
