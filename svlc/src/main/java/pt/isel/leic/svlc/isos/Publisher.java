package pt.isel.leic.svlc.isos;

import static pt.isel.leic.svlc.util.executers.CmdExec.executeCommand;

public class Publisher {

    public static String publish(String iSoSPath) {
        String[] cmd = new String[]{ iSoSPath + ".cmd" };
        try {
            return executeCommand(cmd, "ISoS(" + iSoSPath + ") published!", true).getOutput();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
