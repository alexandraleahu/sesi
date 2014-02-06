package ro.infoiasi.wad.sesi.server.schools;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ro.infoiasi.wad.sesi.client.schools.SchoolsService;
import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.server.sparqlservice.SparqlService;

import java.util.List;


public class SchoolsServiceImpl extends RemoteServiceServlet implements SchoolsService {
    @Override
    public List<String> getAllFacultyNames() {

        final SparqlService sparqlService = new SparqlService();
        return sparqlService.getAllNamesOfType(Constants.SESI_SCHEMA_NS + Constants.FACULTY_CLASS);
    }
}