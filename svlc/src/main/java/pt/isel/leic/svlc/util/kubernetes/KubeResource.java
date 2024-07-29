package pt.isel.leic.svlc.util.kubernetes;

import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import pt.isel.leic.svlc.util.kubernetes.configurations.CreateConfig;

/**
 * Represents a functional interface that represents a Kubernetes resource.
 */
@FunctionalInterface
public interface KubeResource {
     KubernetesObject execute(KubernetesObject resource, CreateConfig config) throws ApiException;
}
