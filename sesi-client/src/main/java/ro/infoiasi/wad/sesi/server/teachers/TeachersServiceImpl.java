package ro.infoiasi.wad.sesi.server.teachers;

import com.google.common.base.Joiner;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import ro.infoiasi.wad.sesi.client.teachers.TeachersService;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.server.progressdetails.InternshipProgressDetailsDeserializer;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.SESI_SCHEMA_NS;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.*;

public class TeachersServiceImpl extends RemoteServiceServlet implements TeachersService {
    @Override
    public Teacher getTeacherById(String teacherID) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(teacherID);

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        Teacher teacher = new TeacherDeserializer().deserialize(m, teacherID);
        client.close();
        return teacher;
    }

    @Override
    public Teacher importTeacherProfile(String url) {

        // reading structured content from the site and parsing
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(Constants.ANY23_URL)
                                 .path("ttl")
                                 .path(url);

        Invocation invocation = target.request()
                                    .accept(TURTLE)
                                    .buildGet();


        String rdfAnswer = invocation.invoke().readEntity(String.class);

        Model m = ModelFactory.createDefaultModel();
        m.read(new StringReader(rdfAnswer), null,  DEFAULT_JENA_LANG);

        Teacher teacher = new TeacherDeserializer().deserializeFromInfoiasiSite(m, url);
        client.close();

        return teacher;

    }

    @Override
    public List<Teacher> getAllTeachers() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH);

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<Teacher> teachers = new TeacherDeserializer().deserialize(m);
        client.close();
        return teachers;
    }

    @Override
    public List<InternshipProgressDetails> getProgressDetailsForTeacher(String teacherId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(teacherId).path("internshipProgressDetails");

        Invocation invocation = target.request().accept(DEFAULT_ACCEPT_RDF_TYPE).buildGet();

        String rdfAnswer = invocation.invoke().readEntity(String.class);

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(rdfAnswer), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        List<InternshipProgressDetails> progressDetails = new InternshipProgressDetailsDeserializer().deserialize(m);
        client.close();
        return progressDetails;
    }


    public String updateTeacher(Teacher teacher) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SESI_BASE_URL).path(RESOURCE_PATH).path(teacher.getId());
        Form form = new Form();
        form.param("name", teacher.getName());
        form.param("monitoringInternships", Joiner.on(',').join(teacher.getMonitoringInternships()));
        form.param("faculty", teacher.getFaculty().toString());
        form.param("siteUrl", teacher.getSiteUrl());
        form.param("courses", Joiner.on(",").join(teacher.getCourses()));
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return null;
        }
        return response.getEntity().toString();
    }

    @Override
    public boolean registerTeacher(String username, String password, String name) {
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