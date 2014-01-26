package ro.infoiasi.wad.sesi.server.serializers;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import ro.infoiasi.wad.sesi.core.model.InternshipProgressDetails;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.server.rpc.InternshipsServiceImpl;
import ro.infoiasi.wad.sesi.server.rpc.StudentServiceImpl;
import ro.infoiasi.wad.sesi.server.util.SparqlService;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class ProgressDetailsDeserializer implements Deserializer<InternshipProgressDetails> {
    @Override
    public InternshipProgressDetails deserialize(OntModel m, String id) {
        SparqlService sparqlService = new SparqlService();
        InternshipProgressDetails details = new InternshipProgressDetails();
        details.setId(id);

        Resource detailsResource = m.getOntResource(SESI_OBJECTS_NS + id);
        // student
        Statement statement = m.getProperty(detailsResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, ATTENDEE_STUDENT_PROP));
        String studentId = sparqlService.getIDFromURI(statement.getResource().getURI());
        StudentServiceImpl studentService = new StudentServiceImpl();
        details.setStudent(studentService.getStudentById(studentId));

        //internship
        statement = m.getProperty(detailsResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, ATTENDED_INTERNSHIP_PROP));
        String internshipId = sparqlService.getIDFromURI(statement.getResource().getURI());
        InternshipsServiceImpl service = new InternshipsServiceImpl();
        details.setInternship(service.getInternshipById(internshipId));

        //teacher
        statement = m.getProperty(detailsResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, TEACHER_MENTOR_PROP));
        Teacher teacher = sparqlService.getTeacher(statement.getResource().getURI());
        details.setTeacher(teacher);

        //status
        statement = m.getProperty(detailsResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, STATUS_PROP));
        Object[] res = sparqlService.getStatus(statement.getResource().getURI());
        details.setStatus((StudentInternshipRelation.Status) res[1]);

        //company feedback
        statement = m.getProperty(detailsResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, FEEDBACK_PROP));
        details.setCompanyFeedback(statement.getLiteral().getString());

        return details;
    }
}
