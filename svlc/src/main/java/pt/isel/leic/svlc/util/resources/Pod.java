package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.openapi.models.*;
import jakarta.xml.bind.annotation.*;

import java.util.List;

/**
 * Represents a Kubernetes Pod.
 */
@XmlRootElement(name = "pod")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pod {

    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "containersList")
    @XmlElement(name = "container")
    private List<Container> containerList;

    @XmlElement(name = "imagePullPolicy")
    private String imagePullPolicy;

    @XmlElement(name = "imagePullSecret")
    private String imagePullSecret;

    public Pod() {
        super();
    }

    public Pod(String name, List<Container> containerList, String imagePullPolicy, String imagePullSecret) {
        super();
        this.setName(name);
        this.setContainerList(containerList);
        this.setImagePullPolicy(imagePullPolicy);
        this.setImagePullSecret(imagePullSecret);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }

    public String getImagePullPolicy() {
        return imagePullPolicy;
    }

    public void setImagePullPolicy(String imagePullPolicy) {
        this.imagePullPolicy = imagePullPolicy;
    }

    public String getImagePullSecret() {
        return imagePullSecret;
    }

    public void setImagePullSecret(String imagePullSecret) {
        this.imagePullSecret = imagePullSecret;
    }

    /**
     * Creates a Kubernetes Pod from the Pod object.
     * @return the Kubernetes Pod.
     */
    public V1Pod toV1Pod() {
        return new V1Pod().apiVersion("v1").kind("Pod").metadata(new V1ObjectMeta().name(name))
            .spec(new V1PodSpec().containers(containerList.stream().map(
                container -> container.toV1Container().imagePullPolicy(imagePullPolicy)).toList()
            ).imagePullSecrets(List.of(new V1LocalObjectReference().name(imagePullSecret))));
    }

    @Override
    public String toString() {
        return "Pod { \n" +
            "name='" + name + "',\n" +
            "containerList=" + containerList + ",\n" +
            "imagePullPolicy='" + imagePullPolicy + "',\n" +
            "imagePullSecret='" + imagePullSecret + "'\n" +
            '}';
    }
}
