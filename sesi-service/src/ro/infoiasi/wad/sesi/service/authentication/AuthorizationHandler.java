package ro.infoiasi.wad.sesi.service.authentication;

import ro.infoiasi.wad.sesi.core.model.Actor;
import ro.infoiasi.wad.sesi.core.util.Handler;

import java.util.Set;

public abstract class AuthorizationHandler implements Handler<Actor, Boolean> {

    public abstract Set<Actor> getAuthorizedActors();

    @Override
    public Boolean handle(Actor input) throws Exception {
        return getAuthorizedActors().contains(input);
    }
}
