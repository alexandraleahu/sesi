package ro.infoiasi.wad.sesi.server.reports;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

class InternshipsReportQueryBuilder extends AbstractReportQueryBuilder {

       @Override
       protected AbstractReportQueryBuilder withMainResourceWhereFields(boolean applications) {
           finalQueryBuilder.append(" ?i ")
                   .append(" rdf:type ")
                   .append(" ")
                   .append(SESI_SCHEMA_SHORT)
                   .append(INTERNSHIP_CLASS)
                   .append("; ");


           if (applications) {
               finalQueryBuilder.append(SESI_SCHEMA_SHORT)
                       .append(HAS_INTERNSHIP_APPLICATION_PROP)
                       .append(" ")
                       .append(APP_OR_PROGRESS_DETAILS)
                       .append(". ")
                       .append(" ")
                       .append(APP_OR_PROGRESS_DETAILS)
                       .append(" ")
                       .append(SESI_SCHEMA_SHORT)
                       .append(CANDIDATE_PROP)
                       .append(" ")
                       .append(" ?s.");

           } else {
               finalQueryBuilder.append(SESI_SCHEMA_SHORT)
                       .append(PROGRESS_PROP)
                       .append(" ")
                       .append(APP_OR_PROGRESS_DETAILS)
                       .append(". ")
                       .append(" ")
                       .append(APP_OR_PROGRESS_DETAILS)
                       .append(" ")
                       .append(SESI_SCHEMA_SHORT)
                       .append(ATTENDEE_STUDENT_PROP)
                       .append(" ")
                       .append(" ?s.");
           }

           return this;
       }

   }