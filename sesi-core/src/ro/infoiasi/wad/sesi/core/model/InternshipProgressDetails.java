package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;

import java.util.EnumSet;

public class InternshipProgressDetails extends StudentInternshipRelation {

    private String teacherId;
    private String companyFeedback;

    @Override
    public EnumSet<Status> getPossibleStatus() {
        return EnumSet.of(Status.InProgress, Status.Finished);
    }

    @Override
    public String getDescription() {
        return getCompanyFeedback();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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
                .add("teacherId", teacherId)
                .add("companyFeedback", companyFeedback)
                .toString();
    }

    @Override
    public String getRelativeUri() {
        return "/internshipProgressDetails/" + getId();
    }
}
