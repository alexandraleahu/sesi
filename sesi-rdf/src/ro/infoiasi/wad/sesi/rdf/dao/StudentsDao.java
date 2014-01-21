package ro.infoiasi.wad.sesi.rdf.dao;

import com.complexible.common.rdf.model.StardogValueFactory;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResourceLinks;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import java.util.List;

import static ro.infoiasi.wad.sesi.rdf.util.Constants.*;

public class StudentsDao implements Dao {
    private final SesiConnectionPool connectionPool = SesiConnectionPool.INSTANCE;

    public String getAllStudents(RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?s where {?s rdf:type sesiSchema:Student .}");

            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getStudent(String id, RDFFormat format) throws Exception {

        ReasoningConnection con = connectionPool.getConnection();
        try {
            GraphQuery graphQuery = con.graph("describe ?s where {?s rdf:type sesiSchema:Student ; sesiSchema:id ?id .}");
            graphQuery.parameter("id", id);
            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getAllStudentApplications(String id) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?application ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Student ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:submittedApplication ?application . ")
                    .append("?application sesiSchema:sesiUrl ?sesiUrl .  ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", id);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "application", "sesiUrl");

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public List<ResourceLinks> getStudentInternshipsProgressDetails(String id) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?progressDetails ?sesiUrl ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Student ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:attendedInternshipProgress ?progressDetails . ")
                    .append("?application sesiSchema:sesiUrl ?sesiUrl .  ")
                    .append("}");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", id);
            return ResultIOUtils.getResourceLinksFromSelectQuery(selectQuery, "progressDetails", "sesiUrl");

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String createStudent(Student student) throws StardogException {
        ReasoningConnection con = connectionPool.getConnection();

        try {
            Resource newStudent = Values.uri(SESI_OBJECTS_NS, student.getId());
            URI studentClass = Values.uri(SESI_SCHEMA_NS, STUDENT_CLASS);

            con.begin();

            Adder adder = con.add();
            adder.statement(newStudent, RDF.TYPE, studentClass);
            adder.statement(newStudent, RDF.TYPE, OWL_NAMED_INDIVIDUAL);

            URI name = Values.uri(SESI_SCHEMA_NS, NAME_PROP);
            URI id = Values.uri(SESI_SCHEMA_NS, ID_PROP);
            URI uri = Values.uri(SESI_SCHEMA_NS, SESI_URL_PROP);
            URI cityProp = Values.uri(SESI_SCHEMA_NS, CITY_PROP);

            adder.statement(newStudent, RDFS.LABEL, Values.literal(student.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(newStudent, name, Values.literal(student.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(newStudent, id, Values.literal(student.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newStudent, uri, Values.literal(student.getRelativeUri(), StardogValueFactory.XSD.STRING));

            //add projects
            for (Technology technology : student.getProjects()) {

            }
            //add studies
            Studies studies = student.getStudies();
            //adding the city
            Faculty faculty = studies.getFaculty();
            University university = faculty.getUniversity();
            City facultyCity = university.getCity();

            URI cityResource = Values.uri(facultyCity.getOntologyUri().toString());
            adder.statement(cityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(cityResource, RDF.TYPE, Values.uri(FREEBASE_NS, CITY_CLASS));
            adder.statement(cityResource, RDFS.LABEL, Values.literal(facultyCity.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(cityResource, RDFS.SEEALSO, Values.uri(facultyCity.getInfoUrl()));

            adder.statement(newStudent, cityProp, cityResource);

            //add the university
            URI universityResource = Values.uri(university.getOntologyUri().toString());
            adder.statement(universityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(universityResource, RDF.TYPE, Values.uri(FREEBASE_NS, UNIVERSITY_CLASS));
            adder.statement(universityResource, RDFS.LABEL, Values.literal(university.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(universityResource, RDFS.LITERAL, Values.literal(university.getSiteUrl(), StardogValueFactory.XSD.STRING));
            adder.statement(universityResource, RDFS.SEEALSO, Values.uri(university.getInfoUrl()));

           //add the faculty
            URI facultyResource = Values.uri(SESI_OBJECTS_NS, FACULTY_PROP);
            adder.statement(facultyResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(facultyResource,  Values.uri(SESI_SCHEMA_NS, UNIVERSITY_PROP), Values.uri(university.getOntologyUri()));

            //add the studies resource
            URI studiesResource = Values.uri(SESI_OBJECTS_NS, STUDIES_CLASS);
            adder.statement(studiesResource, RDFS.LABEL, Values.literal(studies.getLabel()), StardogValueFactory.XSD.STRING);
            adder.statement(studiesResource, Values.uri(SESI_SCHEMA_NS, FACULTY_PROP), facultyResource);
            adder.statement(studiesResource, Values.uri(SESI_SCHEMA_NS, YEAR_OF_STUDY_PROP), Values.literal(studies.getYearOfStudy().toString(), StardogValueFactory.XSD.NON_NEGATIVE_INTEGER));
            adder.statement(studiesResource, Values.uri(SESI_SCHEMA_NS, ENROLLED_STUDENT_PROP), newStudent);


            //add general skills
            for (String generalSkill : student.getGeneralSkills()) {
                URI generalSkillProp = Values.uri(SESI_SCHEMA_NS, GENERAL_SKILL_PROP);
                adder.statement(newStudent, generalSkillProp, Values.literal(generalSkill));
            }
            //add technical skills

            con.commit();
            return student.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public static void main(String[] args) {
        StudentsDao dao = new StudentsDao();

        try {
            //add student
            Student student = newStudent("Gigel");
            dao.createStudent(student);
            System.out.println("\n am inserat + " + student.getId());
            System.out.println(dao.getStudent(student.getId(), RDFFormat.TURTLE));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static Student newStudent(String name) {
        Student student = new Student();
        student.setId(RandomStringUtils.randomAlphanumeric(4));
        student.setName(name);
        student.setGeneralSkills(Lists.newArrayList("funny", "outgoing"));

        City iasi = new City();
        iasi.setOntologyUri("http://rdf.freebase.com/ns/m.01fhg3");
        iasi.setName("Iasi");
        iasi.setInfoUrl("http://www.freebase.com/m/01fhg3");

        University university = new University();
        university.setSiteUrl("http://uaic.ro/uaic/bin/view/Main/WebHome");
        university.setName("Alexandru Ioan Cuza University");
        university.setInfoUrl("http://www.freebase.com/m/0945q0");
        university.setOntologyUri("http://rdf.freebase.com/ns/m.0945q0");
        university.setCity(iasi);
        Faculty faculty = new Faculty();
        faculty.setName("Informatica");
        faculty.setUniversity(university);

        Studies studies = new Studies();
        studies.setName("OC");
        studies.setYearOfStudy(1);
        studies.setFaculty(faculty);
        studies.setLabel("mystudieslabel");
        Degree degree = new Degree();
        degree.setInfoUrl("http://www.freebase.com/m/016t_3");
        degree.setOntologyUri("http://rdf.freebase.com/ns/m.016t_3");
        degree.setName("degree name");
        studies.setDegree(degree);

        student.setStudies(studies);

        return student;
    }

    @Override
    public String getOntClassName() {
        return STUDENT_CLASS;
    }
}
