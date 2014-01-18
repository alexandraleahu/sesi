package ro.infoiasi.wad.sesi.core.model;

import com.google.common.collect.Maps;
import org.joda.time.Period;
import ro.infoiasi.wad.sesi.core.util.HasDescription;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement // used for XML/JSON (de)serialization
public class Internship implements Resource {

    private Company company;
    private String name;
    private String description;
    private String city;
    private Period period;
    private int openings;
    private String minimumRequirements;
    private List<String> skills;
    private List<String> acquiredSkills;
    private boolean offeringRelocation;
    private String id;
    private Category category;
    private int applicantsNo;
    private int salary;


    public List<String> getAcquiredSkills() {

        return acquiredSkills;
    }

    public void setAcquiredSkills(List<String> acquiredSkills) {
        this.acquiredSkills = acquiredSkills;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getApplicantsNo() {

        return applicantsNo;
    }

    public void setApplicantsNo(int applicantsNo) {
        this.applicantsNo = applicantsNo;
    }

    public String getId() {
        return id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getOpenings() {
        return openings;
    }

    public void setOpenings(int openings) {
        this.openings = openings;
    }

    public String getMinimumRequirements() {
        return minimumRequirements;
    }

    public void setMinimumRequirements(String minimumRequirements) {
        this.minimumRequirements = minimumRequirements;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public boolean isOfferingRelocation() {
        return offeringRelocation;
    }

    public void setOfferingRelocation(boolean offeringRelocation) {
        this.offeringRelocation = offeringRelocation;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Internship{" +
                "company=" + company +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", period=" + period +
                ", openings=" + openings +
                ", minimumRequirements='" + minimumRequirements + '\'' +
                ", skills=" + skills +
                ", acquiredSkills=" + acquiredSkills +
                ", offeringRelocation=" + offeringRelocation +
                ", id=" + id +
                ", category=" + category +
                ", applicantsNo=" + applicantsNo +
                ", salary=" + salary +
                '}';
    }

    public static enum Category implements HasDescription {

        Web("Web"),
        Mobile("Mobile"),
        Databases("Database Administration"),
        Cloud("Cloud Computing and Networking"),
        Business("Business Software"),
        BigData("Big Data"),
        AI("Artificial Intelligence"),
        Gaming("Gaming and Graphics"),
        Embedded("Embedded"),
        QA("Quality Assurance"),
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
