package ro.infoiasi.wad.sesi.server.util;


import com.hp.hpl.jena.query.*;
import org.apache.jena.atlas.web.auth.ServiceAuthenticator;
import ro.infoiasi.wad.sesi.core.model.*;

public class SparqlService {
    final static String serviceEndpoint = "http://localhost:5820/sesi/query/";
    private static String programmingLanguage = "http://rdf.freebase.com/ns/computer.programming_language";
    private static String technology = "http://rdf.freebase.com/ns/computer.software";

    public OntologyExtraInfo getOntologyExtraInfo(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?seeAlso where { ")
                .append("<").append(uri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(uri).append(">").append(" rdfs:seeAlso ?seeAlso . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        OntologyExtraInfo extraInfo = new OntologyExtraInfo();
        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                extraInfo.setName(solution.getLiteral("name").getString());
                extraInfo.setInfoUrl(solution.getLiteral("seeAlso").getString());
                extraInfo.setOntologyUri(uri);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return extraInfo;
    }

    public static TechnicalSkill getTechnicalSkill(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?technologyUsed ?level where { ")
                .append("<").append(uri).append(">").append(" sesiSchema:technologyUsed ?technologyUsed . ")
                .append("<").append(uri).append(">").append(" sesiSchema:level ?level . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                TechnicalSkill technicalSkill = new TechnicalSkill();
                QuerySolution solution = rs.nextSolution();
                KnowledgeLevel level = getLevelFromUri(solution.getResource("level").getURI());
                technicalSkill.setLevel(level);

                String technologyUri = solution.getResource("technologyUsed").getURI();
                String type = getTechnologyType(technologyUri);
                if (type.equals(programmingLanguage)) {
                    ProgrammingLanguage programmingLanguage = getProgrammingLanguage(technologyUri);
                    technicalSkill.setProgrammingLanguage(programmingLanguage);
                }
                if (type.equals(technology)) {
                    Technology technology = getTechnology(technologyUri);
                    technicalSkill.setTechnology(technology);
                }
                return technicalSkill;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    private static String getTechnologyType(String technologyUri) {
        StringBuilder typeQuery = new StringBuilder()
                .append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?type where {<").append(technologyUri).append("> rdf:type ?type .}");
        Query query = QueryFactory.create(typeQuery.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));
        try {
            ResultSet rs = qe.execSelect();
            while (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                if (solution.getResource("type").getURI().equals(programmingLanguage) || solution.getResource("type").getURI().equals(technology))
                    return solution.getResource("type").getURI();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    private static KnowledgeLevel getLevelFromUri(String leveluri) {
        String[] parts = leveluri.split("/");
        String level = parts[parts.length - 1];
        return KnowledgeLevel.valueOf(level);

    }

    private static ProgrammingLanguage getProgrammingLanguage(String programmingLanguageUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?seeAlso where { ")
                .append("<").append(programmingLanguageUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(programmingLanguageUri).append(">").append(" rdfs:seeAlso ?seeAlso . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
                QuerySolution solution = rs.nextSolution();
                String seeAlso = solution.getLiteral("seeAlso").getString();
                String name = solution.getLiteral("name").getString();
                programmingLanguage.setOntologyUri(programmingLanguageUri);
                programmingLanguage.setName(name);
                programmingLanguage.setInfoUrl(seeAlso);
                return programmingLanguage;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    private static Technology getTechnology(String technologyUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?description ?programmingLanguageUsed ?seeAlso where { ")
                .append("<").append(technologyUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(technologyUri).append(">").append(" sesiSchema:description ?description . ")
                .append("<").append(technologyUri).append(">").append(" sesiSchema:programmingLanguageUsed ?programmingLanguageUsed . ")
                .append("<").append(technologyUri).append(">").append(" rdfs:seeAlso ?seeAlso . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                Technology technology = new Technology();
                QuerySolution solution = rs.nextSolution();
                String seeAlso = solution.getLiteral("seeAlso").getString();
                String name = solution.getLiteral("name").getString();
                String description = solution.getLiteral("description").getString();
                ProgrammingLanguage programmingLanguage = getProgrammingLanguage(solution.getResource("programmingLanguageUsed").getURI());
                Technology usedTech = getTechnology(solution.getResource("technologyUsed").getURI());
                technology.addTechnology(usedTech);
                technology.setOntologyUri(technologyUri);
                technology.setName(name);
                technology.setDescription(description);
                technology.setInfoUrl(seeAlso);
                technology.addProgrammingLanguage(programmingLanguage);
                return technology;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    public Studies getStudies(String studiesUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?yearsOfStudy ?degree ?faculty where { ")
                .append("<").append(studiesUri).append(">").append(" sesiSchema:yearOfStudy ?yearOfStudy . ")
                .append("<").append(studiesUri).append(">").append(" sesiSchema:degree ?degree . ")
                .append("<").append(studiesUri).append(">").append(" sesiSchema:studyFaculty ?studyFaculty . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                Studies studies = new Studies();
                QuerySolution solution = rs.nextSolution();
                Integer yearOfStudy = solution.getLiteral("yearOfStudy").getInt();
                String degreeUri = solution.getLiteral("degree").getString();
                Degree degree = getDegree(degreeUri);
                String facultyUri = solution.getLiteral("faculty").getString();
                Faculty faculty = getFaculty(facultyUri);
                studies.setDegree(degree);
                studies.setYearOfStudy(yearOfStudy);
                studies.setFaculty(faculty);
                return studies;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    private Faculty getFaculty(String facultyUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?university where { ")
                .append("<").append(facultyUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(facultyUri).append(">").append(" sesiSchema:university ?university . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                String name = solution.getLiteral("name").getString();
                String universityUri = solution.getResource("university").getURI();
                University university = getUniversity(universityUri);
                Faculty faculty = new Faculty();
                faculty.setName(name);
                faculty.setUniversity(university);
                return faculty;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    private University getUniversity(String universityUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?label ?siteUrl ?seeAlso ?city where { ")
                .append("<").append(universityUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(universityUri).append(">").append(" rdfs:label ?label . ")
                .append("<").append(universityUri).append(">").append(" sesiSchema:siteUrl ?siteUrl . ")
                .append("<").append(universityUri).append(">").append(" rdfs:seeAlso ?seeAlso . ")
                .append("<").append(universityUri).append(">").append(" sesiSchema:inCity ?city . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                String name = solution.getLiteral("name").getString();
                String label = solution.getLiteral("label").getString();
                String siteUrl = solution.getLiteral("siteUrl").getString();
                String seeAlso = solution.getLiteral("siteUrl").getString();
                String cityUri = solution.getResource("city").getURI();
                City city = getCity(cityUri);
                University university = new University();
                university.setName(name);
                university.setLabel(label);
                university.setOntologyUri(universityUri);
                university.setSiteUrl(siteUrl);
                university.setInfoUrl(seeAlso);
                university.setCity(city);
                return university;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }


    public StudentProject getStudentProject(String projectUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?label ?description ?programmingLanguageUsed where { ")
                .append("<").append(projectUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(projectUri).append(">").append(" rdfs:label ?label . ")
                .append("<").append(projectUri).append(">").append(" sesiSchema:description ?description . ")
                .append("<").append(projectUri).append(">").append(" sesiSchema:programmingLanguageUsed ?programmingLanguageUsed . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                String name = solution.getLiteral("name").getString();
                String label = solution.getLiteral("label").getString();
                String description = solution.getLiteral("description").getString();
                String programmingLanguageUri = solution.getResource("programmingLanguageUsed").getURI();
                ProgrammingLanguage programmingLanguage = getProgrammingLanguage(programmingLanguageUri);
                StudentProject project = new StudentProject();
                project.setName(name);
                project.setDescription(description);
                project.addProgrammingLanguage(programmingLanguage);
                return project;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    private City getCity(String cityUri) {
        OntologyExtraInfo extraInfo = getOntologyExtraInfo(cityUri);
        City city = new City();
        city.setOntologyUri(cityUri);
        city.setName(extraInfo.getName());
        city.setInfoUrl(extraInfo.getInfoUrl());
        return city;
    }

    private Degree getDegree(String degreeUri) {
        OntologyExtraInfo extraInfo = getOntologyExtraInfo(degreeUri);
        Degree degree = new Degree();
        degree.setInfoUrl(extraInfo.getInfoUrl());
        degree.setName(extraInfo.getName());
        degree.setOntologyUri(degreeUri);
        return degree;
    }

    public static void main(String[] args) {

    }

    public Object[] getStatus(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?comment where { ")
                .append("<").append(uri).append(">").append(" rdfs:comment ?comment . } ");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                String comment = solution.getLiteral("comment").getString();
                String[] split = uri.split("/");
                String name = split[split.length - 1];
                StudentInternshipRelation.Status status = null;

                if (name.equals("accepted")) status = StudentInternshipRelation.Status.accepted;
                if (name.equals("rejected")) status = StudentInternshipRelation.Status.rejected;
                if (name.equals("pending")) status = StudentInternshipRelation.Status.pending;
                if (name.equals("inProgress")) status = StudentInternshipRelation.Status.inProgress;
                if (name.equals("finished")) status = StudentInternshipRelation.Status.finished;
                return new Object[]{comment, status};
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    public String getIDFromURI(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?id where { ")
                .append("<").append(uri).append(">").append(" sesiSchema:id ?id . } ");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                return solution.getLiteral("id").getString();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    public Teacher getTeacher(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?title ?name ?siteUrl ?faculty where { ")
                .append("<").append(uri).append(">").append(" sesiSchema:title ?title . ")
                .append("<").append(uri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(uri).append(">").append(" sesiSchema:siteUrl ?siteUrl . ")
                .append("<").append(uri).append(">").append(" sesiSchema:isTeacherOf ?faculty . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = QueryExecutionFactory.sparqlService(serviceEndpoint, query, new ServiceAuthenticator("admin", "admin".toCharArray()));

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                String name = solution.getLiteral("name").getString();
                String title = solution.getLiteral("title").getString();
                String facultyUri = solution.getResource("faculty").getURI();
                Faculty faculty = getFaculty(facultyUri);
                String siteUrl = solution.getLiteral("siteUrl").getString();

                Teacher teacher = new Teacher();
                teacher.setName(name);
                teacher.setTitle(title);
                teacher.setSiteUrl(siteUrl);
                teacher.setFaculty(faculty);

                return teacher;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }
}
