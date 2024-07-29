package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.openapi.models.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Map;

/**
 * Represents a Kubernetes Deployment.
 */
@XmlRootElement(name = "deployment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deployment {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "replicas")
    private Integer replicas;

    @XmlElement(name = "containerList")
    private List<Container> containerList;

    @XmlElement(name = "imagePullPolicy")
    private String imagePullPolicy;

    @XmlElement(name = "imagePullSecret")
    private String imagePullSecret;

    public Deployment() {
        super();
    }

    public Deployment(String name, Integer replicas, List<Container> containerList, String imagePullPolicy, String imagePullSecret) {
        super();
        this.setName(name);
        this.setReplicas(replicas);
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

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
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
     * Converts this Deployment to a V1Deployment.
     * @return the V1Deployment
     */
    public V1Deployment toV1Deployment() {
        return new V1Deployment().apiVersion("apps/v1").kind("Deployment").metadata(new V1ObjectMeta().name(name))
            .spec(new V1DeploymentSpec().replicas(replicas).selector(new V1LabelSelector().matchLabels(Map.of("app", name)))
            .template(new V1PodTemplateSpec().metadata(new V1ObjectMeta().labels(Map.of("app", name)))
            .spec(new V1PodSpec().containers(containerList.stream().map(
                container -> container.toV1Container().imagePullPolicy(imagePullPolicy)).toList()
            ).imagePullSecrets(List.of(new V1LocalObjectReference().name(imagePullSecret))))));
    }
}
