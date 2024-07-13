package pt.isel.leic.svlc.yaml;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import pt.isel.leic.svlc.util.results.Failure;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Success;

import java.util.Map;

import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIfElse;

/**
 * This class represents a Yaml converter.
 * It is used to convert a map to a Yaml string.
 */
public class YamlConverter {

    /**
     * Converts a map to a Yaml string.
     *
     * @param data The map to convert.
     * @return A {@link Result} object with the Yaml string if the conversion was successful,
     * or an error message if the conversion was not successful.
     */
    private static Result<Failure, Success<String>> toYaml(Map<String, Object> data) {
        try {
            Yaml yaml = new Yaml();
            return Result.Right(new Success<>(yaml.dump(data)));
        } catch (YAMLException e) {
            return Result.Left(new Failure(e.getMessage()));
        }
    }

    public static String generateYaml(Map<String, Object> data) {
        Result<Failure, Success<String>> yaml = toYaml(data);
        try {
            return execIfElse(
                yaml.success(),
                () -> yaml.right().value(),
                () -> yaml.left().message()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
