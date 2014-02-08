package ro.infoiasi.wad.sesi.shared;

public class UnsuccessfulLoginException extends RuntimeException {


    public UnsuccessfulLoginException() {
        super("Wrong username or password");
    }
    public UnsuccessfulLoginException(String msg) {
        super(msg);
    }
}
