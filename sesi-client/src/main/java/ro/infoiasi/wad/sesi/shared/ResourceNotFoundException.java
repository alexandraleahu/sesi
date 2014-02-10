package ro.infoiasi.wad.sesi.shared;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException() {
        super("Resource not found!");
    }
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
