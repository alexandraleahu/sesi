package ro.infoiasi.wad.sesi.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ro.infoiasi.wad.sesi.client.rpc.OntologyExtraInfoService;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;


public class OntologyExtraInfoServiceImpl extends RemoteServiceServlet implements OntologyExtraInfoService {
    @Override
    public OntologyExtraInfo get(String ontologyClassName, String ontologyId) {

       return null;
    }
}