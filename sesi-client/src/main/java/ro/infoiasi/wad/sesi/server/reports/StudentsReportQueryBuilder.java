package ro.infoiasi.wad.sesi.server.reports;

import ro.infoiasi.wad.sesi.client.reports.ReportBean;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;
class StudentsReportQueryBuilder extends AbstractReportQueryBuilder {

       @Override
       protected AbstractReportQueryBuilder withMainResourceWhereFields(ReportBean.StudentInternshipRelationType applications) {
           finalQueryBuilder.append(" ?s ")
                            .append(" rdf:type ")
                            .append(" ")
                            .append(SESI_SCHEMA_SHORT)
                            .append(STUDENT_CLASS)
                            .append("; ");


           if (applications == ReportBean.StudentInternshipRelationType.Applications) {
               finalQueryBuilder.append(SESI_SCHEMA_SHORT)
                                .append(SUBMITTED_APPLICATION_PROP)
                                .append(" ")
                                .append(APP_OR_PROGRESS_DETAILS)
                                .append(". ")
                                .append(" ")
                                .append(APP_OR_PROGRESS_DETAILS)
                                .append(" ")
                                .append(SESI_SCHEMA_SHORT)
                                .append(APPLICATION_INTERNSHIP_PROP)
                                .append(" ")
                                .append(" ?i.");

           } else {
               finalQueryBuilder.append(SESI_SCHEMA_SHORT)
                               .append(ATTENDED_INTERNSHIP_PROGRESS_PROP)
                               .append(" ")
                               .append(APP_OR_PROGRESS_DETAILS)
                               .append(". ")
                               .append(" ")
                               .append(APP_OR_PROGRESS_DETAILS)
                               .append(" ")
                               .append(SESI_SCHEMA_SHORT)
                               .append(ATTENDED_INTERNSHIP_PROP)
                               .append(" ")
                               .append(" ?i.");
           }

           return this;
       }


   }