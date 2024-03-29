package ro.infoiasi.wad.sesi.server.applications;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import ro.infoiasi.wad.sesi.core.model.InternshipApplication;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;
import ro.infoiasi.wad.sesi.server.deserializerinterfaces.ResourceDeserializer;
import ro.infoiasi.wad.sesi.server.internships.InternshipsServiceImpl;
import ro.infoiasi.wad.sesi.server.sparqlservice.SparqlService;
import ro.infoiasi.wad.sesi.server.students.StudentsServiceImpl;

import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class InternshipApplicationDeserializer implements ResourceDeserializer<InternshipApplication> {
    @Override
    public InternshipApplication deserialize(OntModel m, String id) {
        SparqlService sparqlService = new SparqlService();
        InternshipApplication application = new InternshipApplication();
        application.setId(id);

        Resource applicationResource = m.getOntResource(SESI_OBJECTS_NS + id);
        if (applicationResource != null) {
            //description
            Statement statement = m.getProperty(applicationResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, DESCRIPTION_PROP));
            if (statement != null) {
                application.setFeedback(statement.getLiteral().getString());
            }
    
            //published at
            statement = m.getProperty(applicationResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, PUBLISHED_AT_PROP));
            if (statement != null) {
                XSDDateTime startDate = (XSDDateTime) (statement.getLiteral().getValue());
                application.setPublishedAt(startDate.asCalendar().getTime());
            }
    
            //motivation
            statement = m.getProperty(applicationResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, MOTIVATION_PROP));
            if (statement != null) {
                application.setMotivation(statement.getLiteral().getString());
            }
    
            //status
            statement = m.getProperty(applicationResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, STATUS_PROP));
            if (statement != null) {
                String statusUri = statement.getResource().getURI();
                application.setStatus(StudentInternshipRelation.Status.valueOf(
                        statusUri.substring(statusUri.lastIndexOf("/") + 1)
                ));
            }

            // feedback
            statement = m.getProperty(applicationResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, FEEDBACK_PROP));
            if (statement != null) {
                application.setFeedback(statement.getLiteral().getString());
            }

            //internship
            statement = m.getProperty(applicationResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, APPLICATION_INTERNSHIP_PROP));
            if (statement != null) {
                String internshipID = sparqlService.getIDFromURI(statement.getResource().getURI());
                InternshipsServiceImpl internshipsService = new InternshipsServiceImpl();
                application.setInternship(internshipsService.getInternshipById(internshipID));
            }
    
            //student
            statement = m.getProperty(applicationResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, CANDIDATE_PROP));
            if (statement != null) {
                String studentID = statement.getObject().asResource().getLocalName();
                StudentsServiceImpl studentService = new StudentsServiceImpl();
                application.setStudent(studentService.getStudentById(studentID));
            }
        }
        return application;
    }

    public List<InternshipApplication> deserialize(OntModel m) {
        List<InternshipApplication> internshipApplications = Lists.newArrayList();

        ResIterator resIterator = m.listResourcesWithProperty(ResourceFactory.createProperty(SESI_SCHEMA_NS, ID_PROP));
        while (resIterator.hasNext()) {
            Resource resource = resIterator.nextResource();
            String[] parts = resource.getURI().split("/");
            internshipApplications.add(deserialize(m, parts[parts.length - 1]));
        }
        return internshipApplications;
    }
}
