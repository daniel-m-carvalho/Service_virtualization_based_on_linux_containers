package pt.isel.leic.svlc.helm.yaml;

import org.yaml.snakeyaml.error.YAMLException;
import pt.isel.leic.svlc.util.results.Failure;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Success;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the values of a Helm chart.
 * It is used to store the values of a Helm chart in a map.
 */
public class Values extends YamlConverter {
    private final Map<String, Object> values;

    /**
     * Constructor of the class.
     */
    public Values() {
        this.values = new HashMap<>();
    }

    /**
     * Sets a property in the map.
     * @param key the key of the property
     * @param value the value of the property
     */
    public void setProperty(String key, Object value) {
        this.values.put(key, value);
    }

    /**
     * Gets the map of values.
     * @return the map of values
     */
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * Gets a property from the map.
     * @param key the key of the property
     * @return the value of the property
     */
    public Object getProperty(String key) {
        return this.values.get(key);
    }

    @Override
    public String toString() {
        return "ValuesYaml{" +
                "values=" + values +
                "\n}";
    }
}
