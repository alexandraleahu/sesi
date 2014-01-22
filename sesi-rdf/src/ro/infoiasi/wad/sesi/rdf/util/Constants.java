package ro.infoiasi.wad.sesi.rdf.util;

import com.complexible.common.rdf.model.Values;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.OWL;

public class Constants {

    private Constants() {}

    public static final int ID_LENGTH = 4;
    public static final String SESI_SCHEMA_NS = "http://www.infoiasi.ro/wad/schemas/sesi/";
    public static final String SESI_OBJECTS_NS = "http://www.infoiasi.ro/wad/objects/sesi/";
    public static final String FREEBASE_NS = "http://rdf.freebase.com/ns/";

    public static final String INTERNSHIP_CLASS = "Internship";
    public static final String APPLICATION_CLASS = "InternshipApplication";
    public static final String PROGRESS_DETAILS_CLASS = "InternshipProgressDetails";
    public static final String STUDENT_CLASS = "Student";
    public static final String COMPANY_CLASS = "SoftwareCompany";
    public static final String CITY_CLASS = "location.citytown";
    public static final String CURRENCY_CLASS = "finance.currency";
    public static final String SOFTWARE_SKILL_CLASS = "SoftwareSkill";
    public static final String PROGRAMMING_LANG_CLASS = "computer.programming_language";
    public static final String SOFTWARE_CLASS = "computer.software";
    public static final String UNIVERSITY_CLASS = "education.university";
    public static final String FACULTY_CLASS = "Faculty";
    public static final String DEGREE_CLASS = "education.educational_degree";
    public static final String STUDENT_PROJECT_CLASS = "StudentProject";
    public static final String STUDIES_CLASS= "AcademicStudies";
    public static final String TEACHER_CLASS = "Teacher";

    public static final String ID_PROP = "id";
    public static final String NAME_PROP = "name";
    public static final String DESCRIPTION_PROP = "description";
    public static final String SESI_URL_PROP = "sesiUrl";

    public static final String ACQUIRED_TECHNICAL_PROP = "acquiredTechnicalSkill";
    public static final String ACQUIRED_GENERAL_PROP = "acquiredGeneralSkill";
    public static final String PREFERRED_GENERAL_PROP = "preferredGeneralSkill";
    public static final String PREFERRED_TECHNICAL_PROP = "preferredTechnicalSkill";
    public static final String LEVEL_PROP = "level";
    public static final String PROGRAMMING_USED_PROP = "programmingLanguageUsed";
    public static final String TECHNOLOGY_USED_PROP = "technologyUsed";

    public static final String CATEGORY_PROP = "category";
    public static final String START_DATE_PROP = "startDate";
    public static final String END_DATE_PROP = "endDate";

    public static final String CITY_PROP = "inCity";
    public static final String RELOCATION_PROP = "offersRelocation";

    public static final String SALARY_PROP = "offersSalary";
    public static final String CURRENCY_PROP = "hasCurrency";
    public static final String SALARY_VALUE_PROP = "numericalValue";


    public static final String OPENINGS_PROP = "openingsCount";

    public static final String PROGRESS_PROP = "progressDetails";
    public static final String PUBLISHED_BY_PROP = "publishedByCompany";
    public static final String HAS_INTERNSHIP_APPLICATION_PROP = "hasInternshipApplication";

    public static final String WORKED_ON_PROJECT_PROP = "workedOnProject";
    public static final String TECHNICAL_SKILL_PROP = "technicalSkill";
    public static final String HAS_STUDIES_PROP = "hasStudies";
    public static final String GENERAL_SKILL_PROP = "generalSkill";
    public static final String UNIVERSITY_PROP = "university";
    public static final String FACULTY_PROP = "studyFaculty";
    public static final String YEAR_OF_STUDY_PROP = "yearOfStudy";
    public static final String ENROLLED_STUDENT_PROP = "enrolledStudent";
    public static final String DEGREE_PROP = "degree";
    public static final String DEVELOPED_BY_PROP = "developedBy";

    public static final String IS_ACTIVE_PROP = "isActive";
    public static final String SITE_URL_PROP = "siteUrl";


    public static final String CANDIDATE_PROP = "candidate";
    public static final String APPLICATION_INTERNSHIP_PROP = "applicationInternship";
    public static final String STATUS_PROP = "status";
    public static final String FEEDBACK_PROP = "feedback";

    public static final String TEACHER_MENTOR_PROP = "associateInternshipTeacher";
    public static final String ATTENDED_INTERNSHIP_PROP = "attendedInternship";
    public static final String ATTENDEE_STUDENT_PROP = "attendeeStudent";

    public static final String INITIAL_FEEDBACK = "Your application was submitted into the system.";
    public static final String INITIAL_ATTENDANCE_FEEDBACK = "The student has just started the internship.";

    public static final String IS_TEACHER_OF_PROP = "isTeacherOf";
    public static final String TITLE_PROP = "title";

    public static final String HAS_APPLICATION_PROP = "hasApplication";
    public static final String HAS_COMPANY_PROGRESS_DETAILS_PROP = "hasCompanyProgressDetails";

    public static final URI OWL_NAMED_INDIVIDUAL = Values.uri(OWL.NAMESPACE, "NamedIndividual");



}
