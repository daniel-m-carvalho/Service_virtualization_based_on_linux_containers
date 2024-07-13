package pt.isel.leic.svlc.yaml;

import io.fabric8.kubernetes.client.utils.Serialization;

import java.util.List;
import java.util.Map;

/**
 * This class represents a Yaml converter.
 * It is used to convert a map to a Yaml string.
 */
public class YamlConverter {

    /**
     * Converts a map to a Yaml string.
     *
     * @param data The map to convert.
     * @return The Yaml string representation of the map.
     */
    public static String toYaml(Map<String, Object> data) {
        return Serialization.asYaml(data);
    }

    /**
     * Converts a list of maps to a Yaml string.
     *
     * @param resources The list of maps to convert.
     * @return The Yaml string representation of the list of maps.
     */
    public static String serializeToYaml(List<Map<String, Object>> resources) {
        StringBuilder yaml = new StringBuilder();
        resources.forEach(resource -> {
                yaml.append(toYaml(resource));
                yaml.append("\n---\n");
        });
        return yaml.toString();
    }
}
