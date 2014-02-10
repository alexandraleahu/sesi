package ro.infoiasi.wad.sesi.server.login;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import ro.infoiasi.wad.sesi.client.authentication.SigninRecord;
import ro.infoiasi.wad.sesi.client.authentication.SigninService;
import ro.infoiasi.wad.sesi.core.model.StudentLinkedinProfile;
import ro.infoiasi.wad.sesi.core.model.User;
import ro.infoiasi.wad.sesi.core.util.XMLUtils;
import ro.infoiasi.wad.sesi.server.util.APIKeys;

import javax.servlet.http.HttpSession;

public class SigninServiceImpl extends RemoteServiceServlet implements SigninService {
    static private String LinkedinAuthenticateUrl = "https://www.linkedin.com/uas/oauth/authenticate?oauth_token=";

    private OAuthService oAuthService;
    //private Scribe linkedinService;

    @Override
    public String getAuthenticateUrl(String provider, String callbackUrl)
            throws Exception {
        String url = LinkedinAuthenticateUrl;

        OAuthService service = this.getService(callbackUrl);

        Token requestToken = service.getRequestToken();
        SigninRecord sr = new SigninRecord();
        sr.requestSecret = requestToken.getSecret();
        sr.requestToken = requestToken.getToken();
        sr.authenticateUrl = url + sr.requestToken;
        sr.provider = provider;
        sr.token = requestToken;

        HttpSession session = this.getThreadLocalRequest().getSession(true);
        session.setAttribute("SigninRecord", sr);
        return sr.authenticateUrl;
    }

    @Override
    public User verify(String verifierToken) throws Exception {
        HttpSession session = this.getThreadLocalRequest().getSession();
        if (session == null)
            throw new Exception("Service Error: Invalid Sign in request");

        SigninRecord sr = (SigninRecord) session.getAttribute("SigninRecord");
        if (sr == null)
            throw new Exception("Service Error: Invalid Sign in request");

        Token token = sr.token; //new Token(sr.requestToken, sr.requestSecret);
        Verifier verifier = new Verifier(verifierToken);
        OAuthService service = oAuthService;
        Token aToken = service.getAccessToken(token, verifier);
        OAuthRequest request = new OAuthRequest(Verb.GET, this.getProfileRequestString(sr.provider));

        service.signRequest(aToken, request);

        Response response = request.send();
        System.out.println("Response code: " + response.getCode());
        if (response.getCode() != 200) {
            System.out.println("Failed to verify: http code: " + response.getCode());
            throw new Exception("OAuth verified failed - http code: " + response.getCode());
        }
        String xml = response.getBody();
        System.out.println("Response body: " + xml);
        return XMLUtils.getUserFromLinkedInResponse(xml);

    }

    @Override
    public StudentLinkedinProfile getProfile(String verifierToken) throws Exception {
        HttpSession session = this.getThreadLocalRequest().getSession();
        if (session == null)
            throw new Exception("Service Error: Invalid Sign in request");

        SigninRecord sr = (SigninRecord) session.getAttribute("SigninRecord");
        if (sr == null)
            throw new Exception("Service Error: Invalid Sign in request");

        Token token = sr.token; //new Token(sr.requestToken, sr.requestSecret);
        Verifier verifier = new Verifier(verifierToken);
        OAuthService service = oAuthService;
        Token aToken = service.getAccessToken(token, verifier);
        OAuthRequest request = new OAuthRequest(Verb.GET, this.getProfileDetailsRequestString(sr.provider));

        service.signRequest(aToken, request);

        Response response = request.send();
        System.out.println("Response code: " + response.getCode());
        if (response.getCode() != 200) {
            System.out.println("Failed to verify: http code: " + response.getCode());
            throw new Exception("OAuth verified failed - http code: " + response.getCode());
        }
        String xml = response.getBody();
        System.out.println("Response body: " + xml);
        return XMLUtils.getStudentFromLinkedInResponse(xml);

    }

    private OAuthService getService(String callback) {
        if (oAuthService != null) return oAuthService;

        String key = null;
        String secret = null;
        String url = null;

        key = APIKeys.LINKEDIN_API;
        secret = APIKeys.LINKEDIN_SECRET;
        url = LinkedinAuthenticateUrl;

        OAuthService service = new ServiceBuilder().provider(LinkedInApi.class)
                .apiKey(key).apiSecret(secret).callback(callback).build();
        oAuthService = service;
        return service;
    }

    private String getProfileDetailsRequestString(String provider) {
        //add your code to format the string per provider
        //done only for linked for now
        return "http://api.linkedin.com/v1/people/~:(id,first-name,last-name,location,projects,educations,skills)";
    }

    private String getProfileRequestString(String provider) {
        //add your code to format the string per provider
        //done only for linked for now
        return "http://api.linkedin.com/v1/people/~:(id,first-name,last-name)";
    }
}
