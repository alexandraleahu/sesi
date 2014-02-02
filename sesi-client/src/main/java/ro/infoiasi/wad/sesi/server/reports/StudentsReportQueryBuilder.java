package ro.infoiasi.wad.sesi.server.reports;

import com.hp.hpl.jena.vocabulary.RDF;
import static ro.infoiasi.wad.sesi.core.util.Constants.*;
class StudentsReportQueryBuilder extends AbstractReportQueryBuilder {

       @Override
       protected AbstractReportQueryBuilder withMainResourceWhereFields(boolean applications) {
           finalQueryBuilder.append(" ?s ")
                            .append(RDF.type.toString())
                            .append(" ")
                            .append(SESI_SCHEMA_NS)
                            .append(STUDENT_CLASS)
                            .append(" ; ");

           if (applications) {
               finalQueryBuilder.append(SESI_SCHEMA_NS)
                                .append(SUBMITTED_APPLICATION_PROP)
                                .append(" ")
                                .append(APP_OR_PROGRESS_DETAILS)
                                .append(". ")
                                .append(" ")
                                .append(APP_OR_PROGRESS_DETAILS)
                                .append(" ")
                                .append(SESI_SCHEMA_NS)
                                .append(APPLICATION_INTERNSHIP_PROP)
                                .append(" ")
                                .append(" ?i.");

           } else {
               finalQueryBuilder.append(SESI_SCHEMA_NS)
                               .append(ATTENDED_INTERNSHIP_PROGRESS_PROP)
                               .append(" ")
                               .append(APP_OR_PROGRESS_DETAILS)
                               .append(". ")
                               .append(" ")
                               .append(APP_OR_PROGRESS_DETAILS)
                               .append(" ")
                               .append(SESI_SCHEMA_NS)
                               .append(ATTENDED_INTERNSHIP_PROP)
                               .append(" ")
                               .append(" ?i.");
           }

           return this;
       }


   }