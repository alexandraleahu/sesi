package ro.infoiasi.wad.sesi.core.model;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import ro.infoiasi.wad.sesi.core.util.HasDescription;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement // used for XML/JSON (de)serialization
public class Internship implements Resource {

    private String companyId;
    private String name;
    private String description;
    private City city;
    private DateTime startDate;
    private DateTime endDate;
    private int openings;
    private List<String> acquiredGeneralSkills;
    private List<TechnicalSkill> acquiredTechnicalSkills;
    private List<String> preferredGeneralSkills;
    private List<TechnicalSkill> preferredTechnicalSkills;
    private boolean offeringRelocation;
    private String id;
    private Category category;
    private int applicantsNo;
    private Salary salary;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRelativeUri() {
        return "/internships/" + getId();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("companyId", companyId)
                .add("name", name)
                .add("description", description)
                .add("city", city)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .add("openings", openings)
                .add("acquiredGeneralSkills", acquiredGeneralSkills)
                .add("acquiredTechnicalSkills", acquiredTechnicalSkills)
                .add("preferredGeneralSkills", preferredGeneralSkills)
                .add("preferredTechnicalSkills", preferredTechnicalSkills)
                .add("offeringRelocation", offeringRelocation)
                .add("id", id)
                .add("category", category)
                .add("applicantsNo", applicantsNo)
                .add("salary", salary)
                .toString();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public int getOpenings() {
        return openings;
    }

    public void setOpenings(int openings) {
        this.openings = openings;
    }

    public List<String> getAcquiredGeneralSkills() {
        return acquiredGeneralSkills;
    }

    public void setAcquiredGeneralSkills(List<String> acquiredGeneralSkills) {
        this.acquiredGeneralSkills = acquiredGeneralSkills;
    }

    public List<TechnicalSkill> getAcquiredTechnicalSkills() {
        return acquiredTechnicalSkills;
    }

    public void setAcquiredTechnicalSkills(List<TechnicalSkill> acquiredTechnicalSkills) {
        this.acquiredTechnicalSkills = acquiredTechnicalSkills;
    }

    public List<String> getPreferredGeneralSkills() {
        return preferredGeneralSkills;
    }

    public void setPreferredGeneralSkills(List<String> preferredGeneralSkills) {
        this.preferredGeneralSkills = preferredGeneralSkills;
    }

    public List<TechnicalSkill> getPreferredTechnicalSkills() {
        return preferredTechnicalSkills;
    }

    public void setPreferredTechnicalSkills(List<TechnicalSkill> preferredTechnicalSkills) {
        this.preferredTechnicalSkills = preferredTechnicalSkills;
    }

    public boolean isOfferingRelocation() {
        return offeringRelocation;
    }

    public void setOfferingRelocation(boolean offeringRelocation) {
        this.offeringRelocation = offeringRelocation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getApplicantsNo() {
        return applicantsNo;
    }

    public void setApplicantsNo(int applicantsNo) {
        this.applicantsNo = applicantsNo;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public static enum Category implements HasDescription {

        WebDev("Web"),
        Mobile("Mobile"),
        Databases("Database Administration"),
        CloudComputing("Cloud Computing and Networking"),
        BusinessSoftware("Business Software"),
        BigData("Big Data"),
        ArtificialIntelligence("Artificial Intelligence"),
        Gaming("Gaming and Graphics"),
        Embedded("Embedded"),
        QualityAssurance("Quality Assurance"),
        Management("IT Project Management and Consulting")
        ;

        private final String description;
        private static final Map<String, Category> FROM_DESCRIPTION = buildDescriptionMappings();

        private static Map<String, Category> buildDescriptionMappings() {

            Map<String, Category> mappings = Maps.newHashMap();
            for (Category category : Category.values()) {
                mappings.put(category.getDescription(), category);
            }

            return mappings;
        }

        public static Category fromDescription(String description) {
            return FROM_DESCRIPTION.get(description);
        }

        Category(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return getDescription();
        }
    }

}
