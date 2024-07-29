package pt.isel.leic.svlc.podman.cmd;

/**
 * This class contains the commands that are used to interact with the Podman CLI.
 * The commands are stored as static arrays of strings.
 * The commands are used to create pods, deploy containers in pods, pull images,
 * check images, prune pods, start pods, stop pods, get pod statistics,
 * create containers and delete images.
 */
public class Commands {

    public static String[] createPodCMD(String podName, String ports) {
        return new String[]{"podman", "pod", "create", "--name", podName, "-p", ports};
    }

    public static String[] deployInPodCMD(String podName, String image) {
        return new String[]{"podman", "run", "-d", "--pod", podName, image};
    }

    public static String[] pullImageCMD(String image) {
        return new String[]{"podman", "pull", image};
    }

    public static String[] checkImageCMD(String image) {
        return new String[]{"podman", "images", "-q", image};
    }

    public static String[] deletePodCMD(String podName) {
        return new String[]{"podman", "pod", "rm", "-f", podName};
    }

    public static String[] startPodCMD(String podName) {
        return new String[]{"podman", "pod", "start", podName};
    }

    public static String[] stopPodCMD(String podName) {
        return new String[]{"podman", "pod", "stop", podName};
    }

    public static String[] inspectPodCMD(String podName) {
        return new String[]{"podman", "pod", "inspect", podName};
    }

    public static String[] createContainerCMD(String name, String image) {
        return new String[]{"podman", "create", "--name", name, image};
    }

    public static String[] deleteContainerCMD(String containerId) {
        return new String[]{"podman", "rm", containerId};
    }

    public static String[] deleteImageCMD(String imageId) {
        return new String[]{"podman", "rmi", imageId};
    }

    public static String[] startPodmanServiceCMD(String ipPort) {
        return new String[]{"podman", "system", "service", "tcp:" + ipPort};
    }
}
