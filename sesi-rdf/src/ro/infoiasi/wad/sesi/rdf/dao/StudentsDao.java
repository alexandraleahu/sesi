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
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.rio.RDFFormat;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.rdf.connection.SesiConnectionPool;
import ro.infoiasi.wad.sesi.rdf.util.ResultIOUtils;

import java.util.Arrays;
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

    public String getAllStudentApplications(String id, RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("describe ?application  ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Student ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:submittedApplication ?application . ")
                    .append("}");

            GraphQuery graphQuery = con.graph(sb.toString());
            graphQuery.parameter("id", id);
            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getStudentApplicationsCount(String id, TupleQueryResultFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("select ?application (COUNT(?application) as ?applicationCount) ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Student ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:submittedApplication ?application . ")
                    .append("}  GROUP BY ?application");

            SelectQuery selectQuery = con.select(sb.toString());
            selectQuery.parameter("id", id);
            return ResultIOUtils.getSparqlResultsFromSelectQuery(selectQuery, format);

        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    public String getStudentInternshipsProgressDetails(String id, RDFFormat format) throws Exception {
        ReasoningConnection con = connectionPool.getConnection();
        try {
            StringBuilder sb = new StringBuilder()
                    .append("describe ?progressDetails ")
                    .append("where {")
                    .append("[] rdf:type sesiSchema:Student ; ")
                    .append("sesiSchema:id ?id ; ")
                    .append("sesiSchema:attendedInternshipProgress ?progressDetails . ")
                    .append("}");

            GraphQuery graphQuery = con.graph(sb.toString());
            graphQuery.parameter("id", id);
            return ResultIOUtils.writeGraphResultsToString(graphQuery, format);

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
            URI description = Values.uri(SESI_SCHEMA_NS, DESCRIPTION_PROP);

            adder.statement(newStudent, RDFS.LABEL, Values.literal(student.getName()));
            adder.statement(newStudent, name, Values.literal(student.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(newStudent, id, Values.literal(student.getId(), StardogValueFactory.XSD.STRING));
            adder.statement(newStudent, uri, Values.literal(student.getRelativeUri(), StardogValueFactory.XSD.STRING));
            adder.statement(newStudent, description, Values.literal(student.getDescription(), StardogValueFactory.XSD.STRING));

            //add projects
            for (StudentProject studentProject: student.getProjects()) {
                URI projectResource = Values.uri(SESI_OBJECTS_NS,  RandomStringUtils.randomAlphanumeric(ID_LENGTH));
                adder.statement(projectResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
                adder.statement(projectResource, RDF.TYPE, Values.uri(SESI_SCHEMA_NS, STUDENT_PROJECT_CLASS));
                adder.statement(projectResource, RDFS.LABEL, Values.literal(studentProject.getLabel()));
                adder.statement(projectResource, Values.uri(SESI_SCHEMA_NS, DESCRIPTION_PROP), Values.literal(studentProject.getDescription()), StardogValueFactory.XSD.STRING);

                URI programmingLanguageURI = Values.uri(studentProject.getProgrammingLanguage().getOntologyUri());
                adder.statement(programmingLanguageURI, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
                adder.statement(programmingLanguageURI, RDF.TYPE, Values.uri(FREEBASE_NS, PROGRAMMING_LANG_CLASS));
                adder.statement(programmingLanguageURI, RDFS.LABEL, Values.literal(studentProject.getProgrammingLanguage().getName()));
                adder.statement(programmingLanguageURI, RDFS.SEEALSO, Values.literal(studentProject.getProgrammingLanguage().getInfoUrl(), StardogValueFactory.XSD.ANYURI));
                URI programmingLanguageProperty = Values.uri(SESI_SCHEMA_NS, TECHNOLOGY_USED_PROP);
                adder.statement(projectResource, programmingLanguageProperty, programmingLanguageURI);
                adder.statement(projectResource, Values.uri(SESI_SCHEMA_NS, DEVELOPED_BY_PROP), newStudent);
                adder.statement(newStudent, Values.uri(SESI_SCHEMA_NS, WORKED_ON_PROJECT_PROP), projectResource);
            }
            //add studies
            Studies studies = student.getStudies();
            //adding the city
            Faculty faculty = studies.getFaculty();
            University university = faculty.getUniversity();
            City universityCity = university.getCity();

            URI cityResource = Values.uri(universityCity.getOntologyUri());
            adder.statement(cityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(cityResource, RDF.TYPE, Values.uri(FREEBASE_NS, CITY_CLASS));
            adder.statement(cityResource, RDFS.LABEL, Values.literal(universityCity.getName()));
            adder.statement(cityResource, RDFS.SEEALSO, Values.literal(universityCity.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

            //add the university
            URI universityResource = Values.uri(university.getOntologyUri());
            URI site = Values.uri(SESI_SCHEMA_NS, SITE_URL_PROP);

            adder.statement(universityResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(universityResource, RDF.TYPE, Values.uri(FREEBASE_NS, UNIVERSITY_CLASS));
            adder.statement(universityResource, RDFS.LABEL, Values.literal(university.getName()));
            adder.statement(universityResource, name, Values.literal(university.getName(), StardogValueFactory.XSD.STRING));
            adder.statement(universityResource, site, Values.literal(university.getSiteUrl(), StardogValueFactory.XSD.ANYURI));
            adder.statement(universityResource, RDFS.LITERAL, Values.literal(university.getSiteUrl(), StardogValueFactory.XSD.STRING));
            adder.statement(universityResource, RDFS.SEEALSO, Values.literal(university.getInfoUrl(), StardogValueFactory.XSD.ANYURI));
            adder.statement(universityResource, cityProp, cityResource);


            //add the faculty
            URI facultyResource = Values.uri(SESI_OBJECTS_NS, faculty.getName().replace(' ', '_'));
            adder.statement(facultyResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(facultyResource, RDF.TYPE, Values.uri(SESI_SCHEMA_NS, FACULTY_CLASS));
            adder.statement(facultyResource, Values.uri(SESI_SCHEMA_NS, UNIVERSITY_PROP), Values.uri(university.getOntologyUri()));
            adder.statement(facultyResource, RDFS.LABEL, Values.literal(faculty.getName()));
            adder.statement(facultyResource, name, Values.literal(faculty.getName(), StardogValueFactory.XSD.STRING));


            //add the studies resource
            URI studiesResource = Values.uri(SESI_OBJECTS_NS, RandomStringUtils.randomAlphanumeric(ID_LENGTH));
            adder.statement(studiesResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(studiesResource, RDF.TYPE, Values.uri(SESI_SCHEMA_NS, STUDIES_CLASS));
            adder.statement(studiesResource, Values.uri(SESI_SCHEMA_NS, FACULTY_PROP), facultyResource);
            adder.statement(studiesResource, Values.uri(SESI_SCHEMA_NS, YEAR_OF_STUDY_PROP), Values.literal(studies.getYearOfStudy().toString(), StardogValueFactory.XSD.NON_NEGATIVE_INTEGER));
            adder.statement(studiesResource, Values.uri(SESI_SCHEMA_NS, ENROLLED_STUDENT_PROP), newStudent);
            //add studies degree
            URI degreeURI = Values.uri(studies.getDegree().getOntologyUri());
            adder.statement(degreeURI, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(degreeURI, RDF.TYPE, Values.uri(FREEBASE_NS, DEGREE_CLASS));
            adder.statement(degreeURI, RDFS.LABEL, Values.literal(studies.getDegree().getName()));
            adder.statement(degreeURI, RDFS.SEEALSO, Values.literal(studies.getDegree().getInfoUrl(), StardogValueFactory.XSD.ANYURI));
            adder.statement(studiesResource, Values.uri(SESI_SCHEMA_NS, DEGREE_PROP), degreeURI);

            adder.statement(newStudent, Values.uri(SESI_SCHEMA_NS, HAS_STUDIES_PROP), studiesResource);

            //add general skills
            for (String generalSkill : student.getGeneralSkills()) {
                URI generalSkillProp = Values.uri(SESI_SCHEMA_NS, GENERAL_SKILL_PROP);
                adder.statement(newStudent, generalSkillProp, Values.literal(generalSkill));
            }
            //add technical skills
            if(student.getTechnicalSkills() != null) {
                URI technicalSkillsProp =  Values.uri(SESI_SCHEMA_NS, TECHNICAL_SKILL_PROP);
                addTechnicalSkills(student.getTechnicalSkills(), newStudent, adder, technicalSkillsProp);
            }
            con.commit();
            return student.getRelativeUri();
        } finally {
            connectionPool.releaseConnection(con);
        }
    }

    private void addTechnicalSkills(List<TechnicalSkill> technicalSkills, Resource newStudent, Adder adder, URI technicalSkillsProp) throws StardogException {

        for (TechnicalSkill technicalSkill : technicalSkills) {

            // creating the programming language / technology (if it exists, the old triples won't be affected)
            StringBuilder sb = new StringBuilder();
            ProgrammingLanguage programmingLanguage = technicalSkill.getProgrammingLanguage();
            URI technologyUri = null;
            URI languageOrSoftwareUri = null;

            if (programmingLanguage != null) {
                technologyUri = Values.uri(programmingLanguage.getOntologyUri());
                adder.statement(technologyUri, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
                adder.statement(technologyUri, RDF.TYPE, Values.uri(FREEBASE_NS, PROGRAMMING_LANG_CLASS));
                adder.statement(technologyUri, RDFS.LABEL, Values.literal(programmingLanguage.getName()));
                adder.statement(technologyUri, RDFS.SEEALSO, Values.literal(programmingLanguage.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

                languageOrSoftwareUri = Values.uri(SESI_SCHEMA_NS, PROGRAMMING_USED_PROP);
                sb.append(programmingLanguage.getName().replace(' ', '_'));

            } else {
                Technology software = technicalSkill.getTechnology();
                technologyUri = Values.uri(software.getOntologyUri());
                adder.statement(technologyUri, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
                adder.statement(technologyUri, RDF.TYPE, Values.uri(FREEBASE_NS, SOFTWARE_CLASS));
                adder.statement(technologyUri, RDFS.LABEL, Values.literal(software.getName()));
                adder.statement(technologyUri, RDFS.SEEALSO, Values.literal(software.getInfoUrl(), StardogValueFactory.XSD.ANYURI));

                languageOrSoftwareUri = Values.uri(SESI_SCHEMA_NS, TECHNOLOGY_USED_PROP);
                sb.append(software.getName().replace(' ', '_'));

            }
            sb.append(technicalSkill.getLevel().toString());

            URI softwareSkillResource = Values.uri(SESI_OBJECTS_NS, sb.toString());
            URI level = Values.uri(SESI_SCHEMA_NS, technicalSkill.getLevel().toString());
            URI hasLevel = Values.uri(SESI_SCHEMA_NS, LEVEL_PROP);

            // composing the software skill
            adder.statement(softwareSkillResource, RDF.TYPE, OWL_NAMED_INDIVIDUAL);
            adder.statement(softwareSkillResource, RDF.TYPE, Values.uri(SESI_SCHEMA_NS, SOFTWARE_SKILL_CLASS));

            adder.statement(softwareSkillResource, languageOrSoftwareUri, technologyUri);
            adder.statement(softwareSkillResource, hasLevel, level);

            // adding the software skills to the technicalSkills
            adder.statement(newStudent, technicalSkillsProp, softwareSkillResource);
        }
    }


    public static void main(String[] args) {
        StudentsDao dao = new StudentsDao();

        try {
//            System.out.println(dao.getStudent("ionpopescu", RDFFormat.TURTLE));

            System.out.println("applications " + dao.getAllStudentApplications("ionpopescu", RDFFormat.JSONLD));
            System.out.println("\n applications count " + dao.getStudentApplicationsCount("ionpopescu", TupleQueryResultFormat.JSON));
            //add student
//            Student student = newStudent("Gigel");
//            dao.createStudent(student);
//            System.out.println("\n am inserat + " + student.getId());
//            System.out.println(dao.getStudent(student.getId(), RDFFormat.TURTLE));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static Student newStudent(String name)  {
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
        university.setName("Some Random University");
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

        ProgrammingLanguage java = new ProgrammingLanguage();
        java.setName("Java");
        java.setInfoUrl("http://www.freebase.com/m/07sbkfb");
        java.setOntologyUri("http://rdf.freebase.com/ns/m.07sbkfb");

        TechnicalSkill javaIntermediate = new TechnicalSkill();
        javaIntermediate.setLevel(KnowledgeLevel.Intermediate);
        javaIntermediate.setProgrammingLanguage(java);

        Technology android = new Technology();
        android.setName("Android");
        android.setInfoUrl("http://www.freebase.com/m/02wxtgw");
        android.setOntologyUri("http://rdf.freebase.com/ns/m.02wxtgw");

        TechnicalSkill androidAdvanced = new TechnicalSkill();
        androidAdvanced.setLevel(KnowledgeLevel.Advanced);
        androidAdvanced.setTechnology(android);

        student.setTechnicalSkills(Arrays.asList(javaIntermediate, androidAdvanced));

        student.setStudies(studies);

        StudentProject project = new StudentProject();
        project.setName("SESI PROJECT");
        project.setLabel("sesiproj");
        project.setDescription("internship application for students");
        project.setStudentID(student.getId());
        project.setProgrammingLanguage(java);

        student.setProjects(Lists.newArrayList(project));

        return student;
    }

    @Override
    public String getOntClassName() {
        return STUDENT_CLASS;
    }
}
