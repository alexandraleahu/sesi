package ro.infoiasi.wad.sesi.service.authentication;

import ro.infoiasi.wad.sesi.core.model.User;
import ro.infoiasi.wad.sesi.core.util.Handler;

import java.util.Set;

public abstract class AuthorizationHandler implements Handler<User, Boolean> {

    public abstract Set<User> getAuthorizedActors();

    @Override
    public Boolean handle(User input) throws Exception {
        return getAuthorizedActors().contains(input);
    }
}
