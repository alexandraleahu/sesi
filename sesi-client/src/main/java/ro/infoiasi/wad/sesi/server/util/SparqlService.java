package ro.infoiasi.wad.sesi.server.util;


import com.google.common.collect.Lists;
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
        if (level.equals("Intermediate"))
            return KnowledgeLevel.Intermediate;
        if (level.equals("Advanced"))
            return KnowledgeLevel.Advanced;
        if (level.equals("Basic"))
            return KnowledgeLevel.Basic;
        if (level.equals("Proficient"))
            return KnowledgeLevel.Proficient;
        return null;

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

                technology.setOntologyUri(technologyUri);
                technology.setName(name);
                technology.setDescription(description);
                technology.setInfoUrl(seeAlso);
                technology.setProgrammingLanguages(Lists.newArrayList(programmingLanguage.getName()));
                return technology;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            qe.close();
        }
        return null;
    }

    public static void main(String[] args) {
        getTechnicalSkill("http://www.infoiasi.ro/wad/objects/sesi/JavaEEIntermediate");

//        getProgrammingLanguage("http://rdf.freebase.com/ns/m.07657k");
    }
}
