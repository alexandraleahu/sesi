package ro.infoiasi.wad.sesi.core.util;

public final class HandlerChain {

    private HandlerChain(){}

    public static <I, T, O>  O chain(I input, Handler<I, T> first, Handler<T, O> second) throws Exception {

        return second.handle(first.handle(input));
    }
}
