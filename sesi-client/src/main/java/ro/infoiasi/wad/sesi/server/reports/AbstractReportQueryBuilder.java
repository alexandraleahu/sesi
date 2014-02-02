package ro.infoiasi.wad.sesi.server.reports;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import ro.infoiasi.wad.sesi.client.reports.NumericRestriction;
import ro.infoiasi.wad.sesi.client.reports.QueryBean;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;
import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.shared.ComparisonOperator;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

abstract class AbstractReportQueryBuilder {

    protected StringBuilder finalQueryBuilder;

    protected AbstractReportQueryBuilder() {
        finalQueryBuilder = new StringBuilder();
    }
    protected static final List<String> FIELD_NAMES = Lists.newArrayList("?iname", "?iSesiUrl", "?cname", "?cSesiUrl",
            "?sname", "?sSesiUrl", "?schoolName", "?status", "?feedback");
    protected static final String PUBLISHED_AT_SPARQL_PROP = "?" + Constants.PUBLISHED_AT_PROP;
    protected static final String APP_OR_PROGRESS_DETAILS = "?app";
    protected static final List<String> PERIOD = Lists.newArrayList("?startDate", "?endDate");
    protected boolean filterAdded = false;

    protected AbstractReportQueryBuilder withBasicSelectFields(boolean applications) {

        finalQueryBuilder
                .append(Joiner.on(", ")
                        .join(FIELD_NAMES));
        if (applications) {
            finalQueryBuilder.append(", ")
                    .append(PUBLISHED_AT_SPARQL_PROP);
        } else {

            finalQueryBuilder.append(", ")
                    .append(Joiner.on(", ")
                            .join(PERIOD));
        }
        return this;


    }

    protected AbstractReportQueryBuilder withApplicationOrProgressDetailsFields(boolean applications) {

        finalQueryBuilder.append(" ")
                .append(APP_OR_PROGRESS_DETAILS)
                .append(" ")
                .append(SESI_SCHEMA_NS)
                .append(STATUS_PROP)
                .append(" ?status; ")
                .append(SESI_SCHEMA_NS)
                .append(FEEDBACK_PROP)
                .append(" ?feedback ");

        if (applications) {

            finalQueryBuilder.append("; ")
                    .append(SESI_SCHEMA_NS)
                    .append(PUBLISHED_AT_SPARQL_PROP)
                    .append(" ")
                    .append(PUBLISHED_AT_SPARQL_PROP)
                    .append(" . ");
        } else {

            finalQueryBuilder.append(". ")
                    .append(" ?i ")
                    .append(SESI_SCHEMA_NS)
                    .append(START_DATE_PROP)
                    .append("  ")
                    .append(PERIOD.get(0))
                    .append(" ; ")
                    .append(SESI_SCHEMA_NS)
                    .append(END_DATE_PROP)
                    .append("  ")
                    .append(PERIOD.get(1))
                    .append(" . ");
        }

        return this;
    }

    protected AbstractReportQueryBuilder withBasicWhereFields() {

        finalQueryBuilder.append("?i ")
                .append(SESI_SCHEMA_NS)
                .append(NAME_PROP)
                .append(" ?iname; ")
                .append(SESI_SCHEMA_NS)
                .append(SESI_URL_PROP)
                .append(" ?iSesiUrl. ")
                .append(SESI_SCHEMA_NS)
                .append(PUBLISHED_BY_PROP)
                .append(" ?c. ")
                .append("?c ")
                .append(SESI_SCHEMA_NS)
                .append(NAME_PROP)
                .append(" ?cname; ")
                .append(SESI_SCHEMA_NS)
                .append(SESI_URL_PROP)
                .append(" ?cSesiUrl. ")
                .append(" ?s ")
                .append(SESI_SCHEMA_NS)
                .append(NAME_PROP)
                .append(" ?sname; ")
                .append(SESI_SCHEMA_NS)
                .append(SESI_URL_PROP)
                .append(" ?sSesiUrl. ");



        return this;
    }

    protected AbstractReportQueryBuilder withGroupByFields(boolean applications) {

        Iterable<String> fields = Iterables.concat(FIELD_NAMES, applications ? Lists.newArrayList(PUBLISHED_AT_SPARQL_PROP)
                                                                             : PERIOD);
        finalQueryBuilder.append(Joiner.on(" ")
                                       .join(fields));
        return this;
    }

    protected AbstractReportQueryBuilder startSelect() {
        finalQueryBuilder.append(" select ");

        return this;
    }

    protected AbstractReportQueryBuilder startWhere() {
        finalQueryBuilder.append(" where ");

        return this;
    }

    protected AbstractReportQueryBuilder startFilter() {
        finalQueryBuilder.append(" filter ");

        return this;
    }

    protected AbstractReportQueryBuilder startGroupBy() {
        finalQueryBuilder.append(" group by ");

        return this;
    }

    protected AbstractReportQueryBuilder startHaving() {
        finalQueryBuilder.append(" having ");

        return this;
    }

    protected AbstractReportQueryBuilder openCurlyBrace() {
        finalQueryBuilder.append(" { ");

        return this;
    }

    protected AbstractReportQueryBuilder closeCurlyBrace() {
        finalQueryBuilder.append(" } ");

        return this;
    }

    protected AbstractReportQueryBuilder openBracket() {
        finalQueryBuilder.append(" ( ");

        return this;
    }

    protected AbstractReportQueryBuilder closeBracket() {
        finalQueryBuilder.append(" ) ");

        return this;
    }

    protected AbstractReportQueryBuilder withStatusFilter(List<StudentInternshipRelation.Status> statuses) {

        if (statuses != null) {

            if (!filterAdded) {
                filterAdded = true;
            } else {
                addAndOperator();

            }

            finalQueryBuilder.append(" (?status = ");
            List<String> uriStatuses = Lists.transform(statuses, new Function<StudentInternshipRelation.Status, String>() {
                @Nullable
                @Override
                public String apply(@Nullable StudentInternshipRelation.Status input) {
                    return SESI_SCHEMA_NS + input.toString();
                }
            });

            finalQueryBuilder.append(Joiner.on(" || ?status = ")
                                           .join(uriStatuses))
                             .append(") ");
        }
        return this;
    }

    protected AbstractReportQueryBuilder withNameFilter(List<String> names, String fieldName) {
        if (names != null) {

            if (!filterAdded) {
                filterAdded = true;
            } else {
                addAndOperator();
            }

            finalQueryBuilder.append("(")
                            .append(fieldName)
                            .append(" = ");


            finalQueryBuilder.append(Joiner.on(" || " + fieldName + " = ")
                                           .join(names))
                             .append(") ");
        }
        return this;
    }
    protected AbstractReportQueryBuilder addAndOperator() {

        finalQueryBuilder.append(" && ");
        return this;
    }
    protected AbstractReportQueryBuilder addOrOperator() {

        finalQueryBuilder.append(" || ");
        return this;
    }
    protected  AbstractReportQueryBuilder withPeriodFilter(Date startDate, Date endDate, boolean applications) {
        boolean isStartDate = false;
        if(startDate != null || endDate != null) {

            if (!filterAdded) {
                filterAdded = true;
            } else {
                addAndOperator();
            }

            finalQueryBuilder.append("( ");

        }

        if (startDate != null) {
            isStartDate = true;
            if (applications) {
                addDateFilter(startDate, ComparisonOperator.ge, PUBLISHED_AT_SPARQL_PROP);


            } else {
                addDateFilter(startDate, ComparisonOperator.ge, PERIOD.get(0));
            }
        }

        if (endDate != null) {

           finalQueryBuilder.append(isStartDate ? " && " : "");
           if (applications) {
               addDateFilter(startDate, ComparisonOperator.le, PUBLISHED_AT_SPARQL_PROP);

           } else {
               addDateFilter(startDate, ComparisonOperator.le, PERIOD.get(1));

           }
        }

        if(startDate != null || endDate != null) {
            finalQueryBuilder.append(") ");
        }

        return this;
    }

    private void addDateFilter(Date date, ComparisonOperator op, String fieldName) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-ddTHH:mm:ss Z");
        finalQueryBuilder.append(" ")
                         .append(fieldName)
                         .append(" ")
                         .append(op.getDescription())
                         .append(" ")
                         .append("\"")
                         .append(formatter.parseDateTime(date.toString()))
                         .append("\"^^xsd:dateTime");
    }

    protected AbstractReportQueryBuilder withHavingClause(NumericRestriction numericRestriction) {

        if (numericRestriction != null) {

             finalQueryBuilder.append(" count (")
                              .append(APP_OR_PROGRESS_DETAILS)
                              .append(") ")
                              .append(numericRestriction.getOp().getDescription())
                              .append(" ")
                              .append(numericRestriction.getLimit());
        }

        return this;
    }
    protected abstract AbstractReportQueryBuilder withMainResourceWhereFields(boolean applications);

    protected String build() {
        return finalQueryBuilder.toString();
    }

    public String buildQuery(QueryBean queryBean) {

        boolean applications = queryBean.isApplications();
        AbstractReportQueryBuilder queryBuilder = startSelect()
                .withBasicSelectFields(applications)
                .startWhere()
                .openCurlyBrace()
                    .withMainResourceWhereFields(applications)
                    .withApplicationOrProgressDetailsFields(applications)
                    .withBasicWhereFields();

        if (queryBean.getStatuses() != null ||
                queryBean.getCompanyNames() != null ||
                queryBean.getFacultyNames() != null ||
                queryBean.getStartDate() != null ||
                queryBean.getEndDate() != null) {

            queryBuilder.startFilter()
                        .openBracket()
                        .withStatusFilter(queryBean.getStatuses())
                        .withNameFilter(queryBean.getCompanyNames(), "?c")
                        .withNameFilter(queryBean.getFacultyNames(), "schoolName")
                        .withPeriodFilter(queryBean.getStartDate(), queryBean.getEndDate(), applications)
                        .closeBracket();





        }

        AbstractReportQueryBuilder builder = queryBuilder.closeCurlyBrace()
                                                        .startGroupBy()
                                                        .withGroupByFields(applications);

        if (queryBean.getNumericRestriction() != null) {

            builder.startHaving()
                    .openBracket()
                    .withHavingClause(queryBean.getNumericRestriction())
                    .closeBracket();
        }

        return builder.build();
    }


    public static void main(String[] args) {


    }

}
