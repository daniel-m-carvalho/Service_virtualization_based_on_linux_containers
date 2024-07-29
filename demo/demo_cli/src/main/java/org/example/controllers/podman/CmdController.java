package org.example.controllers.podman;

import org.example.controllers.isos.Publisher;
import pt.isel.leic.svlc.pod.cmd.PodmanCmd;

import static pt.isel.leic.svlc.util.executers.CommonExec.exec;

public class CmdController {
    public static void handleDeployCmd(String[] args) {
        String imageName = args[3].split("/")[2];
        PodmanCmd podmanCmd = new PodmanCmd(args[2], args[3], args[4]);
        try {
            exec(podmanCmd::createPod)
                    .then(() -> exec(podmanCmd::pullImage)).print()
                    .then(() -> exec(podmanCmd::deployInPod)).print()
                    .complete();
            Publisher.publish(imageName);
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void handleStatsCmd(String[] args) {
        PodmanCmd podmanCmd = new PodmanCmd();
        podmanCmd.setPodName(args[2]);
        try {
            exec(podmanCmd::inspectPod).print().complete();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
