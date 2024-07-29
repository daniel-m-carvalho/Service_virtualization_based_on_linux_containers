package pt.isel.leic.svlc.util.kubernetes.configurations;

/**
 * Configuration class for scaling a resource.
 */
public class CreateConfig {

    private String name;  // The name of the resource to scale.
    private int replicas; // The number of replicas to scale to.
    private String pretty;  // If 'true', then the output is pretty printed.
    private String dryRun;  // If 'true', then the server will simulate the request.
    private String fieldManager;    // A unique value that identifies this request.
    private String fieldValidation; // If 'true', then the input is validated before submitting the request.

    /**
     * Creates a new CreateConfig object with no arguments.
     */
    public CreateConfig() {}

    /**
     * Creates a new CreateConfig object.
     * @param name The name of the resource to scale.
     * @param replicas The number of replicas to scale to.
     */
    public CreateConfig(String name, int replicas) {
        this.name = name;
        this.replicas = replicas;
    }

    // Getters and Setters for the class attributes.

    public String getName() {
        return name;
    }

    public int getReplicas() {
        return replicas;
    }

    public String getPretty() {
        return pretty;
    }

    public void setPretty(String pretty) {
        this.pretty = pretty;
    }

    public String getDryRun() {
        return dryRun;
    }

    public void setDryRun(String dryRun) {
        this.dryRun = dryRun;
    }

    public String getFieldManager() {
        return fieldManager;
    }

    public void setFieldManager(String fieldManager) {
        this.fieldManager = fieldManager;
    }

    public String getFieldValidation() {
        return fieldValidation;
    }

    public void setFieldValidation(String fieldValidation) {
        this.fieldValidation = fieldValidation;
    }
}
