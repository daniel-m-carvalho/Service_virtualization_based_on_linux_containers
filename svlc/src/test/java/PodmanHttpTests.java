import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isel.leic.svlc.podman.cmd.PodmanCmd;
import pt.isel.leic.svlc.podman.http.PodmanHttp;
import pt.isel.leic.svlc.podman.http.Requests;

import java.util.List;
import java.util.Map;

//Only testing in Linux environment
public class PodmanHttpTests {

    private PodmanHttp podmanHttp;
    private Thread service;

    @BeforeEach
    void setUp() {
        podmanHttp = new PodmanHttp();
        try {
            service = PodmanCmd.startPodmanService(podmanHttp.getServiceIpPort());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        service.interrupt();
    }

    @Test
    public void testCreateRunStopAndDeletePod() throws Exception {
        // Create pod
        String expected = "http://"+ podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/pods/create";
        String result = Requests.createPodHTTP(podmanHttp.getServiceIpPort());
        assert(result.equals(expected));

        podmanHttp.setPodName("testPod");
        podmanHttp.setPortsConf(
            Map.of(
                "host_ip", "0.0.0.0",
                "container_port", 8080,
                "host_port", 8080
            )
        );
        podmanHttp.setPayload(
            Map.of(
                "name", "testPod",
                "portmappings", List.of(podmanHttp.getPortsConf())
            )
        );
        assert(podmanHttp.createPod().get("status").equals(201));

        // Start pod
        expected = "http://"+ podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/pods/testPod/start";
        result = Requests.startPodHTTP(podmanHttp.getServiceIpPort(), podmanHttp.getPodName());
        assert(result.equals(expected));
        assert(podmanHttp.startPod().get("status").equals(200));

        // Stop pod
        expected = "http://"+ podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/pods/testPod/stop";
        result = Requests.stopPodHTTP(podmanHttp.getServiceIpPort(), podmanHttp.getPodName());
        assert(result.equals(expected));

        assert(podmanHttp.stopPod().get("status").equals(200));

        // Delete pod
        expected = "http://"+ podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/pods/testPod/kill";
        result = Requests.deletePodHTTP(podmanHttp.getServiceIpPort(), podmanHttp.getPodName());
        assert(result.equals(expected));
        assert(podmanHttp.deletePod().get("status").equals(200));
    }

    @Test
    public void testCreateContainer() throws Exception {
        // Create container
        String expected = "http://" + podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/containers/create";
        String result = Requests.createContainerHTTP(podmanHttp.getServiceIpPort());
        assert (result.equals(expected));

        // Create container
        expected = "http://" + podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/containers/create";
        result = Requests.createContainerHTTP(podmanHttp.getServiceIpPort());
        assert (result.equals(expected));
        podmanHttp.setPayload(
            Map.of(
                "image", "nginx",
                "name", "testContainer"
            )
        );
        assert (podmanHttp.createContainer().get("status").equals(201));

        // Delete Container
        expected = "http://" + podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/containers/testContainer";
        result = Requests.deleteContainerHTTP("testContainer", podmanHttp.getServiceIpPort());
        assert (result.equals(expected));
        assert (podmanHttp.deleteContainer("testContainer").get("status").equals(200));
    }

    @Test
    public void testPullAndDeleteImage() throws Exception {
        // Pull image
        String expected = "http://" + podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/images/pull";
        String result = Requests.pullImageHTTP(podmanHttp.getServiceIpPort());
        assert (result.equals(expected));

        podmanHttp.setImage("nginx");
        podmanHttp.setQueryString(
            Map.of(
                "reference", podmanHttp.getImage()
            )
        );
        assert (podmanHttp.pullImage().get("status").equals(200));

        // Delete image
        expected = "http://" + podmanHttp.getServiceIpPort() + "/v5.0.0/libpod/images/nginx";
        result = Requests.deleteImageHTTP("nginx", podmanHttp.getServiceIpPort());
        assert (result.equals(expected));
        assert (podmanHttp.deleteImage().get("status").equals(200));
    }
}
