package pt.isel.leic.svlc.util.kubernetes.configurations;


/**
 * ListConfig class represents the configuration for a list operation in Kubernetes.
 */
public class ListConfig {

    private String pretty;  // If 'true', then the output is pretty printed.
    private Boolean allowWatchBookmarks;    // Allow watch bookmarks to be sent.
    private String _continue;   // The continue token for the list operation.
    private String fieldSelector;   // A selector to restrict the list of returned objects by their fields.
    private String labelSelector;   // A selector to restrict the list of returned objects by their labels.
    private Integer limit;  // The maximum number of items to return.
    private String resourceVersion; // The resource version from which to continue.
    private String resourceVersionMatch;    // The resource version match label to filter on.
    private Boolean sendInitialEvents;  // Send initial events.
    private Integer timeoutSeconds; // Timeout for the list/watch call.
    private Boolean watch;  // If true, then watch for changes to the returned objects.

    /**
     * Constructs a ListConfig object with no arguments.
     */
    public ListConfig() {}

    public String getPretty() {
        return pretty;
    }

    public void setPretty(String pretty) {
        this.pretty = pretty;
    }

    public Boolean hasPretty() {
        return pretty != null && !pretty.isEmpty();
    }

    public Boolean getAllowWatchBookmarks() {
        return allowWatchBookmarks;
    }

    public void setAllowWatchBookmarks() {
        this.allowWatchBookmarks = true;
    }

    public String getContinue() {
        return _continue;
    }

    public void setContinue(String _continue) {
        this._continue = _continue;
    }

    public Boolean hasContinue() {
        return _continue != null && !_continue.isEmpty();
    }

    public String getFieldSelector() {
        return fieldSelector;
    }

    public void setFieldSelector(String fieldSelector) {
        this.fieldSelector = fieldSelector;
    }

    public Boolean hasFieldSelector() {
        return fieldSelector != null && !fieldSelector.isEmpty();
    }

    public String getLabelSelector() {
        return labelSelector;
    }

    public void setLabelSelector(String labelSelector) {
        this.labelSelector = labelSelector;
    }

    public Boolean hasLabelSelector() {
        return labelSelector != null && !labelSelector.isEmpty();
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean hasLimit() {
        return limit != null;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public Boolean hasResourceVersion() {
        return resourceVersion != null && !resourceVersion.isEmpty();
    }

    public String getResourceVersionMatch() {
        return resourceVersionMatch;
    }

    public void setResourceVersionMatch(String resourceVersionMatch) {
        this.resourceVersionMatch = resourceVersionMatch;
    }

    public Boolean hasResourceVersionMatch() {
        return resourceVersionMatch != null && !resourceVersionMatch.isEmpty();
    }

    public Boolean getSendInitialEvents() {
        return sendInitialEvents;
    }

    public void setSendInitialEvents() {
        this.sendInitialEvents = true;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public Boolean hasTimeoutSeconds() {
        return timeoutSeconds != null;
    }

    public Boolean getWatch() {
        return watch;
    }

    public void setWatch() {
        this.watch = true;
    }
}
