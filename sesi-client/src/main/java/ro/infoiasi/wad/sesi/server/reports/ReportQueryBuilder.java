package ro.infoiasi.wad.sesi.server.reports;

import ro.infoiasi.wad.sesi.client.teachers.reports.QueryBean;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class ReportQueryBuilder {


   private ReportQueryBuilder(){}

   public static String buildQuery(QueryBean queryBean) {

       switch (queryBean.getResourceType()) {
           case Internships:
               return new InternshipsReportQueryBuilder().build(queryBean);
           case Students:
               return new StudentsReportQueryBuilder().build(queryBean);
           default:
               return null;

       }

   }

   private static abstract class AbstractReportQueryBuilder {

       protected StringBuilder finalQueryBuilder;

       protected AbstractReportQueryBuilder() {
           finalQueryBuilder = new StringBuilder();
       }

       private AbstractReportQueryBuilder withBasicSelectFields() {

           finalQueryBuilder.append("select ?iname, ?iSesiUrl, ")
                   .append("?cname, ?cSesiUrl, ")
                   .append("?sname, ?sSesiUrl, ")
                   .append("?schoolName, ")
                   .append("?status, ")
                   .append("?feedback, ")
                   .append("?startDate, ")
                   .append("?endDate ");
           return this;


       }
       private AbstractReportQueryBuilder withBasicWhereFields() {

           finalQueryBuilder.append("?i ")
                   .append(NAME_PROP)
                   .append(" ?iname; ")
                   .append(SESI_URL_PROP)
                   .append(" ?iSesiUrl. ")
                   .append(PUBLISHED_BY_PROP)
                   .append(" ?c. ")
                   .append("?c ")
                   .append(NAME_PROP)
                   .append(" ?cname; ")
                   .append(SESI_URL_PROP)
                   .append(" ?cSesiUrl. ")
                   .append(" ?s ")
                   .append(NAME_PROP)
                   .append(" ?sname; ")
                   .append(SESI_URL_PROP)
                   .append(" ?sSesiUrl. ")
                   .append(" ?app ")
                   .append(STATUS_PROP)
                   .append(" ?status. ");

           return this;
       }

       protected abstract AbstractReportQueryBuilder withMainResourceWhereFields();
       protected abstract String build(QueryBean queryBean);

   }
   private static class StudentsReportQueryBuilder extends AbstractReportQueryBuilder {

       @Override
       protected AbstractReportQueryBuilder withMainResourceWhereFields() {
           return null;
       }

       @Override
       public String build(QueryBean queryBean) {
           return null;
       }
   }

    private static class InternshipsReportQueryBuilder extends AbstractReportQueryBuilder {

       @Override
       protected AbstractReportQueryBuilder withMainResourceWhereFields() {
           return null;
       }

       @Override
       public String build(QueryBean queryBean) {
           return null;
       }
   }



}
