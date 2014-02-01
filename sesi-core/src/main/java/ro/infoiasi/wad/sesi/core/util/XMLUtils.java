package ro.infoiasi.wad.sesi.core.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.infoiasi.wad.sesi.core.model.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XMLUtils {
    public static User getUserFromLinkedInResponse(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = f.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document d = db.parse(is);

        Element root = d.getDocumentElement();
        NodeList ns1 = root.getElementsByTagName("id");
        String id = ns1.item(0).getTextContent();
        ns1 = root.getElementsByTagName("first-name");
        String firstName = ns1.item(0).getTextContent();
        ns1 = root.getElementsByTagName("last-name");
        String lastName = ns1.item(0).getTextContent();

        String userId = firstName + "." + lastName + id;

        is.close();

        User user = new User();
        user.id = userId;
        user.firstName = firstName;
        user.lastName = lastName;
        return user;
    }
}
