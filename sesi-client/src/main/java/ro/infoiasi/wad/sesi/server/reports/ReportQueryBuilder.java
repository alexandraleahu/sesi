package ro.infoiasi.wad.sesi.server.reports;

import ro.infoiasi.wad.sesi.client.reports.ReportBean;

public class ReportQueryBuilder {


   private ReportQueryBuilder(){}

   public static String buildQuery(ReportBean reportBean) {

       switch (reportBean.getResourceType()) {
           case Internships:
               return new InternshipsReportQueryBuilder().buildQuery(reportBean);
           case Students:
               return new StudentsReportQueryBuilder().buildQuery(reportBean);
           default:
               return null;

       }

   }

}
