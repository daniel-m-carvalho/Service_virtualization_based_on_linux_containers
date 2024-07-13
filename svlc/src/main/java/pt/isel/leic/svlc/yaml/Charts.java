package pt.isel.leic.svlc.yaml;

import pt.isel.leic.svlc.util.executers.ExecIfElse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

 /**
 * This class represents the metadata of a Helm chart.
 * It is used to store the metadata of a Helm chart in a map.
 */

public class Charts extends YamlConverter {

    private final String apiVersion = "v2"; // The API version of the Helm chart.
    private String name;    // The name of the Helm chart.
    private String description; // A short description of the chart.
    private String version; // The version of the chart.
    private String appVersion;  // The version of the app this chart installs.
    private List<String> keywords;  // A list of keywords associated with the chart.
    private Map<String, String> sources;    // The source URLs of the chart.
    private List<String> maintainers;   // The maintainers of the chart.
    private String home; // optional
    private String icon; // optional
    private List<String> dependencies; // optional
    private String type; // optional
    private Map<String, String> annotations; // optional

    /**
     * Constructs a Charts object with no arguments.
     */
    public Charts() {}

     /**
      * Constructs a Charts object with specified metadata and configuration.
      * @param name The name of the Helm chart.
      * @param description A short description of the chart.
      * @param version The version of the chart.
      * @param appVersion The version of the app this chart installs.
      * @param keywords A list of keywords associated with the chart.
      * @param sources The source URLs of the chart.
      * @param maintainers A list of maintainers of the chart.
      */
    public Charts(
            String name,
            String description,
            String version,
            String appVersion,
            List<String> keywords,
            Map<String, String> sources,
            List<String> maintainers
    ) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.appVersion = appVersion;
        this.keywords = keywords;
        this.sources = sources;
        this.maintainers = maintainers;
    }

     /**
      * Converts the chart information to YAML format.
      *
      * @return A map containing the chart information.
      */
    public String generateChart() {
        Map<String, Object> map = new HashMap<>();
        map.put("apiVersion", apiVersion);
        map.put("name", name);
        map.put("description", description);
        map.put("version", version);
        map.put("appVersion", appVersion);
        map.put("keywords", keywords);
        map.put("sources", sources);
        map.put("maintainers", maintainers);
        map.put("home", home);
        map.put("icon", icon);
        map.put("dependencies", dependencies);
        map.put("type", type);
        map.put("annotations", annotations);

        return generateYaml(map);
    }

    public String getApiVersion() {
        return apiVersion;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Map<String, String> getSources() {
        return sources;
    }

    public void setSources(Map<String, String> sources) {
        this.sources = sources;
    }

    public List<String> getMaintainers() {
        return maintainers;
    }

    public void setMaintainers(List<String> maintainers) {
        this.maintainers = maintainers;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Map<String, String> annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return "ChartYaml{" +
                "apiVersion='" + apiVersion + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", keywords=" + keywords +
                ", sources=" + sources +
                ", maintainers=" + maintainers +
                ", home='" + home + '\'' +
                ", icon='" + icon + '\'' +
                ", dependencies=" + dependencies +
                ", type='" + type + '\'' +
                ", annotations=" + annotations +
                '}';
    }
}
