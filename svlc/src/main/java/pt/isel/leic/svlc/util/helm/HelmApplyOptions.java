package pt.isel.leic.svlc.util.helm;

public class HelmApplyOptions {

    public static void applyConfigOption(boolean condition, Runnable action) {
        if (condition) action.run();
    }

    public static void applyConfigOption(boolean condition, Runnable action, Runnable elseAction) {
        if (condition) action.run();
        else elseAction.run();
    }
}