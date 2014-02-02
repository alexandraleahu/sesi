package ro.infoiasi.wad.sesi.server.reports;

class InternshipsReportQueryBuilder extends AbstractReportQueryBuilder {

       @Override
       protected AbstractReportQueryBuilder withMainResourceWhereFields(boolean applications) {
           return this;
       }

   }