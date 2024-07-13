package pt.isel.leic.svlc.util.executers;

import pt.isel.leic.svlc.util.DefaultProc;

public class ExecIfElse {

    public static <T> T execIf(boolean condition, DefaultProc<T> action) throws Exception {
        return condition ? action.run() : null;
    }

    public static <T> T execIfElse(boolean condition, DefaultProc<T> action, DefaultProc<T> elseAction) throws Exception {
        return condition ? action.run() : elseAction.run();
    }
}