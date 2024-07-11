package pt.isel.leic.svlc.pod;

import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Failure;
import pt.isel.leic.svlc.util.results.Success;

/**
 * This interface defines the operations that a Podman runner should support.
 * The Podman runner can be a command line tool or an HTTP client.
 * The operations include creating, starting, stopping, and managing pods and containers.
 * As well as pulling and deleting images.
 */
public interface Podman {
    Result<Failure, Success<String>> createPod();
    Result<Failure, Success<String>> startPod();
    Result<Failure, Success<String>> stopPod();
    Result<Failure, Success<String>> prunePods();
    Result<Failure, Success<String>> getPodStatistics();
    Result<Failure, Success<String>> createContainer();
    Result<Failure, Success<String>> pullImage();
    Result<Failure, Success<String>> deleteImage();
}
