package ro.infoiasi.wad.sesi.server.sparqlservice;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * We use a ListMultimap because it allows duplicate values and maintains the order in which the elements were inserted
 */
public class ResultSetToMultimap implements Function<ResultSet, ListMultimap<String, String>> {


    @Nullable
    @Override
    public ListMultimap<String, String> apply(ResultSet input) {

        ListMultimap<String, String> resultMap = ArrayListMultimap.create();

        while (input.hasNext()) {
            QuerySolution querySolution = input.nextSolution();

            Iterator<String> varNames = querySolution.varNames();
            while (varNames.hasNext()) {
                String varName = varNames.next();
                resultMap.put(varName, querySolution.get(varName).toString());

            }
        }
        return resultMap;
    }
}
