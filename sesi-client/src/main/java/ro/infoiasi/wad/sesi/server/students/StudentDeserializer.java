package ro.infoiasi.wad.sesi.server.students;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import ro.infoiasi.wad.sesi.core.model.Student;
import ro.infoiasi.wad.sesi.core.model.StudentProject;
import ro.infoiasi.wad.sesi.core.model.Studies;
import ro.infoiasi.wad.sesi.core.model.TechnicalSkill;
import ro.infoiasi.wad.sesi.server.deserializerinterfaces.ResourceDeserializer;
import ro.infoiasi.wad.sesi.server.sparqlservice.SparqlService;

import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.DEFAULT_JENA_LANG;

public class StudentDeserializer implements ResourceDeserializer<Student> {
    @Override
    public Student deserialize(OntModel m, String id) {
        SparqlService sparqlService = new SparqlService();
        Student student = new Student();
        student.setId(id);

        Resource studentResource = m.getOntResource(SESI_OBJECTS_NS + id);
        if (studentResource != null) {
            // name
            Statement statement = m.getProperty(studentResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));
            if (statement != null) {
                student.setName(statement.getLiteral().getString());
            }
    
            //description
            statement = m.getProperty(studentResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, DESCRIPTION_PROP));
            if (statement != null) {
                student.setDescription(statement.getLiteral().getString());
            }
    
            //general skills
            List<String> generalSkills = Lists.newArrayList();
            StmtIterator stmtIterator = studentResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, GENERAL_SKILL_PROP));
            while (stmtIterator.hasNext()) {
                Statement nextStatement = stmtIterator.nextStatement();
                if (nextStatement != null) {
                    generalSkills.add(nextStatement.getLiteral().getString());
                }
            }
            student.setGeneralSkills(generalSkills);
    
            //technical skills
            List<TechnicalSkill> preferredTechnicalSkills = Lists.newArrayList();
            stmtIterator = studentResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, TECHNICAL_SKILL_PROP));
            while (stmtIterator.hasNext()) {
                Statement nextStatement = stmtIterator.nextStatement();
                if (nextStatement != null) {
                    TechnicalSkill acquiredSkill = sparqlService.getTechnicalSkill(nextStatement.getResource().getURI());
                    preferredTechnicalSkills.add(acquiredSkill);
                }
            }
            student.setTechnicalSkills(preferredTechnicalSkills);
    
            //studies
            statement = m.getProperty(studentResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, HAS_STUDIES_PROP));
            if (statement != null) {
                Studies studies = sparqlService.getStudies(statement.getResource().getURI());
                student.setStudies(studies);
            }
    
            //worked on project
            List<StudentProject> studentProjects = Lists.newArrayList();
            stmtIterator = studentResource.listProperties(ResourceFactory.createProperty(SESI_SCHEMA_NS, WORKED_ON_PROJECT_PROP));
            while (stmtIterator.hasNext()) {
                Statement nextStatement = stmtIterator.nextStatement();
                if (nextStatement != null) {
                    StudentProject project = sparqlService.getStudentProject(nextStatement.getResource().getURI());
                    studentProjects.add(project);
                }
            }
            student.setProjects(studentProjects);
        }
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

    public static void main(String[] args) {
        String s = "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n" +
                "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n" +
                "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n" +
                "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n" +
                "@prefix stardog: <tag:stardog:api:> .\n" +
                "\n" +
                "<http://www.infoiasi.ro/wad/objects/sesi/ionpopescu> a owl:NamedIndividual , <http://www.infoiasi.ro/wad/schemas/sesi/Student> ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/name> \"Ion Popescu\"^^xsd:string ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/description> \"Ambitious student, looking to work mainly with Java technologies\" ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/id> \"ionpopescu\"^^xsd:string ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/sesiUrl> \"/students/ionpopescu\" ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/generalSkill> \"Team player\"^^xsd:string ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/workedOnProject> <http://www.infoiasi.ro/wad/objects/sesi/005> ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/technicalSkill> <http://www.infoiasi.ro/wad/objects/sesi/Java-Intermediate> ;\n" +
                "\t<http://www.infoiasi.ro/wad/schemas/sesi/hasStudies> <http://www.infoiasi.ro/wad/objects/sesi/studies007> .\n";

        OntModel m = ModelFactory.createOntologyModel();
        m.read(new StringReader(s), SESI_SCHEMA_NS, DEFAULT_JENA_LANG);

        StudentDeserializer d = new StudentDeserializer();
        d.deserialize(m, "ionpopescu");
    }
}
