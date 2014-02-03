package ro.infoiasi.wad.sesi.server.reports;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import ro.infoiasi.wad.sesi.client.reports.NumericRestriction;
import ro.infoiasi.wad.sesi.client.reports.QueryBean;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;
import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.shared.ComparisonOperator;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

abstract class AbstractReportQueryBuilder {

    protected StringBuilder finalQueryBuilder;

    protected AbstractReportQueryBuilder() {
        finalQueryBuilder = new StringBuilder();
    }
    protected static final List<String> FIELD_NAMES = Lists.newArrayList("?iname", "?iSesiUrl", "?cname", "?cSesiUrl",
            "?sname", "?sSesiUrl", "?schoolName", "?schoolUri", "?status", "?feedback");
    protected static final String PUBLISHED_AT_SPARQL_PROP = "?" + Constants.PUBLISHED_AT_PROP;
    protected static final String APP_OR_PROGRESS_DETAILS = "?app";
    protected static final List<String> PERIOD = Lists.newArrayList("?startDate", "?endDate");
    protected boolean filterAdded = false;

    protected AbstractReportQueryBuilder withBasicSelectFields(boolean applications) {

        finalQueryBuilder
                .append(Joiner.on(" ")
                        .join(FIELD_NAMES));
        if (applications) {
            finalQueryBuilder.append(" ")
                    .append(PUBLISHED_AT_SPARQL_PROP);
        } else {

            finalQueryBuilder.append(" ")
                    .append(Joiner.on(" ")
                            .join(PERIOD));
        }
        return this;


    }

    protected AbstractReportQueryBuilder withApplicationOrProgressDetailsFields(boolean applications) {

        finalQueryBuilder.append(" ")
                .append(APP_OR_PROGRESS_DETAILS)
                .append(" ")
                .append(SESI_SCHEMA_SHORT)
                .append(STATUS_PROP)
                .append(" ?status; ")
                .append(SESI_SCHEMA_SHORT)
                .append(FEEDBACK_PROP)
                .append(" ?feedback ");

        if (applications) {

            finalQueryBuilder.append("; ")
                    .append(SESI_SCHEMA_SHORT)
                    .append(PUBLISHED_AT_PROP)
                    .append(" ")
                    .append(PUBLISHED_AT_SPARQL_PROP)
                    .append(" . ");
        } else {

            finalQueryBuilder.append(". ")
                    .append(" ?i ")
                    .append(SESI_SCHEMA_SHORT)
                    .append(START_DATE_PROP)
                    .append("  ")
                    .append(PERIOD.get(0))
                    .append(" ; ")
                    .append(SESI_SCHEMA_SHORT)
                    .append(END_DATE_PROP)
                    .append("  ")
                    .append(PERIOD.get(1))
                    .append(" . ");
        }

        return this;
    }

    protected AbstractReportQueryBuilder withBasicWhereFields() {

        finalQueryBuilder.append("?i ")
                .append(SESI_SCHEMA_SHORT)
                .append(NAME_PROP)
                .append(" ?iname; ")
                .append(SESI_SCHEMA_SHORT)
                .append(SESI_URL_PROP)
                .append(" ?iSesiUrl; ")
                .append(SESI_SCHEMA_SHORT)
                .append(PUBLISHED_BY_PROP)
                .append(" ?c. ")
                .append("?c ")
                .append(SESI_SCHEMA_SHORT)
                .append(NAME_PROP)
                .append(" ?cname; ")
                .append(SESI_SCHEMA_SHORT)
                .append(SESI_URL_PROP)
                .append(" ?cSesiUrl. ")
                .append(" ?s ")
                .append(SESI_SCHEMA_SHORT)
                .append(NAME_PROP)
                .append(" ?sname; ")
                .append(SESI_SCHEMA_SHORT)
                .append(SESI_URL_PROP)
                .append(" ?sSesiUrl; ")
                .append(SESI_SCHEMA_SHORT)
                .append(HAS_STUDIES_PROP)
                .append(" ?studies. ")
                .append("?studies ")
                .append(SESI_SCHEMA_SHORT)
                .append(FACULTY_PROP)
                .append(" ?school. ")
                .append(" ?school ")
                .append(SESI_SCHEMA_SHORT)
                .append(NAME_PROP)
                .append(" ?schoolName. ");



        return this;
    }

    protected AbstractReportQueryBuilder startOptional() {

        finalQueryBuilder.append(" optional ");

        return this;
    }

    protected AbstractReportQueryBuilder withOptionalSchoolUrl() {

        finalQueryBuilder.append(" ?school ")
                         .append(" rdfs:seeAlso ")
                         .append(" ?schoolUri ");
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
                    return "<" + SESI_SCHEMA_NS + input.toString() + ">";
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

            List<String> withQuotation = Lists.transform(names, new Function<String, String>() {
                @Nullable
                @Override
                public String apply(@Nullable String input) {
                    return "\"" + input + "\"";
                }
            });
            finalQueryBuilder.append(Joiner.on(" || " + fieldName + " = ")
                                           .join(withQuotation))
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
                addDateFilter(PUBLISHED_AT_SPARQL_PROP, ComparisonOperator.ge, startDate);


            } else {
                addDateFilter(PERIOD.get(0), ComparisonOperator.ge, startDate);
            }
        }

        if (endDate != null) {

           finalQueryBuilder.append(isStartDate ? " && " : "");
           if (applications) {
               addDateFilter(PUBLISHED_AT_SPARQL_PROP, ComparisonOperator.le, endDate);

           } else {
               addDateFilter(PERIOD.get(1), ComparisonOperator.le, endDate);

           }
        }

        if(startDate != null || endDate != null) {
            finalQueryBuilder.append(") ");
        }

        return this;
    }

    private void addDateFilter(String fieldName, ComparisonOperator op, Date date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTime datetime = new DateTime(date);
        finalQueryBuilder.append(" ")
                         .append(fieldName)
                         .append(" ")
                         .append(op.getDescription())
                         .append(" ")
                         .append("\"")
                         .append(datetime.toString(formatter))
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

    protected AbstractReportQueryBuilder withPrefixes() {

        finalQueryBuilder.append("prefix ")
                         .append("rdf: <")
                         .append(RDF.getURI())
                         .append(">  prefix ")
                         .append(SESI_SCHEMA_SHORT)
                         .append(" <")
                         .append(SESI_SCHEMA_NS)
                         .append("> prefix")
                         .append(" xsd: <http://www.w3.org/2001/XMLSchema#> ")
                         .append(" prefix rdfs: <")
                         .append(RDFS.getURI())
                         .append("> ");

        return this;
    }
    public String buildQuery(QueryBean queryBean) {

        boolean applications = queryBean.isApplications();
        AbstractReportQueryBuilder queryBuilder = withPrefixes()
                                                .startSelect()
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
                            .withNameFilter(queryBean.getCompanyNames(), "?cname")
                            .withNameFilter(queryBean.getFacultyNames(), "?schoolName")
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

        QueryBean bean = new QueryBean();

        bean.setApplications(true);

        bean.setCompanyNames(Lists.newArrayList("VirtualComp", "comp2"));
        bean.setFacultyNames(Lists.newArrayList("Faculty Of Computer Science"));
        bean.setStatuses(Lists.newArrayList(StudentInternshipRelation.Status.accepted, StudentInternshipRelation.Status.pending));
        bean.setResourceType(QueryBean.MainResourceType.Students);

        NumericRestriction numericRestriction = new NumericRestriction();
        numericRestriction.setLimit(1);
        numericRestriction.setOp(ComparisonOperator.ge);

        bean.setNumericRestriction(numericRestriction);

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2013, Calendar.MARCH, 13, 19, 0, 0);
        bean.setStartDate(calendar.getTime());

        calendar.set(2014, Calendar.DECEMBER, 13, 19, 0, 0);
        bean.setEndDate(calendar.getTime());

        System.out.println(ReportQueryBuilder.buildQuery(bean));

    }

}
