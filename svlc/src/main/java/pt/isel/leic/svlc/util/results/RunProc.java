package pt.isel.leic.svlc.util.results;

/**
 * Represents a functional interface that takes no arguments and returns an {@link Result} object.
 * @param <L> The type of the left value in the {@link Result} object.
 * @param <R> The type of the right value in the {@link Result} object.
 */
@FunctionalInterface
public interface RunProc<L, R> {
    Result<L, R> run();
}