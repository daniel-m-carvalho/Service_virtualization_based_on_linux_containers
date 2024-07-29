import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isel.leic.svlc.podman.cmd.PodmanCmd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pt.isel.leic.svlc.util.podman.Messages.*;

public class PodmanCmdTests {

    private PodmanCmd podmanCmd;

    @BeforeEach
    void setUp() {
        podmanCmd = new PodmanCmd();
    }

    @Test
    void testSetPodName() {
        podmanCmd.setPodName("newPodName");
        assertEquals("newPodName", podmanCmd.getPodName());
    }

    @Test
    void testSetImage() {
        podmanCmd.setImage("newImage");
        assertEquals("newImage", podmanCmd.getImage());
    }

    @Test
    void testSetPorts() {
        podmanCmd.setPorts("8080:8080");
        assertEquals("8080:8080", podmanCmd.getPorts());
    }

    @Test
    void testCreateStartStopAndDeletePod() throws Exception {
        // Create pod
        podmanCmd.setPodName("testPod");
        podmanCmd.setPorts("8080:8080");
        String result = podmanCmd.createPod();
        assertEquals(CREATE_POD_SUCCESS, result);

        // Start pod
        result = podmanCmd.startPod();
        assertEquals(START_POD_SUCCESS, result);

        // Stop pod
        result = podmanCmd.stopPod();
        assertEquals(STOP_POD_SUCCESS, result);

        // Delete pod
        result = podmanCmd.deletePod();
        assertEquals(DELETE_POD_SUCCESS, result);
    }

    @Test
    void testPullAndDeleteImage() throws Exception {
        // Image does not exist
        podmanCmd.setImage("nginx");
        String result = podmanCmd.pullImage();
        assertEquals(PULL_IMAGE_SUCCESS, result);

        // Image exists
        result = podmanCmd.pullImage();
        assertEquals(IMAGE_EXISTS, result);

        // Delete image
        result = podmanCmd.deleteImage();
        assertEquals(DELETE_IMAGE_SUCCESS, result);
    }

    @Test
    void testCreateContainer() throws Exception {
        // Create container
        podmanCmd.setImage("nginx");
        String result = podmanCmd.createContainer("testContainer");
        assertEquals(CREATE_CONTAINER_SUCCESS, result);

        // Delete container
        result = podmanCmd.deleteContainer("testContainer");
        assertEquals(DELETE_CONTAINER_SUCCESS, result);

        // Delete image
        result = podmanCmd.deleteImage();
        assertEquals(DELETE_IMAGE_SUCCESS, result);
    }

    @Test
    void testDeployAndInspectPod() throws Exception {
        // Create pod
        podmanCmd.setPodName("testPod");
        podmanCmd.setPorts("8080:8080");
        String result = podmanCmd.createPod();
        assertEquals(CREATE_POD_SUCCESS, result);

        // Deploy in pod
        podmanCmd.setImage("nginx");
        result = podmanCmd.deployInPod();
        assertEquals(DEPLOY_IN_POD_SUCCESS, result);

        // Get pod statistics
        result = podmanCmd.inspectPod();
        assertNotNull(result);

        // Delete pod
        result = podmanCmd.deletePod();
        assertEquals(DELETE_POD_SUCCESS, result);

        // Delete image
        podmanCmd.setImage("nginx");
        result = podmanCmd.deleteImage();
        assertEquals(DELETE_IMAGE_SUCCESS, result);
    }

    @Test
    void testStartPodmanService() throws Exception {
        Thread result = PodmanCmd.startPodmanService("8080");
        assertEquals(Thread.State.RUNNABLE, result.getState());
        result.interrupt();
    }
}
