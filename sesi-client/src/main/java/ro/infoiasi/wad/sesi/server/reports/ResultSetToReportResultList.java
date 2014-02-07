package ro.infoiasi.wad.sesi.server.reports;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import ro.infoiasi.wad.sesi.core.model.OntologyExtraInfo;
import ro.infoiasi.wad.sesi.core.model.Resource;
import ro.infoiasi.wad.sesi.core.model.StudentInternshipRelation;
import ro.infoiasi.wad.sesi.shared.BasicResource;
import ro.infoiasi.wad.sesi.shared.ReportResult;

import javax.annotation.Nullable;
import java.util.List;

/**
 * We use a ListMultimap because it allows duplicate values and maintains the order in which the elements were inserted
 */
public class ResultSetToReportResultList implements Function<ResultSet, List<ReportResult>> {

    @Nullable
    @Override
    public List<ReportResult> apply(ResultSet input) {

        List<ReportResult> resultList = Lists.newArrayList();
        Resource[] basicResources = new Resource[3];

        List<String> varNames = Lists.transform(AbstractReportQueryBuilder.FIELD_NAMES, new Function<String, String>() {
            @Nullable
            @Override
            public String apply(String input) {
                return input.substring(1); // removing the "?"
            }
        });

        while (input.hasNext()) {
            QuerySolution querySolution = input.nextSolution();
            ReportResult result = new ReportResult();


            for (int i = 0; i < 3; i++) {

                final String name = querySolution.getLiteral(varNames.get(2 * i))
                        .getString();
                final String sesiUrl = querySolution.getLiteral(varNames.get(2 * i + 1))
                        .getString();

                basicResources[i] = getBasicResource(sesiUrl.substring(sesiUrl.lastIndexOf("/") + 1),
                                                        sesiUrl,
                                                        name,
                                                        name);
            }

            result.setInternshipBasic(basicResources[0]);
            result.setCompanyBasic(basicResources[1]);
            result.setStudentBasic(basicResources[2]);


            // getting the school
            final String schoolName = querySolution.getLiteral(varNames.get(6))
                    .getString();

            result.setSchoolBasic(getOntologyExtraInfo(schoolName,  null));

            // the status and the feedback
            String statusUri = querySolution.get(varNames.get(7))
                    .toString();
            String status = statusUri.substring(statusUri.lastIndexOf("/") + 1);

            String feedback = querySolution.getLiteral(varNames.get(8))
                    .getString();

            result.setStatus(StudentInternshipRelation.Status.valueOf(status));
            result.setFeedback(feedback);

            // check if we have publishedAt or startDate and endDate

            Literal publishedAt = querySolution.getLiteral(AbstractReportQueryBuilder.PUBLISHED_AT_SPARQL_PROP.substring(1));

            if (publishedAt != null) {

                XSDDateTime publishdAtDate = (XSDDateTime) (publishedAt.getValue());
                result.setPublishedAt(publishdAtDate.asCalendar().getTime());
            }   else {

                Literal startDateLiteral = querySolution.getLiteral(AbstractReportQueryBuilder.PERIOD.get(0).substring(1));
                XSDDateTime startDate = (XSDDateTime) (startDateLiteral.getValue());
                result.setStartDate(startDate.asCalendar().getTime());

                Literal endDateLiteral = querySolution.getLiteral(AbstractReportQueryBuilder.PERIOD.get(1).substring(1));
                XSDDateTime endDate = (XSDDateTime) (endDateLiteral.getValue());
                result.setStartDate(endDate.asCalendar().getTime());

            }

            resultList.add(result);
        }
        return resultList;
    }

    private Resource getBasicResource(final String id, final String relativeUri,
                                      final String description, final String name) {

        return new BasicResource(id, relativeUri, description, name);
    }


    private OntologyExtraInfo getOntologyExtraInfo(String name, String infoUrl) {

        OntologyExtraInfo ontologyExtraInfo = new OntologyExtraInfo();
        ontologyExtraInfo.setInfoUrl(infoUrl);
        ontologyExtraInfo.setName(name);
        return ontologyExtraInfo;
    }



}
