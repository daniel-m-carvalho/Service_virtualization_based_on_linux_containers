package pt.isel.leic.svlc.pod;

import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Errors;
import pt.isel.leic.svlc.util.results.Success;

/**
 * This interface defines the operations that a Podman runner should support.
 * The Podman runner can be a command line tool or an HTTP client.
 * The operations include creating, starting, stopping, and managing pods and containers.
 * As well as pulling and deleting images.
 */
public interface Podman {
    Result<Errors, Success<String>> createPod();
    Result<Errors, Success<String>> startPod();
    Result<Errors, Success<String>> stopPod();
    Result<Errors, Success<String>> prunePods();
    Result<Errors, Success<String>> getPodStatistics();
    Result<Errors, Success<String>> createContainer();
    Result<Errors, Success<String>> pullImage();
    Result<Errors, Success<String>> deleteImage();
}
