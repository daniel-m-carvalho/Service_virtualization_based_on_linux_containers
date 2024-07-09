package org.example.controllers.podman;

import pt.isel.leic.svlc.pod.http.PodmanHttp;
import pt.isel.leic.svlc.pod.cmd.PodmanCmd;

public class HttpController {
    public static void handleDeployHttp(String[] args) {
        PodmanHttp podmanHttp = new PodmanHttp(args[2], args[3], args[4]);
        PodmanCmd.startPodmanService().then(podmanHttp::createPod)
                .then(podmanHttp::pullImage)
                .then(podmanHttp::createContainer)
                .then(podmanHttp::startPod).complete();
    }
}
