package pt.isel.leic.svlc.pod.cmd;


import static pt.isel.leic.svlc.pod.http.Requests.IP_PORT;

/**
 * This class contains the commands that are used to interact with the Podman CLI.
 * The commands are stored as static arrays of strings.
 * The commands are used to create pods, deploy containers in pods, pull images,
 * check images, prune pods, start pods, stop pods, get pod statistics,
 * create containers and delete images.
 */
public class Commands {
    public static String[] createPodCMD(String podName, String ports) {
        return new String[] {"podman", "pod", "create", "--name", podName, "-p", ports};
    }

    public static String[] deployInPodCMD(String podName, String image) {
        return new String[] {"podman", "run", "--pod", podName, "-d", image};
    }

    public static String [] pullImageCMD(String image) {
        return new String[] {"podman", "pull", image};
    }

    public static String [] checkImageCMD(String image) {
        return new String[] {"podman", "images", "-q", image};
    }

    public static String [] prunePodsCMD() {
        return new String[] {"podman", "pod", "prune"};
    }

    public static String [] startPodCMD(String podName) {
        return new String[] {"podman", "pod", "start", podName};
    }

    public static String [] stopPodCMD(String podName) {
        return new String[] {"podman", "pod", "stop", podName};
    }

    public static String [] getPodStatisticsCMD(String podName) {
        return new String[] {"podman", "pod", "stats", podName};
    }

    public static String [] createContainerCMD(String image) {
        return new String[] {"podman", "create", image};
    }

    public static String [] deleteImageCMD(String imageId) {
        return new String[] {"podman", "image", "rm", imageId};
    }

    public static String [] startPodmanServiceCMD() {
        return new String[]{"podman", "system", "service", "tcp:" + IP_PORT};
    }
}
