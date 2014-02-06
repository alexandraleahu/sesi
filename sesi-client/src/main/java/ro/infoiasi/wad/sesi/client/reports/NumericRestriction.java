package ro.infoiasi.wad.sesi.client.reports;

import ro.infoiasi.wad.sesi.shared.ComparisonOperator;

import java.io.Serializable;

public class NumericRestriction implements Serializable {

    private ComparisonOperator op;

    private int limit;


    public ComparisonOperator getOp() {
        return op;
    }

    public void setOp(ComparisonOperator op) {
        this.op = op;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NumericRestriction{");
        sb.append("op=").append(op);
        sb.append(", limit=").append(limit);
        sb.append('}');
        return sb.toString();
    }
}
