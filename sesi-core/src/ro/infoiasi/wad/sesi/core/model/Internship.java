package ro.infoiasi.wad.sesi.core.model;

import com.google.common.collect.Maps;
import org.joda.time.Period;
import ro.infoiasi.wad.sesi.core.util.HasDescription;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement // used for XML/JSON (de)serialization
public class Internship implements Resource {

    private int companyId;
    private String name;
    private String description;
    private String city;
    private Period period;
    private int openings;
    private String requirements;
    private List<String> skills;
    private boolean offeringRelocation;
    private int id;
    private Category category;
    private int applicantsNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Internship that = (Internship) o;

        if (applicantsNo != that.applicantsNo) return false;
        if (companyId != that.companyId) return false;
        if (id != that.id) return false;
        if (offeringRelocation != that.offeringRelocation) return false;
        if (openings != that.openings) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (requirements != null ? !requirements.equals(that.requirements) : that.requirements != null) return false;
        if (skills != null ? !skills.equals(that.skills) : that.skills != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + openings;
        result = 31 * result + (requirements != null ? requirements.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (offeringRelocation ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + applicantsNo;
        return result;
    }

    public int getApplicantsNo() {

        return applicantsNo;
    }

    public void setApplicantsNo(int applicantsNo) {
        this.applicantsNo = applicantsNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Internship{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", period=" + period +
                ", openings=" + openings +
                ", requirements='" + requirements + '\'' +
                ", skills=" + skills +
                ", offeringRelocation=" + offeringRelocation +
                ", id=" + id +
                ", category='" + category + '\'' +
                ", applicantsNo=" + applicantsNo +
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
