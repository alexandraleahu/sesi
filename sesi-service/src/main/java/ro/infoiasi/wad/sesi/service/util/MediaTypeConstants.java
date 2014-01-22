package ro.infoiasi.wad.sesi.service.util;

import com.google.common.collect.Maps;
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
    public static final MediaType DEFAULT_RDF_TYPE = MediaType.APPLICATION_JSON_TYPE;

    private static Map<MediaType, RDFFormat> buildMimeTypeToRdfFormatMappings() {

        Map<MediaType, RDFFormat> mappings = Maps.newHashMap();
        mappings.put(MediaType.APPLICATION_JSON_TYPE, RDFFormat.JSONLD);
        mappings.put(RDFXML, RDFFormat.RDFXML);
        mappings.put(TURTLE, RDFFormat.TURTLE);

        return mappings;

    }

    private static final Map<MediaType, RDFFormat> MAPPINGS = buildMimeTypeToRdfFormatMappings();

    public static RDFFormat getRDFFormat(MediaType mediaType) {

        if (MAPPINGS.get(mediaType) != null) {

            return MAPPINGS.get(mediaType);
        }

        return MAPPINGS.get(DEFAULT_RDF_TYPE);
    }

    public static MediaTypeAndRdfFormat getBestReturnTypes(List<MediaType> acceptableMediaTypes) {

        RDFFormat rdfReturnType = null;
        MediaType returnContentType = null;

        if (acceptableMediaTypes.contains(MediaType.APPLICATION_JSON_TYPE)) {
            return new MediaTypeAndRdfFormat(MediaType.APPLICATION_JSON_TYPE, MAPPINGS.get(MediaType.APPLICATION_JSON_TYPE));

        } else if (
                acceptableMediaTypes.contains(MediaTypeConstants.RDFXML)) {

            return new MediaTypeAndRdfFormat(MediaTypeConstants.RDFXML, MAPPINGS.get(MediaTypeConstants.RDFXML));

        } else if (acceptableMediaTypes.contains(MediaTypeConstants.TURTLE)) {
            return new MediaTypeAndRdfFormat(MediaTypeConstants.TURTLE, MAPPINGS.get(MediaTypeConstants.TURTLE));

        } else {
            return new MediaTypeAndRdfFormat(DEFAULT_RDF_TYPE, MAPPINGS.get(DEFAULT_RDF_TYPE));

        }

    }

    public static class MediaTypeAndRdfFormat {

        private MediaType mediaType;
        private RDFFormat rdfFormat;

        public MediaType getMediaType() {
            return mediaType;
        }

        public void setMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
        }

        public RDFFormat getRdfFormat() {
            return rdfFormat;
        }

        public void setRdfFormat(RDFFormat rdfFormat) {
            this.rdfFormat = rdfFormat;
        }

        public MediaTypeAndRdfFormat(MediaType mediaType, RDFFormat rdfFormat) {
            this.mediaType = mediaType;
            this.rdfFormat = rdfFormat;
        }
    }
}
