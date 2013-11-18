package ro.infoiasi.wad.sesi.core.util;


public final class HandlerChain {

    private HandlerChain() {

    }

    public static <I, T, O> Handler<I, O> chain(final Handler<I, T> first, final Handler<T, O> second) {

        return new Handler<I, O>() {
            @Override
            public O handle(I input) throws Exception {
                return second.handle(first.handle(input));
            }
        };
    }

}
