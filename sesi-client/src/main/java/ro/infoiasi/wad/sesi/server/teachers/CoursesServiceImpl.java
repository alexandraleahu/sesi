package ro.infoiasi.wad.sesi.server.teachers;

import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.vocabulary.DCTerms;
import ro.infoiasi.wad.sesi.client.teachers.CoursesService;
import ro.infoiasi.wad.sesi.core.model.Course;
import ro.infoiasi.wad.sesi.core.util.Constants;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.StringReader;
import java.util.List;

import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.DEFAULT_JENA_LANG;
import static ro.infoiasi.wad.sesi.server.util.ServiceConstants.TURTLE;


public class CoursesServiceImpl extends RemoteServiceServlet implements CoursesService {

    @Override
    public List<Course> getCoursesFromInfoiasiSite(List<String> coursesUrls) {

        // reading structured content from the site and parsing
        final Client client = ClientBuilder.newClient();
        List<Course> courses = Lists.newArrayList();

        for (String courseUrl : coursesUrls) {
            WebTarget target = client.target(Constants.ANY23_URL)
                    .path("ttl")
                    .path(courseUrl);

            Invocation invocation = target.request()
                    .accept(TURTLE)
                    .buildGet();


            String rdfAnswer = invocation.invoke().readEntity(String.class);

            Model m = ModelFactory.createDefaultModel();
            m.read(new StringReader(rdfAnswer),null, DEFAULT_JENA_LANG);

            Course course = new Course();
            course.setInfoUrl(courseUrl);
            NodeIterator titleIt = m.listObjectsOfProperty(DCTerms.title);
            if (titleIt.hasNext()) {
                String rawTitle = titleIt.next().asLiteral().getString();
                course.setName(rawTitle.substring(0, rawTitle.indexOf("|")));
            }
            courses.add(course);
        }

        client.close();
        return courses;
    }
}