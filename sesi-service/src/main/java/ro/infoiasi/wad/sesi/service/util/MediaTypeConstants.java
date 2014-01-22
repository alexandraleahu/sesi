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
    
    public static final MediaType DEFAULT_RDF_TYPE = MediaType.APPLICATION_JSON_TYPE;

    private static Map<MediaType, FileFormat> buildMimeTypeToRdfFormatMappings() {

        Map<MediaType, FileFormat> mappings = Maps.newHashMap();
        mappings.put(JSONLD, RDFFormat.JSONLD);
        mappings.put(RDFXML, RDFFormat.RDFXML);
        mappings.put(TURTLE, RDFFormat.TURTLE);
        mappings.put(SPARQL_JSON, TupleQueryResultFormat.JSON);
        mappings.put(SPARQL_XML, TupleQueryResultFormat.SPARQL);

        return mappings;

    }

    private static final Map<MediaType, FileFormat> MAPPINGS = buildMimeTypeToRdfFormatMappings();


    public static FileFormat getRdfFormat(MediaType mediaType) {

        if (MAPPINGS.get(mediaType) != null) {

            return MAPPINGS.get(mediaType);
        }

        return MAPPINGS.get(DEFAULT_RDF_TYPE);
    }


    public static MediaTypeAndRdfFormat getBestReturnTypes(List<MediaType> acceptableMediaTypes) {

        RDFFormat rdfReturnType = null;
        MediaType returnContentType = null;

        if (acceptableMediaTypes.contains(JSONLD)) {
            return new MediaTypeAndRdfFormat(JSONLD, MAPPINGS.get(JSONLD));
        } else if (
                acceptableMediaTypes.contains(RDFXML)) {
            return new MediaTypeAndRdfFormat(RDFXML, MAPPINGS.get(RDFXML));
        } else if (acceptableMediaTypes.contains(TURTLE)) {
            return new MediaTypeAndRdfFormat(TURTLE, MAPPINGS.get(TURTLE));
        } else if (acceptableMediaTypes.contains(SPARQL_JSON)) {
            return new MediaTypeAndRdfFormat(SPARQL_JSON, MAPPINGS.get(SPARQL_JSON));
        } else if (acceptableMediaTypes.contains(SPARQL_XML)) {
            return new MediaTypeAndRdfFormat(SPARQL_XML, MAPPINGS.get(SPARQL_XML));
        } else {
            return new MediaTypeAndRdfFormat(DEFAULT_RDF_TYPE, MAPPINGS.get(DEFAULT_RDF_TYPE));

        }


    }

    public static class MediaTypeAndRdfFormat {

        private MediaType mediaType;
        private FileFormat rdfFormat;

        public MediaType getMediaType() {
            return mediaType;
        }

        public void setMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
        }

        public FileFormat getRdfFormat() {
            return rdfFormat;
        }

        public void setRdfFormat(FileFormat rdfFormat) {
            this.rdfFormat = rdfFormat;
        }

        public MediaTypeAndRdfFormat(MediaType mediaType, FileFormat rdfFormat) {
            this.mediaType = mediaType;
            this.rdfFormat = rdfFormat;
        }
    }
}
