package org.example.controllers.podman;

import pt.isel.leic.svlc.isos.Publisher;
import pt.isel.leic.svlc.podman.cmd.PodmanCmd;
import pt.isel.leic.svlc.util.FromXml;
import pt.isel.leic.svlc.util.resources.Pod;

import static pt.isel.leic.svlc.util.executers.CommonExec.exec;

public class CmdController {
    public static void handleDeployCmd(String[] args) {
        try {
            Pod pod = FromXml.podFromXml(".\\ConfigFiles\\" + args[2]);
            String ports = pod.getContainerList().get(0).getPortConfigList().get(0).getHostPort() + ":" + pod.getContainerList().get(0).getPortConfigList().get(0).getTargetPort();
            PodmanCmd podmanCmd = new PodmanCmd(pod.getName(), pod.getContainerList().get(0).getImage(), ports);
            String imageName = pod.getContainerList().get(0).getImage().split("/")[2];
            exec(podmanCmd::createPod)
                .then(() -> exec(podmanCmd::pullImage)).print()
                .then(() -> exec(podmanCmd::deployInPod)).print()
                .complete();
            exec(() -> Publisher.publish(".\\isos\\" + imageName)).print().complete();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void handleInspectCmd(String[] args) {
        PodmanCmd podmanCmd = new PodmanCmd();
        podmanCmd.setPodName(args[2]);
        try {
            exec(podmanCmd::inspectPod).print().complete();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
