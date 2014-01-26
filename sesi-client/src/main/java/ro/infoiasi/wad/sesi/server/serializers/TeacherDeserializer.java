package ro.infoiasi.wad.sesi.server.serializers;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import ro.infoiasi.wad.sesi.core.model.Faculty;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.server.util.SparqlService;

import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class TeacherDeserializer implements Deserializer<Teacher> {
    @Override
    public Teacher deserialize(OntModel m, String id) {
        SparqlService sparqlService = new SparqlService();
        Teacher teacher = new Teacher();
        teacher.setId(id);

        Resource teacherResource = m.getOntResource(SESI_OBJECTS_NS + id);
        // name
        Statement statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));
        teacher.setName(statement.getLiteral().getString());

        //title
        statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, TITLE_PROP));
        teacher.setTitle(statement.getLiteral().getString());

        //siteurl
        statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, SITE_URL_PROP));
        teacher.setSiteUrl(statement.getLiteral().getString());

        //faculty
        statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, IS_TEACHER_OF_PROP));
        String facultyUri = statement.getResource().getURI();
        Faculty faculty = sparqlService.getFaculty(facultyUri);
        teacher.setFaculty(faculty);

        return teacher;
    }

    public List<Teacher> deserialize(OntModel m) {
        List<Teacher> teachers = Lists.newArrayList();
        ResIterator resIterator = m.listResourcesWithProperty(ResourceFactory.createProperty(SESI_SCHEMA_NS, ID_PROP));
        while (resIterator.hasNext()) {
            Resource resource = resIterator.nextResource();
            String[] parts = resource.getURI().split("/");
            teachers.add(deserialize(m, parts[parts.length - 1]));
        }
        return teachers;
    }
}
