package pt.isel.leic.svlc.util.executers;

import pt.isel.leic.svlc.util.DefaultProc;
import pt.isel.leic.svlc.util.results.Failure;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Success;

import static pt.isel.leic.svlc.util.results.Result.Left;
import static pt.isel.leic.svlc.util.results.Result.Right;

public class CommonExec {
    /**
     * Executes any command as long as it respects the {@link DefaultProc} functional interface.
     * @param proc The command to be executed.
     * @return An {@link Result} containing an {@link Failure} object in case of failure, or a success message as {@link String} in case of success.
     */
    public static <T> Result<Failure, Success<T>> exec(DefaultProc<T> proc){
        try {
            return Right(new Success<>(proc.run()));
        } catch (Exception e) {
            return Left(new Failure(e.getMessage()));
        }
    }
}
