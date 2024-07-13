package pt.isel.leic.svlc.util.executers;

public class CommandResult {
    private final String output;
    private final Thread thread;

    private CommandResult(String output, Thread thread) {
        this.output = output;
        this.thread = thread;
    }

    public static CommandResult fromOutput(String output) {
        return new CommandResult(output, null);
    }

    public static CommandResult fromThread(Thread thread) {
        return new CommandResult(null, thread);
    }

    public String getOutput() {
        return output;
    }

    public Thread getThread() {
        return thread;
    }
}
