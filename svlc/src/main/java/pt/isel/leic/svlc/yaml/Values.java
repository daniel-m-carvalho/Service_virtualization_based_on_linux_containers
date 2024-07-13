package pt.isel.leic.svlc.yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the values of a chart.
 */
public class Values {

    private final Map<String, Object> values;   // The values of the chart.

    /**
     * Constructor of the class.
     */
    public Values() {
        this.values = new HashMap<>();
    }

    /**
     * Constructor of the class.
     * @param values the values to store
     */
    public Values(Map<String, Object> values) {
        this.values = values;
    }

    /**
     * Returns the values of the chart.
     * @return the values of the chart
     */
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * Sets a property in the map.
     * @param key the key of the property
     * @param value the value of the property
     */
    public void setProperty(String key, Object value) {
        this.values.put(key, value);
    }

    @Override
    public String toString() {
        return "ValuesYaml{" +
                "values=" + values +
                "\n}";
    }
}
