package ro.infoiasi.wad.sesi.shared;

import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;
import ro.infoiasi.wad.sesi.core.model.Resource;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import java.io.Serializable;
import java.util.Date;

public class ReportResult implements Serializable {

    private Resource internshipBasic;
    private Resource companyBasic;
    private Resource studentBasic;
    private OntologyExtraInfo schoolBasic;

    private StudentInternshipRelation.Status status;

    private String feedback;

    private Date publishedAt;
    private Date startDate;
    private Date endDate;


    public Resource getInternshipBasic() {
        return internshipBasic;
    }

    public void setInternshipBasic(Resource internshipBasic) {
        this.internshipBasic = internshipBasic;
    }

    public Resource getCompanyBasic() {
        return companyBasic;
    }

    public void setCompanyBasic(Resource companyBasic) {
        this.companyBasic = companyBasic;
    }

    public Resource getStudentBasic() {
        return studentBasic;
    }

    public void setStudentBasic(Resource studentBasic) {
        this.studentBasic = studentBasic;
    }

    public OntologyExtraInfo getSchoolBasic() {
        return schoolBasic;
    }

    public void setSchoolBasic(OntologyExtraInfo schoolBasic) {
        this.schoolBasic = schoolBasic;
    }

    public StudentInternshipRelation.Status getStatus() {
        return status;
    }

    public void setStatus(StudentInternshipRelation.Status status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportResult{");
        sb.append("internshipBasic=").append(internshipBasic);
        sb.append(", companyBasic=").append(companyBasic);
        sb.append(", studentBasic=").append(studentBasic);
        sb.append(", schoolBasic=").append(schoolBasic);
        sb.append(", status=").append(status);
        sb.append(", feedback='").append(feedback).append('\'');
        sb.append(", publishedAt=").append(publishedAt);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append('}');
        return sb.toString();
    }
}
