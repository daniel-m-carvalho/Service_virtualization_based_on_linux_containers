package pt.isel.leic.svlc.util.helm;

/**
 * Represents a functional interface that receives no arguments and returns a value.
 *
 * @param <T> The type of the value returned by the function.
 */
@FunctionalInterface
public interface helmProc<T> {
    T run();
}
