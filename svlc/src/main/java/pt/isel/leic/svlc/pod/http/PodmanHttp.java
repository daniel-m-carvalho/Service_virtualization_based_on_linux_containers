package pt.isel.leic.svlc.pod.http;

import pt.isel.leic.svlc.pod.Podman;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Errors;
import pt.isel.leic.svlc.util.results.Success;

import java.util.Map;

import static pt.isel.leic.svlc.pod.Messages.IMAGE_EXISTS_ASSUMPTION;
import static pt.isel.leic.svlc.util.auth.AuthHeader.createAuthHeader;
import static pt.isel.leic.svlc.util.results.Result.Left;
import static pt.isel.leic.svlc.util.results.Result.Right;
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
     * Executes an HTTP request with the specified parameters.
     *
     * @param method      The HTTP method to use (e.g., "GET", "POST").
     * @param url         The endpoint URL.
     * @param payload     The request payload, if any.
     * @param queryString Query parameters for the request.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    private Result<Errors, Success<String>> exec(String method, String url, String payload, Map<String, String> queryString, String authInfo) {
        try {
            return Right(new Success<String>(HttpReq.executeRequest(method, url, payload, queryString, authInfo)));
        } catch (Exception e) {
            return Left(new Errors("Failed to deploy. " + e.getMessage()));
        }
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
    public Result<Errors, Success<String>> createContainer() {
        return exec("POST", createContainerHTTP(), payload, null, null);
    }

    /**
     * Starts a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Result<Errors, Success<String>> startPod() {
        return exec("POST", startPodHTTP(podName), HttpReq.defaultPayload, null, null);
    }

    /**
     * Stops a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Result<Errors, Success<String>> stopPod() {
        return exec("POST", stopPodHTTP(podName), HttpReq.defaultPayload, null, null);
    }

    /**
     * Prunes unused pods using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Result<Errors, Success<String>> prunePods() {
        return exec("POST", prunePodsHTTP(), HttpReq.defaultPayload, null, null);
    }

    /**
     * Retrieves statistics for a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Result<Errors, Success<String>> getPodStatistics() {
        return exec("GET", statisticsPodHTTP(), HttpReq.defaultPayload, null, null);
    }

    /**
     * Retrieves logs for a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Result<Errors, Success<String>> createPod() {
        return exec("POST", createPodHTTP(), payload, null, null);
    }

    /**
     * Deploys a container in a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Result<Errors, Success<String>> pullImage() {
        if (image.split("/").length == 1) {
            return Right(new Success<String>(IMAGE_EXISTS_ASSUMPTION));
        }
        return exec("POST", pullImageHTTP(), HttpReq.defaultPayload, queryString, createAuthHeader());
    }

    /**
     * Deploys a container in a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Result<Errors, Success<String>> deleteImage() {
        return exec("DELETE", deleteImageHTTP(image), HttpReq.defaultPayload, null, null);
    }
}
