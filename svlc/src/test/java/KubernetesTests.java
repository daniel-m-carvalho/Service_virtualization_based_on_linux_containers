
import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.models.*;
import org.junit.jupiter.api.Test;
import pt.isel.leic.svlc.util.kubernetes.KubeResource;
import pt.isel.leic.svlc.util.kubernetes.configurations.CreateConfig;
import pt.isel.leic.svlc.util.resources.*;

import static org.junit.jupiter.api.Assertions.*;

public class KubernetesTests {

    @Test
    public void testKubeResource() {
        KubeResource resource = (r, c) -> r;
        CreateConfig config = new CreateConfig();
        KubernetesObject obj = new V1Pod();
        try {
            V1Pod result = (V1Pod) resource.execute(obj, config);
            assertEquals(obj, result);
        } catch (ApiException e) {
            fail("Exception thrown" + e.getMessage());
        }
    }

    @Test
    public void testKubernetesObjects() {
        V1Pod obj = new Pod().toV1Pod();
        assertEquals("v1", obj.getApiVersion());
        assertEquals("Pod", obj.getKind());

        V1Secret secret = new Secret().toV1Secret();
        assertEquals("v1", secret.getApiVersion());
        assertEquals("Secret", secret.getKind());

        V1Service service = new Service().toV1Service();
        assertEquals("v1", service.getApiVersion());
        assertEquals("Service", service.getKind());

        V1Deployment deployment = new Deployment().toV1Deployment();
        assertEquals("apps/v1", deployment.getApiVersion());
        assertEquals("Deployment", deployment.getKind());

        V1ConfigMap configMap = new ConfigMap().toV1ConfigMap();
        assertEquals("v1", configMap.getApiVersion());
        assertEquals("ConfigMap", configMap.getKind());

        V1PersistentVolumeClaim pvc = new PersistentVolumeClaim().toV1PersistentVolumeClaim();
        assertEquals("v1", pvc.getApiVersion());
        assertEquals("PersistentVolumeClaim", pvc.getKind());

        V1PersistentVolume pv = new PersistentVolume().toV1PersistentVolume();
        assertEquals("v1", pv.getApiVersion());
        assertEquals("PersistentVolume", pv.getKind());
    }

}
