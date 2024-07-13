package pt.isel.leic.svlc.util.kubernetes;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * Represents a functional interface that represents a Kubernetes resource.
 */
@FunctionalInterface
public interface KubeResource {
     HasMetadata execute(KubernetesClient client, String namespace, HasMetadata resource);
}
