package pt.isel.leic.svlc.pod.cmd;

import pt.isel.leic.svlc.pod.Podman;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Errors;
import pt.isel.leic.svlc.util.results.Success;

import static pt.isel.leic.svlc.pod.Messages.*;
import static pt.isel.leic.svlc.util.results.Result.Left;
import static pt.isel.leic.svlc.util.results.Result.Right;

/**
 * Implements the {@link } interface to provide methods for interacting with Podman via command-line interface.
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
    public PodmanCmd(){ }

    /**
     * Executes a Podman command and returns the result.
     *
     * @param cmd The Podman command to be executed as an array of {@link String}.
     * @param successMessage The success message to be returned if the command is successful.
     * @return An {@link Result} containing an {@link Errors} object in case of failure, or a success message as {@link String} in case of success.
     */
    private static Result<Errors, Success<String>> exec(String [] cmd, String successMessage, boolean wait){
        try {
            return Right(new Success<String>(CmdExec.executeCommand(cmd, successMessage, wait)));
        } catch (Exception e) {
            return Left(new Errors(e.getMessage()));
        }
    }

    /**
     * Deploys a container within a pod using the 'podman run' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if the deployment failed, or a success message if the deployment succeeded.
     */
    public Result<Errors, Success<String>> deployInPod() {
        return exec(Commands.deployInPodCMD(podName, image), DEPLOY_IN_POD_SUCCESS ,true);
    }

    /**
     * Creates a pod using the 'podman pod create' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if the creation failed, or a success message if the creation succeeded.
     */
    public Result<Errors, Success<String>> createPod(){
        return exec(Commands.createPodCMD(podName, ports), CREATE_POD_SUCCESS,true);
    }

    /**
     * Starts a pod using the 'podman pod start' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if starting the pod failed, or a success message if the pod was started successfully.
     */
    @Override
    public Result<Errors, Success<String>> startPod() {
        return exec(Commands.startPodCMD(podName), START_POD_SUCCESS,true);
    }

    /**
     * Stops a pod using the 'podman pod stop' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if stopping the pod failed, or a success message if the pod was stopped successfully.
     */
    @Override
    public Result<Errors, Success<String>> stopPod() {
        return exec(Commands.stopPodCMD(podName), STOP_POD_SUCCESS,true);
    }

    /**
     * Prunes (removes) stopped pods using the 'podman pod prune' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if
     * pruning the pods failed, or a success message if the pods were pruned successfully.
     */
    @Override
    public Result<Errors, Success<String>> prunePods() {
        return exec(Commands.prunePodsCMD(), PRUNE_PODS_SUCCESS,true);
    }

    /**
     * Gets statistics for the pods using the 'podman pod stats' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if getting the statistics failed,
     * or the statistics as a {@link String} if successful.
     * */
    @Override
    public Result<Errors, Success<String>> getPodStatistics() {
        return exec(Commands.getPodStatisticsCMD(podName), GET_POD_STATISTICS_SUCCESS,true);
    }

    /**
     * Creates a container using the 'podman create' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if the creation failed,
     * or a success message if the creation succeeded.
     */
    @Override
    public Result<Errors, Success<String>> createContainer() {
        return exec(Commands.createContainerCMD(image), CREATE_CONTAINER_SUCCESS,true);
    }

    /**
     * Pulls an image using the 'podman pull' command.
     * If the image already exists, it does not pull the image.
     *
     * @return An {@link Result} containing an {@link Errors} object if the pull failed,
     * or a success message if the image was pulled successfully.
     */
    public Result<Errors, Success<String>> pullImage() {
        Result<Errors, Success<String>> imageExists = exec(Commands.checkImageCMD(image), EMPTY,true);
        if (imageExists.success() && imageExists.right().value().isEmpty()){
            return exec(Commands.pullImageCMD(image), PULL_IMAGE_SUCCESS,true);
        } else {
            return Right(new Success<String>(IMAGE_EXISTS));
        }
    }

    /**
     * Deletes an image using the 'podman image rm' command.
     *
     * @return An {@link Result} containing an {@link Errors} object if the deletion failed,
     * or a success message if the image was deleted successfully.
     */
    @Override
    public Result<Errors, Success<String>> deleteImage() {
        return exec(Commands.deleteImageCMD(image),  DELETE_IMAGE_SUCCESS,true);
    }

    public static Result<Errors, Success<String>> startPodmanService() {
        return exec(Commands.startPodmanServiceCMD(), PODMAN_SERVICE_STARTED,false);
    }
}