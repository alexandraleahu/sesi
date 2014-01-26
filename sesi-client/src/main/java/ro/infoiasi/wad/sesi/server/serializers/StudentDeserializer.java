package ro.infoiasi.wad.sesi.server.serializers;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.StudentProject;
import ro.infoiasi.wad.sesi.core.model.Studies;
import ro.infoiasi.wad.sesi.core.model.TechnicalSkill;
import ro.infoiasi.wad.sesi.server.util.SparqlService;

import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class StudentDeserializer implements ResourceDeserializer<Student> {
    @Override
    public Student deserialize(OntModel m, String id) {
        SparqlService sparqlService = new SparqlService();
        Student student = new Student();
        student.setId(id);

        Resource studentResource = m.getOntResource(SESI_OBJECTS_NS + id);
        // name
        Statement statement = m.getProperty(studentResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));
        student.setName(statement.getLiteral().getString());

        //description
        statement = m.getProperty(studentResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, DESCRIPTION_PROP));
        student.setDescription(statement.getLiteral().getString());

        //general skkills
        List<String> generalSkills = Lists.newArrayList();
        StmtIterator stmtIterator = studentResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, TECHNICAL_SKILL_PROP));
        while (stmtIterator.hasNext()) {
            Statement nextStatement = stmtIterator.nextStatement();
            generalSkills.add(nextStatement.getLiteral().getString());
        }
        student.setGeneralSkills(generalSkills);

        //technical skills
        List<TechnicalSkill> preferredTechnicalSkills = Lists.newArrayList();
        stmtIterator = studentResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, TECHNICAL_SKILL_PROP));
        while (stmtIterator.hasNext()) {
            Statement nextStatement = stmtIterator.nextStatement();
            TechnicalSkill acquiredSkill = sparqlService.getTechnicalSkill(nextStatement.getResource().getURI());
            preferredTechnicalSkills.add(acquiredSkill);
        }
        student.setTechnicalSkills(preferredTechnicalSkills);

        //studies
        statement = m.getProperty(studentResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, HAS_STUDIES_PROP));
        Studies studies = sparqlService.getStudies(statement.getResource().getURI());
        student.setStudies(studies);

        //worked on project
        List<StudentProject> studentProjects = Lists.newArrayList();
        stmtIterator = studentResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, WORKED_ON_PROJECT_PROP));
        while (stmtIterator.hasNext()) {
            Statement nextStatement = stmtIterator.nextStatement();
            StudentProject project = sparqlService.getStudentProject(nextStatement.getResource().getURI());
            studentProjects.add(project);
        }
        student.setProjects(studentProjects);

        return student;

    }

    public List<Student> deserialize(OntModel m) {
        List<Student> students = Lists.newArrayList();
        ResIterator resIterator = m.listResourcesWithProperty(ResourceFactory.createProperty(SESI_SCHEMA_NS, ID_PROP));
        while (resIterator.hasNext()) {
            Resource resource = resIterator.nextResource();
            String[] parts = resource.getURI().split("/");
            students.add(deserialize(m, parts[parts.length - 1]));
        }
        return students;
    }
}
