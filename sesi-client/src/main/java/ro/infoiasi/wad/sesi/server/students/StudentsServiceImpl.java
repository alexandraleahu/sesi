package ro.infoiasi.wad.sesi.server.students;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import ro.infoiasi.wad.sesi.client.students.StudentsService;
import ro.infoiasi.wad.sesi.core.model.Internship;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.server.applications.InternshipApplicationDeserializer;
import ro.infoiasi.wad.sesi.server.internships.InternshipDeserializer;
import ro.infoiasi.wad.sesi.server.progressdetails.InternshipProgressDetailsDeserializer;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;

public class StudentsServiceImpl extends RemoteServiceServlet implements StudentsService {
    @Override
    public Student getStudentById(String studentId) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL)
                .path(RESOURCE_PATH)
                .path(studentId);

        Response response = target.request(DEFAULT_ACCEPT_RDF_TYPE)
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }
        String rdfAnswer = response
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
    public boolean updateStudent(Student student) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(student.getId());
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE)
                .put(Entity.entity(student, MediaType.APPLICATION_XML_TYPE));
        int status = response.getStatus();
        client.close();
        return status == Response.Status.OK.getStatusCode();
    }

    @Override
    public List<Internship> getRecommendedInternships(String studentId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(studentId).path("recommendedInternships");

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<Internship> details = new InternshipDeserializer().deserialize(m);
        client.close();
        return details;
    }

    @Override
    public boolean registerStudent(String username, String password, String name) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);
        Form form = new Form();
        form.param("username", username);
        form.param("password", password);
        form.param("name", name);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return false;
        }
        return true;
    }

}