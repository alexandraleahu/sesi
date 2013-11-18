package ro.infoiasi.wad.sesi.core.util;

public interface Handler<In, Out> {

    Out handle (In input) throws Exception;

}
