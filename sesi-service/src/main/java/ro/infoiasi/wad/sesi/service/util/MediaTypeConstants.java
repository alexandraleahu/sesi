package ro.infoiasi.wad.sesi.service.util;

import com.google.common.collect.Maps;
import info.aduna.lang.FileFormat;
import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.rio.RDFFormat;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

public class MediaTypeConstants {

    private MediaTypeConstants() {}

    public static final MediaType TURTLE = new MediaType("text", "turtle");
    public static final String TURTLE_STRING = "text/turtle";
    public static final MediaType RDFXML = new MediaType("application", "rdf+xml");
    public static final String RDFXML_STRING = "application/rdf+xml";
    public static final MediaType JSONLD = new MediaType("application", "ld+json");
    public static final String JSON_LD_STRING = "application/ld+json";

    public static final MediaType SPARQL_XML = new MediaType("application", "sparql-results+xml");
    public static final String SPARQL_XML_STRING = "application/sparql-results+xml";
    public static final MediaType SPARQL_JSON = new MediaType("application", "sparql-results+json");
    public static final String SPARQL_JSON_STRING = "application/sparql-results+json";

    public static final MediaType DEFAULT_RDF_TYPE = TURTLE;
    public static final MediaType DEFAULT_SPARQL_TYPE = SPARQL_JSON;
    private static Map<MediaType, RDFFormat> buildMimeTypeToRdfFormatMappings() {

        Map<MediaType, RDFFormat> mappings = Maps.newHashMap();
        mappings.put(JSONLD, RDFFormat.JSONLD);
        mappings.put(RDFXML, RDFFormat.RDFXML);
        mappings.put(TURTLE, RDFFormat.TURTLE);


        return mappings;

    }

    private static Map<MediaType, TupleQueryResultFormat> buildMimeTypeToSparqlFormatMappings() {

        Map<MediaType, TupleQueryResultFormat> mappings = Maps.newHashMap();
        mappings.put(SPARQL_JSON, TupleQueryResultFormat.JSON);
        mappings.put(SPARQL_XML, TupleQueryResultFormat.SPARQL);


        return mappings;

    }

    private static final Map<MediaType, RDFFormat> RDF_MAPPINGS = buildMimeTypeToRdfFormatMappings();
    private static final Map<MediaType, TupleQueryResultFormat> SPARQL_MAPPINGS = buildMimeTypeToSparqlFormatMappings();


    public static RDFFormat getRdfFormat(MediaType mediaType) {
        return RDF_MAPPINGS.get(mediaType);

    }

    public static TupleQueryResultFormat getSparqlFormat(MediaType mediaType) {
        return SPARQL_MAPPINGS.get(mediaType);

    }


    public static MediaTypeAndRdfFormat<RDFFormat> getBestRdfReturnTypes(List<MediaType> acceptableMediaTypes) {

        if (acceptableMediaTypes.contains(JSONLD)) {
            return new MediaTypeAndRdfFormat<>(JSONLD, RDF_MAPPINGS.get(JSONLD));
        } else if (
                acceptableMediaTypes.contains(RDFXML)) {
            return new MediaTypeAndRdfFormat<>(RDFXML, RDF_MAPPINGS.get(RDFXML));
        } else if (acceptableMediaTypes.contains(TURTLE)) {
            return new MediaTypeAndRdfFormat<>(TURTLE, RDF_MAPPINGS.get(TURTLE));
        } else {
            return new MediaTypeAndRdfFormat<>(DEFAULT_RDF_TYPE, RDF_MAPPINGS.get(DEFAULT_RDF_TYPE));

        }


    }
    public static MediaTypeAndRdfFormat<TupleQueryResultFormat> getBestSparqlReturnTypes(List<MediaType> acceptableMediaTypes) {


        if (acceptableMediaTypes.contains(SPARQL_JSON)) {
            return new MediaTypeAndRdfFormat<>(SPARQL_JSON, SPARQL_MAPPINGS.get(SPARQL_JSON));
        } else if (acceptableMediaTypes.contains(SPARQL_XML)) {
            return new MediaTypeAndRdfFormat<>(SPARQL_XML, SPARQL_MAPPINGS.get(SPARQL_XML));
        } else {
            return new MediaTypeAndRdfFormat<>(DEFAULT_SPARQL_TYPE, SPARQL_MAPPINGS.get(DEFAULT_SPARQL_TYPE));

        }


    }

    public static class MediaTypeAndRdfFormat<T extends FileFormat> {

        private MediaType mediaType;
        private T rdfFormat;

        public MediaType getMediaType() {
            return mediaType;
        }

        public void setMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
        }

        public T getRdfFormat() {
            return rdfFormat;
        }

        public void setRdfFormat(T rdfFormat) {
            this.rdfFormat = rdfFormat;
        }

        public MediaTypeAndRdfFormat(MediaType mediaType, T rdfFormat) {
            this.mediaType = mediaType;
            this.rdfFormat = rdfFormat;
        }
    }
}
