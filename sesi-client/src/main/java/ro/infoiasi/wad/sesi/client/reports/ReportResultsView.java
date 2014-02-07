package ro.infoiasi.wad.sesi.client.reports;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import ro.infoiasi.wad.sesi.client.commonwidgets.ResourceColumn;
import ro.infoiasi.wad.sesi.client.commonwidgets.ResourceViewCell;
import ro.infoiasi.wad.sesi.core.model.Resource;
import ro.infoiasi.wad.sesi.shared.ReportResult;

import java.util.Date;
import java.util.List;


public class ReportResultsView extends Composite implements LeafValueEditor<List<ReportResult>> {

    private List<ReportResult> value;
    private ResourceColumn<ReportResult,Resource> internshipColumn;
    private ResourceColumn<ReportResult,Resource> companyColumn;
    private ResourceColumn<ReportResult,Resource> studentColumn;
    private TextColumn<ReportResult> schoolColumn;
    private TextColumn<ReportResult> statusColumn;
    private TextColumn<ReportResult> feedbackColumn;
    private TextColumn<ReportResult> publishedAtColumn;
    private TextColumn<ReportResult> startDateColumn;
    private TextColumn<ReportResult> endDateColumn;

    @Override
    public void setValue(List<ReportResult> value) {

        if (value != null) {

            this.value = value;
            resultsTable.setRowData(value);
        }

    }

    public void setValue(List<ReportResult> value, ReportBean.StudentInternshipRelationType type) {

        switch (type) {
            case Applications:
                removeStartAndEndDate();
                addPublishedDate();
                break;
            case InProgressInternships:
                removePublishedAt();
                addStartAndEndDate();
                break;
            default:
                break;
        }
        setValue(value);

    }

    private void removePublishedAt() {
        if (resultsTable.getColumnIndex(publishedAtColumn) != -1) {

            resultsTable.removeColumn(publishedAtColumn);
        }
    }

    private void removeStartAndEndDate() {
        if (resultsTable.getColumnIndex(startDateColumn) != -1) {

            resultsTable.removeColumn(startDateColumn);
        }

        if (resultsTable.getColumnIndex(endDateColumn) != -1) {

            resultsTable.removeColumn(endDateColumn);
        }
    }

    @Override
    public List<ReportResult> getValue() {
        return value;
    }

    interface ReportResultsViewUiBinder extends UiBinder<CellTable, ReportResultsView> {
    }

    private static ReportResultsViewUiBinder ourUiBinder = GWT.create(ReportResultsViewUiBinder.class);
    @UiField(provided = true)
    CellTable<ReportResult> resultsTable = new CellTable<ReportResult>(5);

    public ReportResultsView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        initColumns();
        composeBasicTable();


    }

    private void initColumns() {

        internshipColumn = new ResourceColumn<ReportResult, Resource>(new ResourceViewCell<Resource>()) {
            @Override
            public Resource getValue(ReportResult reportResult) {
                return reportResult.getInternshipBasic();
            }
        };

        companyColumn = new ResourceColumn<ReportResult, Resource>(new ResourceViewCell<Resource>()) {
            @Override
            public Resource getValue(ReportResult reportResult) {
                return reportResult.getCompanyBasic();
            }
        };

        studentColumn = new ResourceColumn<ReportResult, Resource>(new ResourceViewCell<Resource>()) {
            @Override
            public Resource getValue(ReportResult reportResult) {
                return reportResult.getStudentBasic();
            }
        };

        schoolColumn = new TextColumn<ReportResult>() {
            @Override
            public String getValue(ReportResult object) {
                return object.getSchoolBasic().getName();
            }
        };


        statusColumn = new TextColumn<ReportResult>() {
            @Override
            public String getValue(ReportResult object) {
                return object.getStatus().toString();
            }
        };

        feedbackColumn = new TextColumn<ReportResult>() {
            @Override
            public String getValue(ReportResult object) {
                return object.getFeedback();
            }
        };

        publishedAtColumn = new TextColumn<ReportResult>() {
            @Override
            public String getValue(ReportResult object) {
                Date publishedAt = object.getPublishedAt();
                return publishedAt != null ? publishedAt.toString() : "";
            }
        };

        startDateColumn = new TextColumn<ReportResult>() {
            @Override
            public String getValue(ReportResult object) {
                Date startDate = object.getStartDate();
                return startDate != null ? startDate.toString() : "";
            }
        };

        endDateColumn = new TextColumn<ReportResult>() {
            @Override
            public String getValue(ReportResult object) {
                Date endDate = object.getEndDate();
                return endDate != null ? endDate.toString() : "";
            }
        };

    }

    private void composeBasicTable() {


        resultsTable.addColumn(internshipColumn, "Internship");



        resultsTable.addColumn(companyColumn, "Company");



        resultsTable.addColumn(studentColumn, "Student");


        resultsTable.addColumn(schoolColumn, "School");




        resultsTable.addColumn(statusColumn, "Status");



        resultsTable.addColumn(feedbackColumn, "Feedback");


    }

    private void addPublishedDate() {


        if (resultsTable.getColumnIndex(publishedAtColumn) == -1) {

            resultsTable.addColumn(publishedAtColumn, "Published at");
        }
    }

    private void addStartAndEndDate() {


        if (resultsTable.getColumnIndex(startDateColumn) == -1) {

            resultsTable.addColumn(startDateColumn, "Start date");
        }


        if (resultsTable.getColumnIndex(endDateColumn) == -1) {

            resultsTable.addColumn(endDateColumn, "End date");
        }
    }


}