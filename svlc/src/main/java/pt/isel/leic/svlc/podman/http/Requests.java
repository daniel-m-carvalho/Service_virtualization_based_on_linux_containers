package pt.isel.leic.svlc.podman.http;

/**
 *  This class contains the URLs for the HTTP requests.
 *  The URLs are used to interact with the Podman API.
 *  The class contains methods to create, start, stop, and manage pods and containers.
 *  As well as methods to pull and delete images.
 */
public class Requests {

    public static String createPodHTTP(String ipPort) {
        return prepareBaseURL(ipPort) + "/pods/create";
    }

    public static String startPodHTTP(String podName, String ipPort) {
        return prepareBaseURL(ipPort) + "/pods/" + podName + "/start";
    }

    public static String stopPodHTTP(String podName, String ipPort) {
        return prepareBaseURL(ipPort) + "/pods/" + podName + "/kill";
    }

    public static String deletePodHTTP(String podName, String ipPort) {
        return prepareBaseURL(ipPort) + "/pods/" + podName;
    }

    public static String inspectPodHTTP(String podName, String ipPort) {
        return prepareBaseURL(ipPort) + "/pods/"+ podName +"/stats";
    }

    public static String pullImageHTTP(String ipPort) {
        return prepareBaseURL(ipPort) + "/images/pull";
    }

    public static String deleteImageHTTP(String imageId,String ipPort) {
        return prepareBaseURL(ipPort) + "/images/" + imageId;
    }

    public static String createContainerHTTP(String ipPort) {
        return prepareBaseURL(ipPort) + "/containers/create";
    }

    public static String deleteContainerHTTP(String containerId, String ipPort) {
        return prepareBaseURL(ipPort) + "/containers/" + containerId;
    }

    private static String prepareBaseURL(String ipPort) {
        return String.format("http://%s/v5.0.0/libpod", ipPort);
    }
}
