package ro.infoiasi.wad.sesi.shared;

public class ResourceAlreadyExistsException extends RuntimeException {


    public ResourceAlreadyExistsException() {
        super("Resource already exists!");
    }
    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }
}
