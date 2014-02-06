package ro.infoiasi.wad.sesi.client.reports;

import ro.infoiasi.wad.sesi.core.util.HasDescription;
import ro.infoiasi.wad.sesi.shared.ComparisonOperator;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReportBean implements Serializable {

    private List<String> companyNames;
    private List<String> facultyNames;

    private NumericRestriction numericRestriction;

    private List<String> statuses;

    private Date startDate;
    private Date endDate;

    private StudentInternshipRelationType studentInternshipRelation; // whether applications or progress details

    private MainResourceType resourceType;

    public ReportBean() {
        // initializing with default values
        //resourceType = MainResourceType.Internships;
        //studentInternshipRelation = StudentInternshipRelationType.Applications;
        numericRestriction = new NumericRestriction();
        numericRestriction.setOp(ComparisonOperator.eq);
        numericRestriction.setLimit(1);
    }

    public StudentInternshipRelationType getStudentInternshipRelation() {
        return studentInternshipRelation;
    }

    public List<String> getCompanyNames() {
        return companyNames;
    }

    public void setCompanyNames(List<String> companyNames) {
        this.companyNames = companyNames;
    }

    public List<String> getFacultyNames() {
        return facultyNames;
    }

    public void setFacultyNames(List<String> facultyNames) {
        this.facultyNames = facultyNames;
    }

    public NumericRestriction getNumericRestriction() {
        return numericRestriction;
    }

    public void setNumericRestriction(NumericRestriction numericRestriction) {
        this.numericRestriction = numericRestriction;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
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

    public StudentInternshipRelationType isApplications() {
        return studentInternshipRelation;
    }

    public void setStudentInternshipRelation(StudentInternshipRelationType studentInternshipRelation) {
        this.studentInternshipRelation = studentInternshipRelation;
    }

    public MainResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(MainResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public static enum MainResourceType {

        Internships,
        Students
    }
    public static enum StudentInternshipRelationType implements HasDescription {

        Applications ("Applications"),
        InProgressInternships("In Progress Internships");

        private final String description;

        StudentInternshipRelationType(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReportBean{");
        sb.append("companyNames=").append(companyNames);
        sb.append(", facultyNames=").append(facultyNames);
        sb.append(", numericRestriction=").append(numericRestriction);
        sb.append(", statuses=").append(statuses);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", studentInternshipRelation=").append(studentInternshipRelation);
        sb.append(", resourceType=").append(resourceType);
        sb.append('}');
        return sb.toString();
    }
}
