package ro.infoiasi.wad.sesi.server.reports;

import ro.infoiasi.wad.sesi.client.reports.QueryBean;

public class ReportQueryBuilder {


   private ReportQueryBuilder(){}

   public static String buildQuery(QueryBean queryBean) {

       switch (queryBean.getResourceType()) {
           case Internships:
               return new InternshipsReportQueryBuilder().buildQuery(queryBean);
           case Students:
               return new StudentsReportQueryBuilder().buildQuery(queryBean);
           default:
               return null;

       }

   }

}
