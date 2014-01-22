package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.City;
import ro.infoiasi.wad.sesi.core.model.Faculty;
import ro.infoiasi.wad.sesi.core.model.Teacher;
import ro.infoiasi.wad.sesi.core.model.University;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import static ro.infoiasi.wad.sesi.rdf.util.Constants.*;

public class TeachersDao implements Dao {

    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getTeacherById(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?t where {?t rdf:type sesiSchema:Teacher ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getAllTeachers(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?t where {?t rdf:type sesiSchema:Teacher .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getAllInternshipProgressDetails(String teacherId, TupleQueryResultFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?details ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Teacher ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:isAssociateInternshipTeacherOf ?details . ")
                    .append("?details sesiSchema:sesiUrl ?sesiUrl . ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", teacherId);
            return ResultIOUtils.getSparqlResultsFromSelectQuery(selectQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String createTeacher(Teacher teacher) throws StardogException {

        ReasoningConnection con = connectionPool.getConnection();

        try {

            Resource newTeacher = Values.uri(SESI_OBJECTS_NS, teacher.getId());
            URI progressDetailsClass = Values.uri(SESI_SCHEMA_NS, getOntClassName());

            con.begin();

            Adder adder = con.add();

            // adding the class and the owl:namedIndividual type
            adder.statement(newTeacher, RDF.TYPE, progressDetailsClass);
            adder.statement(newTeacher, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            // then, the id, sesiUrl and name
            URI ID = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI sesiUrl = Values.uri(SESI_SCHEMA_NS, SESI_URL_PROP);
            URI siteUrl = Values.uri(SESI_SCHEMA_NS, SITE_URL_PROP);
            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);
            URI title = Values.uri(SESI_SCHEMA_NS, TITLE_PROP);

            adder.statement(newTeacher, ID, Values.literal(teacher.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newTeacher, name, Values.literal(teacher.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(newTeacher, sesiUrl, Values.literal(teacher.getRelativeUri()));
            adder.statement(newTeacher, title, Values.literal(teacher.getTitle(), StardogValueFactory.XSD.STRING));
            if (teacher.getSiteUrl() != null) {
                adder.statement(newTeacher, siteUrl, Values.literal(teacher.getSiteUrl(), StardogValueFactory.XSD.ANYURI));
            }

            // adding the faculty property
            Faculty faculty = teacher.getFaculty();
            University university = faculty.getUniversity();

            // adding the city
            City universityCity = university.getCity();

            URI cityResource = Values.uri(universityCity.getOntologyUri());
            URI inCity = Values.uri(SESI_SCHEMA_NS, CITY_PROP);

            adder.statement(cityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(cityResource, RDF.TYPE, Values.uri(FREEBASE_NS, CITY_CLASS));
            adder.statement(cityResource, RDFS.LABEL, Values.literal(universityCity.getName()));
            adder.statement(cityResource, RDFS.SEEALSO, Values.literal(universityCity.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

            //add the university
            URI universityResource = Values.uri(university.getOntologyUri());

            adder.statement(universityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(universityResource, RDF.TYPE, Values.uri(FREEBASE_NS, UNIVERSITY_CLASS));
            adder.statement(universityResource, RDFS.LABEL, Values.literal(university.getName()));
            adder.statement(universityResource, name, Values.literal(university.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(universityResource, siteUrl, Values.literal(university.getSiteUrl(), StardogValueFactory.XSD.ANYURI));
            adder.statement(universityResource, RDFS.SEEALSO, Values.literal(university.getInfoUrl(), StardogValueFactory.XSD.ANYURI));
            adder.statement(universityResource, inCity, cityResource);

            //add the faculty
            URI facultyResource = Values.uri(SESI_OBJECTS_NS, faculty.getName().replace(' ', '_'));
            adder.statement(facultyResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(facultyResource, RDF.TYPE, Values.uri(SESI_SCHEMA_NS, FACULTY_CLASS));
            adder.statement(facultyResource, Values.uri(SESI_SCHEMA_NS, UNIVERSITY_PROP), universityResource);
            adder.statement(facultyResource, RDFS.LABEL, Values.literal(faculty.getName()));
            adder.statement(facultyResource, name, Values.literal(faculty.getName(), StardogValueFactory.XSD.STRING));

            // add the faculty to the teacher
            URI isTeacherOf = Values.uri(SESI_SCHEMA_NS, IS_TEACHER_OF_PROP);
            adder.statement(newTeacher, isTeacherOf, facultyResource);


            con.commit();

            return teacher.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }


    }

    public void deleteTeacher(String teacherId) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();

        try {
            URI teacherUri =
                    Values.uri(SESI_OBJECTS_NS + teacherId);

            // deleting an individual means deleting all statements that have the individual as a subject or as an object
            con.begin();
            con.remove()
                    .statements(teacherUri, null, null)
                    .statements(null, null, teacherUri);
            con.commit();
        } finally {
            connectionPool.releaseConnection(con);
        }

    }


    @Override
    public String getOntClassName() {
        return TEACHER_CLASS;
    }

    public static void main(String[] args) throws Exception {
        TeachersDao dao = new TeachersDao();

//        Teacher teacher = new Teacher();
//        teacher.setTitle("Maths teacher at the Faculty of Computer Science");
//        teacher.setName("Ionel Munteanu");
//        teacher.setId("ionel_munteanu");
//        teacher.setSiteUrl("http://profs.info.uaic.ro/~munteanu");
//
//
//        City iasi = new City();
//
//        iasi.setOntologyUri("http://rdf.freebase.com/ns/m.01fhg3");
//        iasi.setName("Iasi");
//        iasi.setInfoUrl("http://www.freebase.com/m/01fhg3");
//
//
//        University university = new University();
//        university.setSiteUrl("http://uaic.ro/uaic/bin/view/Main/WebHome");
//        university.setName("Al.I.Cuza");
//        university.setInfoUrl("http://www.freebase.com/m/0945q0");
//        university.setOntologyUri("http://rdf.freebase.com/ns/m.0945q0");
//        university.setCity(iasi);
//
//        Faculty faculty = new Faculty();
//        faculty.setName("Faculty Of Computer Science");
//        faculty.setUniversity(university);
//
//        teacher.setFaculty(faculty);
//
//        System.out.println(dao.createTeacher(teacher));

        System.out.println(dao.getTeacherById("ionbarbu", RDFFormat.TURTLE));


    }
}
