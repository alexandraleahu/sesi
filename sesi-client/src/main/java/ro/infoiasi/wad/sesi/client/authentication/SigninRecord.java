package ro.infoiasi.wad.sesi.client.authentication;

import org.scribe.model.Token;

public class SigninRecord {
	public String requestToken;
	public String requestSecret;
	public String verifier;
	
	public String provider;
	public String authenticateUrl;
	public Token token;
}
