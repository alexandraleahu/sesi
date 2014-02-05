package ro.infoiasi.wad.sesi.server.students;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.authentication.SigninRecord;
import ro.infoiasi.wad.sesi.client.students.StudentService;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.server.applications.InternshipApplicationDeserializer;
import ro.infoiasi.wad.sesi.server.progressdetails.InternshipProgressDetailsDeserializer;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;

public class StudentServiceImpl extends RemoteServiceServlet implements StudentService {
    @Override
    public Student getStudentById(String studentId) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH)
                .path(studentId);

        Invocation invocation = target.request()
                .accept(DEFAULT_ACCEPT_RDF_TYPE)
                .buildGet();

        String rdfAnswer = invocation.invoke()
                .readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        Student student = new StudentDeserializer().deserialize(m, studentId);
        client.close();
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<Student> students = new StudentDeserializer().deserialize(m);
        client.close();
        return students;
    }

    @Override
    public List<InternshipApplication> getStudentApplications(String studentId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(studentId).path("applications");

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<InternshipApplication> applications = new InternshipApplicationDeserializer().deserialize(m);
        client.close();
        return applications;
    }

    @Override
    public List<InternshipProgressDetails> getStudentInternshipProgressDetails(String studentId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(studentId).path("internshipProgressDetails");

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<InternshipProgressDetails> details = new InternshipProgressDetailsDeserializer().deserialize(m);
        client.close();
        return details;
    }

    @Override
    public String updateStudent(Student student) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean registerStudent(String username, String password) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return false;
        }
        return true;
    }

    @Override
    public Student getStudentProfile(String studentId) {
        HttpSession session = this.getThreadLocalRequest().getSession(true);
        SigninRecord signingRecord = (SigninRecord) session.getAttribute("SigninRecord");

        return StudentLinkedInProfileService.getStudentProfile(studentId, signingRecord);
    }


    public static final String RESOURCE_PATH = "students";
}