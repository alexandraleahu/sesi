package ro.infoiasi.wad.sesi.server.students;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.students.StudentService;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.server.applications.InternshipApplicationDeserializer;
import ro.infoiasi.wad.sesi.server.progressdetails.InternshipProgressDetailsDeserializer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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
    public String saveStudent(Student student) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static final String RESOURCE_PATH = "students";
}