package ro.infoiasi.wad.sesi.server.sparqlservice;


import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.jena.SDJenaFactory;
import com.complexible.stardog.reasoning.api.ReasoningType;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import org.apache.jena.atlas.web.auth.ServiceAuthenticator;
import ro.infoiasi.wad.sesi.client.reports.NumericRestriction;
import ro.infoiasi.wad.sesi.client.reports.ReportBean;
import ro.infoiasi.wad.sesi.core.model.*;
import ro.infoiasi.wad.sesi.server.reports.ReportQueryBuilder;
import ro.infoiasi.wad.sesi.server.reports.ResultSetToReportResultList;
import ro.infoiasi.wad.sesi.shared.ComparisonOperator;
import ro.infoiasi.wad.sesi.shared.ReportResult;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SparqlService {
    private static final  String serviceEndpoint = "http://localhost:5820/sesi/query/";
    private static final  String programmingLanguage = "http://rdf.freebase.com/ns/computer.programming_language";
    private static final  String technology = "http://rdf.freebase.com/ns/computer.software";

    public List<ReportResult> getReportResults(String sparqlQuery) {
        List<ReportResult> resultMultimap = null;
        Query query = QueryFactory.create(sparqlQuery);
        // setting the reasoning
        try {
            Connection con = ConnectionConfiguration
                    .to("sesi")
                    .server("http://localhost:5820")
                    .credentials("admin", "admin")
                    .reasoning(ReasoningType.SL)
                    .connect();

            Model model = SDJenaFactory.createModel(con);
            QueryExecution qe  = QueryExecutionFactory.create(query, model);

            if (qe != null) {

                ResultSet resultSet = qe.execSelect();

                resultMultimap = new ResultSetToReportResultList().apply(resultSet);
                qe.close();
                model.close();


            }
        } catch (StardogException e1) {
                e1.printStackTrace();
        }

        return resultMultimap;
    }

    private QueryExecution getQueryExecution(Query query) {


        return QueryExecutionFactory.sparqlService(serviceEndpoint, query,
                                    new ServiceAuthenticator("admin", "admin".toCharArray()));

    }

    public OntologyExtraInfo getOntologyExtraInfo(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?seeAlso where { ")
                .append("optional {").append("<").append(uri).append(">").append(" sesiSchema:name ?name . }")
                .append("optional {").append("<").append(uri).append(">").append(" rdfs:seeAlso ?seeAlso . }}");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

        OntologyExtraInfo extraInfo = new OntologyExtraInfo();
        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                Literal name = solution.getLiteral("name");
                if (name != null) {

                    extraInfo.setName(name.getString());
                }
                Literal seeAlso = solution.getLiteral("seeAlso");
                if (seeAlso != null) {

                    extraInfo.setInfoUrl(seeAlso.getString());
                }
                extraInfo.setOntologyUri(uri);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return extraInfo;
    }

    public  TechnicalSkill getTechnicalSkill(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?technologyUsed ?level where { ")
                .append("<").append(uri).append(">").append(" ?prop ?technologyUsed . ")
                .append("<").append(uri).append(">").append(" sesiSchema:level ?level . ")
                .append("filter (?prop = sesiSchema:technologyUsed || ?prop = sesiSchema:programmingLanguageUsed) }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

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

    private  String getTechnologyType(String technologyUri) {
        StringBuilder typeQuery = new StringBuilder()
                .append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?type where {<").append(technologyUri).append("> rdf:type ?type .}");
        Query query = QueryFactory.create(typeQuery.toString());
        QueryExecution qe = getQueryExecution(query);
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

    private  KnowledgeLevel getLevelFromUri(String leveluri) {
        String[] parts = leveluri.split("/");
        String level = parts[parts.length - 1];
        return KnowledgeLevel.valueOf(level);

    }

    private  ProgrammingLanguage getProgrammingLanguage(String programmingLanguageUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?seeAlso where { ")
                .append("<").append(programmingLanguageUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(programmingLanguageUri).append(">").append(" rdfs:seeAlso ?seeAlso . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

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

    private  Technology getTechnology(String technologyUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?description ?programmingLanguageUsed ?technologyUsed ?seeAlso where { ")
                .append("<").append(technologyUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(technologyUri).append(">").append(" sesiSchema:description ?description . ")
                .append("<").append(technologyUri).append(">").append(" rdfs:seeAlso ?seeAlso . ")
                .append(" optional {<").append(technologyUri).append(">").append(" sesiSchema:programmingLanguageUsed ?programmingLanguageUsed . ")
                .append("} optional {<").append(technologyUri).append(">").append(" sesiSchema:technologyUsed ?technologyUsed . } }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                Technology technology = new Technology();
                QuerySolution solution = rs.nextSolution();
                String seeAlso = solution.getLiteral("seeAlso").getString();
                String name = solution.getLiteral("name").getString();
                String description = solution.getLiteral("description").getString();
                Resource programmingLanguageResource = solution.getResource("programmingLanguageUsed");
                if (programmingLanguageResource != null) {

                    ProgrammingLanguage programmingLanguage = getProgrammingLanguage(programmingLanguageResource.getURI());
                    technology.addProgrammingLanguage(programmingLanguage);
                }
                Resource technologyUsed = solution.getResource("technologyUsed");
                if (technologyUsed != null) {

                    Technology usedTech = getTechnology(technologyUsed.getURI());
                    technology.addTechnology(usedTech);
                }
                technology.setOntologyUri(technologyUri);
                technology.setName(name);
                technology.setDescription(description);
                technology.setInfoUrl(seeAlso);

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
                .append("select ?yearOfStudy ?degree ?faculty where { ")
                .append("<").append(studiesUri).append(">").append(" sesiSchema:yearOfStudy ?yearOfStudy . ")
                .append("<").append(studiesUri).append(">").append(" sesiSchema:degree ?degree . ")
                .append("<").append(studiesUri).append(">").append(" sesiSchema:studyFaculty ?faculty . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                Studies studies = new Studies();
                QuerySolution solution = rs.nextSolution();
                Integer yearOfStudy = solution.getLiteral("yearOfStudy").getInt();
                String degreeUri = solution.getResource("degree").getURI();
                Degree degree = getDegree(degreeUri);
                String facultyUri = solution.getResource("faculty").getURI();
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

    public Faculty getFaculty(String facultyUri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name ?university ?infoUrl where { ")
                .append("<").append(facultyUri).append(">").append(" sesiSchema:name ?name . ")
                .append("<").append(facultyUri).append(">").append(" sesiSchema:university ?university . ")
                .append("optional {<").append(facultyUri).append(">").append(" rdfs:seeAlso ?infoUrl. }}");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                String name = solution.getLiteral("name").getString();
                String universityUri = solution.getResource("university").getURI();
                Literal url = solution.getLiteral("infoUrl");
                Faculty faculty = new Faculty();
                if (url != null) {

                    String infoUrl = url.getString();
                    faculty.setInfoUrl(infoUrl);
                }
                University university = getUniversity(universityUri);
                faculty.setName(name);
                faculty.setUniversity(university);
                faculty.setOntologyUri(facultyUri);
                return faculty;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    public List<String> getAllNamesOfType(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?name  where { ")
                .append(" [] rdf:type ")
                .append(String.format("<%s> ;", uri))
                .append(" sesiSchema:name ?name . }");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

        List<String> names = Lists.newArrayList();
        try {
            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution solution = rs.nextSolution();
                String name = solution.getLiteral("name").getString();
                names.add(name);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return names;
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
        QueryExecution qe = getQueryExecution(query);

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
        QueryExecution qe = getQueryExecution(query);

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
        ReportBean bean = new ReportBean();

        bean.setStudentInternshipRelation(ReportBean.StudentInternshipRelationType.Applications);

        bean.setCompanyNames(Lists.newArrayList("VirtualComp", "comp2"));
        bean.setFacultyNames(Lists.newArrayList("Faculty Of Computer Science"));
        bean.setStatuses(Lists.newArrayList(StudentInternshipRelation.Status.accepted.toString(),
                StudentInternshipRelation.Status.pending.toString()));
        bean.setResourceType(ReportBean.MainResourceType.Internships);

        NumericRestriction numericRestriction = new NumericRestriction();
        numericRestriction.setLimit(1);
        numericRestriction.setOp(ComparisonOperator.ge);

        bean.setNumericRestriction(numericRestriction);

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2013, Calendar.MARCH, 13, 19, 0, 0);
        bean.setStartDate(calendar.getTime());

        calendar.set(2014, Calendar.DECEMBER, 13, 19, 0, 0);
        bean.setEndDate(calendar.getTime());

        String query = ReportQueryBuilder.buildQuery(bean);

        System.out.println(query);
        System.out.println(new SparqlService().getReportResults(query));
    }

    public Object[] getStatus(String uri) {
        StringBuilder builder = new StringBuilder();
        builder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ")
                .append("PREFIX sesiSchema: <http://www.infoiasi.ro/wad/schemas/sesi/> ")
                .append("select ?comment where { ")
                .append("<").append(uri).append(">").append(" rdfs:comment ?comment . } ");
        Query query = QueryFactory.create(builder.toString());
        QueryExecution qe = getQueryExecution(query);

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
        QueryExecution qe = getQueryExecution(query);

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
        QueryExecution qe = getQueryExecution(query);

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
