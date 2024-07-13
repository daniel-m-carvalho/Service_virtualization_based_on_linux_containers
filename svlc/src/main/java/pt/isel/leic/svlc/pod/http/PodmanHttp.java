package pt.isel.leic.svlc.pod.http;

import pt.isel.leic.svlc.util.podman.Podman;
import pt.isel.leic.svlc.util.executers.HttpExec;
import pt.isel.leic.svlc.util.results.Result;

import java.util.Map;

import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIfElse;
import static pt.isel.leic.svlc.util.podman.Messages.IMAGE_EXISTS_ASSUMPTION;
import static pt.isel.leic.svlc.util.auth.Auth.createAuthHeader;
import static pt.isel.leic.svlc.util.executers.HttpExec.executeRequest;
import static pt.isel.leic.svlc.pod.http.Requests.*;

/**
 * Implements the {@link Podman} interface to manage Podman containers and pods through HTTP requests.
 * This class provides methods to create, start, stop, and prune pods,
 * as well as to create containers and pull and delete images.
 */
public class PodmanHttp implements Podman {

    String podName;
    String image;
    String[] ports;
    String payload;
    Map<String,String> queryString;

    /**
     * Constructs a {@code PodmanHttp} instance with specified configurations.
     *
     * @param podName      The name of the pod to be managed.
     * @param image The registry path for pulling images.
     * @param ports        The port mappings in "hostPort:containerPort" format.
     */
    public PodmanHttp(String podName, String image, String ports) {
        this.podName = podName;
        this.image = image;
        this.ports = ports.split(":");
    }

    /**
     * Sets the payload for the HTTP request.
     *
     * @param payload The payload to set.
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Sets the query string for the HTTP request.
     *
     * @param queryString The query string to set.
     */
    public void setQueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    /**
     * Creates a container using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String createContainer() throws Exception {
        return executeRequest("POST", createContainerHTTP(), payload, null, null);
    }

    /**
     * Starts a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String startPod() throws Exception {
        return executeRequest("POST", startPodHTTP(podName), HttpExec.defaultPayload, null, null);
    }

    /**
     * Stops a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String stopPod() throws Exception {
        return executeRequest("POST", stopPodHTTP(podName), HttpExec.defaultPayload, null, null);
    }

    /**
     * Prunes unused pods using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String prunePods() throws Exception {
        return executeRequest("POST", prunePodsHTTP(), HttpExec.defaultPayload, null, null);
    }

    /**
     * Retrieves statistics for a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String getPodStatistics() throws Exception {
        return executeRequest("GET", statisticsPodHTTP(), HttpExec.defaultPayload, null, null);
    }

    /**
     * Retrieves logs for a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String createPod() throws Exception {
        return executeRequest("POST", createPodHTTP(), payload, null, null);
    }

    /**
     * Deploys a container in a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String pullImage() throws Exception {
        return execIfElse(
                image.split("/").length == 1,
                () -> IMAGE_EXISTS_ASSUMPTION,
                () -> executeRequest("POST", pullImageHTTP(), HttpExec.defaultPayload, queryString, createAuthHeader())
        );
    }

    /**
     * Deploys a container in a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public String deleteImage() throws Exception {
        return executeRequest("DELETE", deleteImageHTTP(image), HttpExec.defaultPayload, null, null);
    }
}
