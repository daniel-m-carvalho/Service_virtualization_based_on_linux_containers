package pt.isel.leic.svlc.util.results;

import pt.isel.leic.svlc.util.DefaultProc;

import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIf;
import static pt.isel.leic.svlc.util.executers.ExecIfElse.execIfElse;

/**
 * This class represents a container object that may either contain a failure (left) or a success (right) value.
 * It is used to handle operations that can either succeed or fail.
 *
 * @param <L> The type of the failure value.
 * @param <R> The type of the success value.
 */
public record Result<L, R>(L left, R right) {
    /**
     * Constructs an Either object with a failure (left) and a success (right) value.
     *
     * @param left  The failure value.
     * @param right The success value.
     */
    public Result {
    }

    /**
     * Creates an Either object with a failure (left) value.
     *
     * @param value The failure value.
     * @param <L>   The type of the failure value.
     * @param <R>   The type of the success value.
     * @return An Either object with a failure value.
     */
    public static <L, R> Result<L, R> Left(L value) {
        return new Result<>(value, null);
    }

    /**
     * Creates an Either object with a success (right) value.
     *
     * @param value The success value.
     * @param <L>   The type of the failure value.
     * @param <R>   The type of the success value.
     * @return An Either object with a success value.
     */
    public static <L, R> Result<L, R> Right(R value) {
        return new Result<>(null, value);
    }

    /**
     * Gets the success (right) value of the Either object.
     *
     * @return The success value.
     */
    @Override
    public R right() {
        return right;
    }

    /**
     * Gets the failure (left) value of the Either object.
     *
     * @return The failure value.
     */
    @Override
    public L left() {
        return left;
    }

    /**
     * Checks if the Either object contains a success (right) value.
     *
     * @return true if the Either object contains a success value, false otherwise.
     */
    public boolean success() {
        return right != null;
    }

    /**
     * Executes a given runner if the operation succeeded.
     *
     * @param resultProc The runner to be executed.
     * @return The result of the runner if the operation succeeded.
     * @throws RuntimeException If the operation failed.
     */
    public Result<L, R> then(DefaultProc<Result<L,R>> resultProc) throws Exception {
        return execIfElse(
            success() && resultProc != null,
            resultProc,
            () -> {
                throw new Exception(left.toString());
            }
        );
    }

    /**
     * Prints the success (right) value of the Either object.
     * If the operation failed, it does nothing.
     * @return The Result object.
     */
    public Result<L, R> print() {
        if (success()) System.out.println(right);
        return this;
    }

    /**
     * This method is used when the operation is the last in a chain of operations.
     *  It throws a RuntimeException if the operation failed.
     */
    public void complete() throws Exception{
        execIf(
            !success(),
            () -> {
                throw new Exception(left.toString());
            }
        );
    }
}