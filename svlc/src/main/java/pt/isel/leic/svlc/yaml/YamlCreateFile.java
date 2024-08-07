package pt.isel.leic.svlc.yaml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import pt.isel.leic.svlc.util.results.Failure;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Success;

import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIf;
import static pt.isel.leic.svlc.util.results.Result.Left;

public class YamlCreateFile {

    /**
     * Checks if the filename is valid.
     *
     * @param filename The filename to check.
     * @return True if the filename is valid, false otherwise.
     */
    private static boolean isInvalidFilename(String filename) {
        return filename == null || filename.isEmpty();
    }

    /**
     * Ensures that the filename has the .yaml extension.
     *
     * @param filename The filename to check.
     * @return The filename with the .yaml extension.
     */
    private static String ensureYamlExtension(String filename) {
        return filename.endsWith(".yaml") ? filename : filename + ".yaml";
    }

    /**
     * Checks if the HELM_PATH environment variable is set.
     *
     * @param path The HELM_PATH environment variable.
     * @return True if the HELM_PATH environment variable is set, false otherwise.
     */
    private static boolean isPathNotSet(String path) {
        return path == null || path.isEmpty();
    }

    /**
     * Creates a YAML file with the given content.
     *
     * @param filename The filename of the YAML file.
     * @param content  The content of the YAML file.
     * @return A {@link Result} object with the success message if the file was created successfully,
     * or an error message if the file was not created.
     */
    public static Result<Failure, Success<String>> createYamlFile(String filename, String content, String path) {

        try {
            execIf(
                isInvalidFilename(filename),
                () -> Left(new Failure("Filename cannot be null or empty"))
            );

            filename = ensureYamlExtension(filename);

            execIf(
                isPathNotSet(path),
                () -> Left(new Failure("HELM_PATH environment variable is not set"))
            );

            Path filePath = Paths.get(path, filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            return Result.Right(new Success<>("File created successfully: " + filePath));
        } catch (Exception e) {
            return Left(new Failure("Failed to create or write to file: " + e.getMessage()));
        }
    }
}
