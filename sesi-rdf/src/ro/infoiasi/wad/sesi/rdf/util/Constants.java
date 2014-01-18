package ro.infoiasi.wad.sesi.rdf.util;

import com.complexible.common.rdf.model.Values;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.OWL;

public class Constants {

    private Constants() {}

    public static final String SESI_SCHEMA_NS = "http://www.infoiasi.ro/wad/schemas/sesi/";
    public static final String SESI_OBJECTS_NS = "http://www.infoiasi.ro/wad/objects/sesi/";
    public static final String FREEBASE_NS = "http://rdf.freebase.com/ns/";

    public static final String INTERNSHIP_CLASS = "Internship";
    public static final String STUDENT_CLASS = "Student";
    public static final String COMPANY_CLASS = "SoftwareCompany";
    public static final String CITY_CLASS = "location.citytown";

    public static final String ID_PROP = "id";
    public static final String NAME_PROP = "name";
    public static final String DESCRIPTION_PROP = "description";

    public static final String ACQUIRED_TECHNICAL_PROP = "acquiredTechnicalSkill";
    public static final String ACQUIRED_GENERAL_PROP = "acquiredGeneralSkill";
    public static final String PREFERRED_GENERAL_PROP = "preferredGeneralSkill";
    public static final String PREFERRED_TECHNICAL_PROP = "preferredTechnicalSkill";

    public static final String CATEGORY_PROP = "category";
    public static final String START_DATE_PROP = "startDate";
    public static final String END_DATE_PROP = "endDate";

    public static final String CITY_PROP = "inCity";
    public static final String RELOCATION_PROP = "offersRelocation";
    public static final String SALARY_PROP = "offersSalary";
    public static final String OPENINGS_PROP = "openingsCount";

    public static final String PROGRESS_PROP = "progressDetails";
    public static final String PUBLISHED_BY_PROP = "publishedByCompany";
    public static final String HAS_INTERNSHIP_APPLICATION_PROP = "hasInternshipApplication";

    public static final URI OWL_NAMED_INDIVIDUAL = Values.uri(OWL.NAMESPACE, "NamedIndividual");



}
