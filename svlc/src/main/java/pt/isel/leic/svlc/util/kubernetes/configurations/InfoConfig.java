package pt.isel.leic.svlc.util.kubernetes.configurations;

/**
 * Class that represents the configuration for getting logs from a resource.
 */
public class InfoConfig {

    private final String name;  // The name of the resource to get logs from.
    private String container;   // The container name.
    private Boolean follow = false;  // If true, then the logs are streamed.
    private Boolean insecureSkipTlsVerify = false;   // If true, then the server's certificate will not be checked for validity.
    private Integer limitBytes;  // If set, then the logs are returned for this many bytes.
    private String pretty;  // If 'true', then the output is pretty printed.
    private Boolean previous = false;    // If 'true', then the previous container is used.
    private Integer sinceSeconds;    // If set, then the logs are returned since this many seconds ago.
    private Integer tailLines;   // If set, then the logs are returned for this many lines.
    private Boolean timestamps = false;  // If 'true', then the timestamps are included in the logs.

    /**
     * Constructor
     * @param name The name of the resource to get logs from.
     */
    public InfoConfig(String name) {
        this.name = name;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public Boolean getFollow() {
        return follow;
    }

    public void setFollow() {
        this.follow = true;
    }

    public Boolean getInsecureSkipTlsVerify() {
        return insecureSkipTlsVerify;
    }

    public void setInsecureSkipTlsVerify() {
        this.insecureSkipTlsVerify = true;
    }

    public Integer getLimitBytes() {
        return limitBytes;
    }

    public void setLimitBytes(Integer limitBytes) {
        this.limitBytes = limitBytes;
    }

    public String getPretty() {
        return pretty;
    }

    public void setPretty(String pretty) {
        this.pretty = pretty;
    }

    public Boolean getPrevious() {
        return previous;
    }

    public void setPrevious() {
        this.previous = true;
    }

    public Integer getSinceSeconds() {
        return sinceSeconds;
    }

    public void setSinceSeconds(Integer sinceSeconds) {
        this.sinceSeconds = sinceSeconds;
    }

    public Integer getTailLines() {
        return tailLines;
    }

    public void setTailLines(Integer tailLines) {
        this.tailLines = tailLines;
    }

    public Boolean getTimestamps() {
        return timestamps;
    }

    public void setTimestamps() {
        this.timestamps = true;
    }
}
