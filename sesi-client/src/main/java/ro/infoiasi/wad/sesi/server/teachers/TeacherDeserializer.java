package ro.infoiasi.wad.sesi.server.teachers;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.Resource;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.core.util.Constants;
import ro.infoiasi.wad.sesi.server.deserializerinterfaces.Deserializer;
import ro.infoiasi.wad.sesi.server.sparqlservice.SparqlService;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static ro.infoiasi.wad.sesi.core.util.Constants.*;

public class TeacherDeserializer implements Deserializer<Teacher> {
    @Override
    public Teacher deserialize(OntModel m, String id) {
        SparqlService sparqlService = new SparqlService();
        Teacher teacher = new Teacher();
        teacher.setId(id);

        Resource teacherResource = m.getOntResource(SESI_OBJECTS_NS + id);
        
        if (teacherResource != null) {
            // name
            Statement statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, NAME_PROP));
            if (statement != null) {
                teacher.setName(statement.getLiteral().getString());
            }
    
            //title
            statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, TITLE_PROP));
            if (statement != null) {
                teacher.setTitle(statement.getLiteral().getString());
            }
    
            //siteurl
            statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, SITE_URL_PROP));
            if (statement != null) {
                teacher.setSiteUrl(statement.getLiteral().getString());
            }
    
            //faculty
            statement = m.getProperty(teacherResource, ResourceFactory.createProperty(SESI_SCHEMA_NS, IS_TEACHER_OF_PROP));
            if (statement != null) {
                String facultyUri = statement.getResource().getURI();
                Faculty faculty = sparqlService.getFaculty(facultyUri);
                teacher.setFaculty(faculty);
            }
        }

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

    public Teacher deserializeFromInfoiasiSite(Model m, String url) {

        Teacher teacher = new Teacher();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        NodeIterator nameIt = m.listObjectsOfProperty(ResourceFactory.createProperty(Constants.VCARD_NS, "fn"));
        if (nameIt.hasNext()) {
            teacher.setName(nameIt.next().asLiteral().getString());
        }
        NodeIterator urlIt = m.listObjectsOfProperty(ResourceFactory.createProperty(Constants.VCARD_NS, "url"));
        if (urlIt.hasNext()) {
            teacher.setSiteUrl(urlIt.next().asResource().getURI());
        }

        NodeIterator titleIt = m.listObjectsOfProperty(ResourceFactory.createProperty(Constants.VCARD_NS, "title"));
        if (titleIt.hasNext()) {
            teacher.setTitle(titleIt.next().asLiteral().getString());
        }

        // getting the taught subjects
        NodeIterator coursesIt = m.listObjectsOfProperty(ResourceFactory.createProperty(url, "teaches"));
        List<String> coursesUrls = Lists.newArrayList();

        Any23UrlToRealUrl transform = new Any23UrlToRealUrl(url);
        while (coursesIt.hasNext()) {
            coursesUrls.add(transform.apply(coursesIt.next().asResource().getURI()));
        }

        List<Course> coursesList = new CoursesServiceImpl().getCoursesFromInfoiasiSite(coursesUrls);

        teacher.setCourses(coursesList);

        // right now, hardcoding the faculty to FII
        setFacultyToInfoiasi(teacher);


        return teacher;
    }

    private static class Any23UrlToRealUrl implements Function<String, String> {

        @Nullable
        @Override
        public String apply(String url) {

            String lastPart = url.substring(mainResourceUrl.length() + 1);
            try {
                String firstPart = new URL(url).getAuthority();

                return firstPart + "/" + lastPart;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        private final String mainResourceUrl;

        public Any23UrlToRealUrl(String mainResourceUrl) {
            this.mainResourceUrl = mainResourceUrl;
        }
    }

    private void setFacultyToInfoiasi(Teacher teacher) {
        City iasi = new City();

        iasi.setOntologyUri("http://rdf.freebase.com/ns/m.01fhg3");
        iasi.setName("Iasi");
        iasi.setInfoUrl("http://www.freebase.com/m/01fhg3");

        University university = new University();
        university.setSiteUrl("http://uaic.ro/uaic/bin/view/Main/WebHome");
        university.setName("Al.I.Cuza");
        university.setInfoUrl("http://www.freebase.com/m/0945q0");
        university.setOntologyUri("http://rdf.freebase.com/ns/m.0945q0");
        university.setCity(iasi);

        Faculty faculty = new Faculty();
        faculty.setName("Faculty Of Computer Science");
        faculty.setInfoUrl("http://www.info.uaic.ro/bin/Main/");
        faculty.setUniversity(university);

        teacher.setFaculty(faculty);
    }



}
