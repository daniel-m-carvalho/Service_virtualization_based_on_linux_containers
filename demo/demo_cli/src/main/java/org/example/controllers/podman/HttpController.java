package org.example.controllers.podman;

import pt.isel.leic.svlc.pod.http.PodmanHttp;
import pt.isel.leic.svlc.pod.cmd.PodmanCmd;
import pt.isel.leic.svlc.util.results.Failure;
import pt.isel.leic.svlc.util.results.Result;
import pt.isel.leic.svlc.util.results.Success;

import java.util.List;
import java.util.Map;

import static pt.isel.leic.svlc.util.executers.CommonExec.exec;

public class HttpController {
    public static void handleDeployHttp(String[] args) {
        PodmanHttp podmanHttp = new PodmanHttp(args[3], args[4], args[5]);
        Result<Failure, Success<Thread>> service = null;
        Map<String, Object> podPayload = Map.of(
            "name", args[3],
            "portmappings", List.of(
                podmanHttp.getPortsConf()
            )
        );
        try {
            service = exec(() -> PodmanCmd.startPodmanService(podmanHttp.getServiceIpPort())).print();
            podmanHttp.setPayload(podPayload);
            exec(podmanHttp::createPod).print().then(() -> {
                podmanHttp.setQueryString(
                   Map.of("reference", podmanHttp.getImage())
                );
               return exec(podmanHttp::pullImage);
            }).print()
            .then(() -> exec(
                () -> {
                    podmanHttp.setPayload(Map.of(
                        "image", podmanHttp.getImage()
                    ));
                    return podmanHttp.createContainer();
                }
            )).print().then(() -> {
                podmanHttp.reset();
                return exec(podmanHttp::startPod);
            }).print();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            if (service != null) {
                service.right().value().interrupt();
            }
        }
    }

    public static void handleStatsHttp(String[] args) {
        PodmanHttp podmanHttp = new PodmanHttp();
        podmanHttp.setPodName(args[3]);
        Result<Failure, Success<Thread>> service = null;
        try {
            service = exec(() -> PodmanCmd.startPodmanService(podmanHttp.getServiceIpPort())).print();
            podmanHttp.setPayload(Map.of());
            exec(podmanHttp::inspectPod).print().complete();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            if (service != null) service.right().value().interrupt();
        }
    }
}
