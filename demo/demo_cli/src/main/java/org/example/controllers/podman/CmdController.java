package org.example.controllers.podman;

import pt.isel.leic.svlc.pod.cmd.PodmanCmd;

public class CmdController {
    public static void handleDeployCmd(String[] args) {
        PodmanCmd podmanCmd = new PodmanCmd(args[1], args[2], args[3]);
        podmanCmd.createPod().then(podmanCmd::pullImage).then(podmanCmd::deployInPod).complete();
    }
}
