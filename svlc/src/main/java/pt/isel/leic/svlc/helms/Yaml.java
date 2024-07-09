package pt.isel.leic.svlc.helms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Class that provides a method to convert a map to a YAML string.
 * The map must have a string as key and an object as value.
 */
public abstract class Yaml {
    public static String toYaml(Map<String, Object> map){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.writeValueAsString(map);
        } catch (IOException e) {
            System.out.println("Error converting map to YAML: " + e.getMessage());
            return null;
        }
    }
}
