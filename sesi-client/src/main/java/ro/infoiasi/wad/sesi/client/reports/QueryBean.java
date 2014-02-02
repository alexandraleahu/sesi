package ro.infoiasi.wad.sesi.client.reports;

import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QueryBean implements Serializable {

    private List<String> companyNames;
    private List<String> facultyNames;

    private NumericRestriction numericRestriction;

    private List<StudentInternshipRelation.Status> statuses;

    private Date startDate;
    private Date endDate;

    private boolean applications; // whether applications or progress details

    private MainResourceType resourceType;

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

    public List<StudentInternshipRelation.Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StudentInternshipRelation.Status> statuses) {
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

    public boolean isApplications() {
        return applications;
    }

    public void setApplications(boolean applications) {
        this.applications = applications;
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

}
