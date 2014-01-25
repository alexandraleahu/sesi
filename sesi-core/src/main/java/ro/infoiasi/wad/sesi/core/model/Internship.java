package ro.infoiasi.wad.sesi.core.model;

import ro.infoiasi.wad.sesi.core.util.HasDescription;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement // used for XML/JSON (de)serialization
public class Internship implements Resource, Event {

    private Company company;
    private String name;
    private String description;
    private City city;
    private Date startDate;
    private Date endDate;
    private int openings;
    private List<String> acquiredGeneralSkills;
    private List<TechnicalSkill> acquiredTechnicalSkills;
    private List<String> preferredGeneralSkills;
    private List<TechnicalSkill> preferredTechnicalSkills;
    private boolean offeringRelocation;
    private String id;
    private Category category;
    private Currency salaryCurrency;
    private double salaryValue;
    private int applicationsCount;
    private Date publishedAt;

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getApplicationsCount() {
        return applicationsCount;
    }

    public void setApplicationsCount(int applicationsCount) {
        this.applicationsCount = applicationsCount;
    }

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
        final StringBuilder sb = new StringBuilder("Internship{");
        sb.append("company='").append(company).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", city=").append(city);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", openings=").append(openings);
        sb.append(", acquiredGeneralSkills=").append(acquiredGeneralSkills);
        sb.append(", acquiredTechnicalSkills=").append(acquiredTechnicalSkills);
        sb.append(", preferredGeneralSkills=").append(preferredGeneralSkills);
        sb.append(", preferredTechnicalSkills=").append(preferredTechnicalSkills);
        sb.append(", offeringRelocation=").append(offeringRelocation);
        sb.append(", id='").append(id).append('\'');
        sb.append(", category=").append(category);
        sb.append(", salaryCurrency=").append(salaryCurrency);
        sb.append(", salaryValue=").append(salaryValue);
        sb.append(", applicationsCount=").append(applicationsCount);
        sb.append(", publishedAt=").append(publishedAt);
        sb.append('}');
        return sb.toString();
    }

    public Currency getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(Currency salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public double getSalaryValue() {
        return salaryValue;
    }

    public void setSalaryValue(double salaryValue) {
        this.salaryValue = salaryValue;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

            Map<String, Category> mappings = new HashMap<String, Category>();
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
    }

}
