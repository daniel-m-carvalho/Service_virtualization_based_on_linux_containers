package pt.isel.leic.svlc.podman.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.isel.leic.svlc.util.podman.Podman;
import pt.isel.leic.svlc.util.executers.HttpExec;
import pt.isel.leic.svlc.util.results.Result;

import java.util.Map;

import static pt.isel.leic.svlc.podman.cmd.Commands.checkImageCMD;
import static pt.isel.leic.svlc.util.executers.CmdExec.executeCommand;
import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIfElse;
import static pt.isel.leic.svlc.util.podman.Messages.IMAGE_EXISTS;
import static pt.isel.leic.svlc.util.auth.Auth.createAuthHeader;
import static pt.isel.leic.svlc.util.executers.HttpExec.executeRequest;
import static pt.isel.leic.svlc.podman.http.Requests.*;

/**
 * Implements the {@link Podman} interface to manage Podman containers and pods through HTTP requests.
 * This class provides methods to create, start, stop, and prune pods,
 * as well as to create containers and pull and delete images.
 */
public class PodmanHttp implements Podman<Map<String,Object>> {

    private String podName;
    private String image;
    private Map<String,Object> portsConf;
    private String payload = HttpExec.defaultPayload;
    private Map<String,String> queryString;
    private String serviceIpPort;

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
        this.portsConf = transform(ports.split(":"));
        this.serviceIpPort = "localhost:8080";
    }

    /**
     * Constructs a {@code PodmanHttp} instance with default configurations.
     */
    public PodmanHttp() {
        this.serviceIpPort = "localhost:8080";
    }

    /**
     * Sets the name of the pod to be managed.
     *
     * @param podName The name of the pod to set.
     */
    public void setPodName(String podName) {
        this.podName = podName;
    }

    /**
     * Gets the name of the pod to be managed.
     *
     * @return The name of the pod to be managed.
     */
    public String getPodName() {
        return podName;
    }

    /**
     * Sets the registry path for pulling images.
     *
     * @param image The registry path to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the registry path for pulling images.
     *
     * @return The registry path for pulling images.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the port mappings in "hostPort:containerPort" format.
     *
     * @param ports The port mappings to set.
     */

    public void setPortsConf(Map<String, Object> ports) {
        this.portsConf = ports;
    }

    /**
     * Gets the port mappings in "hostPort:containerPort" format.
     * @return The port mappings in "hostPort:containerPort" format.
     */
    public Map<String, Object> getPortsConf() {
        return portsConf;
    }

    /**
     * Sets the payload for the HTTP request.
     * @param payload The payload to set.
     * @throws JsonProcessingException If an error occurs while processing the JSON.
     */
    public void setPayload(Map<String, Object> payload) throws JsonProcessingException {
        this.payload = createPayload(payload);
    }

    /**
     * Gets the payload for the HTTP request.
     * @return The payload for the HTTP request.
     */

    public String getPayload() {
        return payload;
    }

    /**
     * Clears the payload for the HTTP request.
     */
    private void clearPayload() {
        this.payload = HttpExec.defaultPayload;
    }

    /**
     * Sets the query string for the HTTP request.
     * @param queryString The query string to set.
     */
    public void setQueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    /**
     * Gets the query string for the HTTP request.
     * @return The query string for the HTTP request.
     */
    public Map<String, String> getQueryString() {
        return queryString;
    }

    /**
     * Clears the query string for the HTTP request.
     */
    private void clearQueryString() {
        this.queryString = null;
    }

    /**
     * Sets the IP and port of the Podman service.
     * @param serviceIpPort The IP and port of the Podman service.
     */
    public void setServiceIpPort(String serviceIpPort) {
        this.serviceIpPort = serviceIpPort;
    }

    /**
     * Gets the IP and port of the Podman service.
     * @return The IP and port of the Podman service.
     */
    public String getServiceIpPort() {
        return serviceIpPort;
    }

    /**
     * Clears the payload and query string for the HTTP request.
     */
    public void reset() {
        clearPayload();
        clearQueryString();
    }

    /**
     * Creates a container using the Podman REST API.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    public Map<String,Object> createContainer() throws Exception {
        return executeRequest("POST", createContainerHTTP(serviceIpPort), payload, null, null);
    }

    /**
     * Deletes a container using the Podman REST API.
     * @param name The name of the container to delete.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String, Object> deleteContainer(String name) throws Exception {
        return executeRequest("DELETE", deleteContainerHTTP(name, serviceIpPort), payload, null, null);
    }

    /**
     * Starts a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String,Object> startPod() throws Exception {
        return executeRequest("POST", startPodHTTP(podName, serviceIpPort), payload, null, null);
    }

    /**
     * Stops a pod using the Podman REST API.
     *
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String,Object> stopPod() throws Exception {
        return executeRequest("POST", stopPodHTTP(podName, serviceIpPort), payload, null, null);
    }

    /**
     * Prunes unused pods using the Podman REST API.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String,Object> deletePod() throws Exception {
        return executeRequest("POST", deletePodHTTP(podName, serviceIpPort), payload, null, null);
    }

    /**
     * Retrieves statistics for a pod using the Podman REST API.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String,Object> inspectPod() throws Exception {
        return executeRequest("GET", inspectPodHTTP(podName, serviceIpPort), payload, null, null);
    }

    /**
     * Retrieves logs for a pod using the Podman REST API.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String,Object> createPod() throws Exception {
        return executeRequest("POST", createPodHTTP(serviceIpPort), payload, null, null);
    }

    /**
     * Deploys a container in a pod using the Podman REST API.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String,Object> pullImage() throws Exception {
        String checkImage = executeCommand(checkImageCMD(image), "", true).getOutput();
        return execIfElse(
            !checkImage.isEmpty(),
            () -> Map.of("message", IMAGE_EXISTS),
            () -> executeRequest("POST", pullImageHTTP(serviceIpPort), payload, queryString, createAuthHeader())
        );
    }

    /**
     * Deploys a container in a pod using the Podman REST API.
     * @return Either an error or a success message wrapped in an {@link Result} object.
     */
    @Override
    public Map<String,Object> deleteImage() throws Exception {
        return executeRequest("DELETE", deleteImageHTTP(image, serviceIpPort), payload, null, null);
    }

    /**
     *  Converts an array of strings to an array of integers.
     *  @param ports The array of strings to convert.
     *  @return The array of integers.
     */
    private Map<String, Object> transform(String[] ports) {
        return  Map.of(
            "host_ip", "0.0.0.0",
            "container_port", Integer.parseInt(ports[1]),
            "host_port", Integer.parseInt(ports[0])
        );
    }

    /**
     * Creates a JSON payload from a map of key-value pairs.
     * @param payload The map of key-value pairs to convert.
     * @return The JSON payload.
     * @throws JsonProcessingException If an error occurs while processing the JSON.
     */
    private static String createPayload (Map<String, Object> payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(payload);
    }
}
