package ro.infoiasi.wad.sesi.shared;

public class UnsuccessfulLoginException extends Exception {


    public UnsuccessfulLoginException() {
        super("Wrong username or password");
    }
    public UnsuccessfulLoginException(String msg) {
        super(msg);
    }
}
