package pt.isel.leic.svlc.pod.http;

/**
 *  This class contains the URLs for the HTTP requests.
 *  The URLs are used to interact with the Podman API.
 *  The class contains methods to create, start, stop, and manage pods and containers.
 *  As well as methods to pull and delete images.
 */
public class Requests {

    public final static String IP_PORT = "localhost:8080";

    private final static String BASE_URL = "http://"+ IP_PORT +"/v5.0.0/libpod";

    public static String createPodHTTP() {
        return BASE_URL + "/pods/create";
    }

    public static String startPodHTTP(String podName) {
        return BASE_URL + "/pods/" + podName + "/start";
    }

    public static String stopPodHTTP(String podName) {
        return BASE_URL + "/pods/" + podName + "/stop";
    }

    public static String prunePodsHTTP() {
        return BASE_URL + "/pods/prune";
    }

    public static String statisticsPodHTTP() {
        return BASE_URL + "/pods/stats";
    }

    public static String pullImageHTTP() {
        return BASE_URL + "/images/pull";
    }

    public static String deleteImageHTTP(String imageId) {
        return BASE_URL + "/images/" + imageId;
    }

    public static String createContainerHTTP() {
        return BASE_URL + "/containers/create";
    }
}
