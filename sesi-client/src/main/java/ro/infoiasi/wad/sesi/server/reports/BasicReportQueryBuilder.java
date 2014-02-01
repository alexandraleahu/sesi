package ro.infoiasi.wad.sesi.server.reports;

public abstract class BasicReportQueryBuilder {

    public abstract String getMainResourceQuery();

    public String getSelectFields() {

        StringBuilder sb = new StringBuilder();

        return sb.toString();



    }
}
