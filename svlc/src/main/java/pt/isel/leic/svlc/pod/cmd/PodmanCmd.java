package pt.isel.leic.svlc.pod.cmd;

import pt.isel.leic.svlc.util.podman.Podman;

import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIfElse;
import static pt.isel.leic.svlc.util.podman.Messages.*;
import static pt.isel.leic.svlc.util.executers.CmdExec.executeCommand;

/**
 * Implements the {@link Podman} interface to provide methods for interacting with Podman via command-line interface.
 * This class encapsulates the functionality for creating, starting, stopping, and managing pods and containers using Podman commands.
 */
public class PodmanCmd implements Podman {

    String podName; // The name of the pod to be managed.
    String image;   // The image or registry to be used in the pod.
    String ports;   // The ports to be exposed by the pod.

    /**
     * Constructs a PodmanCmd instance with specified pod name, image, and ports.
     *
     * @param podName The name of the pod.
     * @param image The Docker image to be used in the pod.
     * @param ports The ports to be exposed by the pod.
     */
    public PodmanCmd(String podName, String image, String ports){
        this.podName = podName;
        this.image = image;
        this.ports = ports;
    }

    /**
     * Constructs a PodmanCmd instance with no arguments.
     */
    public PodmanCmd(){}

    /**
     * Deploys a container within a pod using the 'podman run' command.
     * @return A String containing the output of the command.
     */
    public String deployInPod() throws Exception {
        return executeCommand(Commands.deployInPodCMD(podName, image), DEPLOY_IN_POD_SUCCESS, true).getOutput();
    }

    /**
     * Creates a pod using the 'podman pod create' command.
     * @return A String containing the output of the command.
     */
    @Override
    public String createPod() throws Exception{
        return executeCommand(Commands.createPodCMD(podName, ports), CREATE_POD_SUCCESS,true).getOutput();
    }

    /**
     * Starts a pod using the 'podman pod start' command.
     * @return A String containing the output of the command.
     */
    @Override
    public String startPod() throws Exception {
        return executeCommand(Commands.startPodCMD(podName), START_POD_SUCCESS,true).getOutput();
    }

    /**
     * Stops a pod using the 'podman pod stop' command.
     * @return A String containing the output of the command.
     */
    @Override
    public String stopPod() throws Exception {
        return executeCommand(Commands.stopPodCMD(podName), STOP_POD_SUCCESS,true).getOutput();
    }

    /**
     * Prunes (removes) stopped pods using the 'podman pod prune' command.
     * @return A String containing the output of the command.
     */
    @Override
    public String prunePods() throws Exception {
        return executeCommand(Commands.prunePodsCMD(), PRUNE_PODS_SUCCESS,true).getOutput();
    }

    /**
     * Gets statistics for the pods using the 'podman pod stats' command.
     * @return A String containing the output of the command.
     */
    @Override
    public String getPodStatistics() throws Exception {
        return executeCommand(Commands.getPodStatisticsCMD(podName), GET_POD_STATISTICS_SUCCESS,true).getOutput();
    }

    /**
     * Creates a container using the 'podman create' command.
     * @return A String containing the output of the command.
     */
    @Override
    public String createContainer() throws Exception {
        return executeCommand(Commands.createContainerCMD(image), CREATE_CONTAINER_SUCCESS, true).getOutput();
    }

    /**
     * Pulls an image using the 'podman pull' command.
     * If the image already exists, it does not pull the image.
     * @return A String containing the output of the command.
     */
    public String pullImage() throws Exception {
        String imageExists = executeCommand(Commands.checkImageCMD(image), EMPTY,true).getOutput();

        return execIfElse(
                imageExists.isEmpty(),
                () -> executeCommand(Commands.pullImageCMD(image), PULL_IMAGE_SUCCESS,true).getOutput(),
                () -> IMAGE_EXISTS
        );
    }

    /**
     * Deletes an image using the 'podman image rm' command.
     * @return A String containing the output of the command.
     */
    @Override
    public String deleteImage() throws Exception {
        return executeCommand(Commands.deleteImageCMD(image),  DELETE_IMAGE_SUCCESS,true).getOutput();
    }

    /**
     * Starts the Podman service using the 'podman system service' command.
     * @return A Thread object representing the thread that executes the command.
     */
    public static Thread startPodmanService() throws Exception {
        return executeCommand(Commands.startPodmanServiceCMD(), PODMAN_SERVICE_STARTED,false).getThread();
    }
}