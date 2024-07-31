package pt.isel.leic.svlc.util.resources;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cluster")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cluster {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "namespace")
    private String namespace;

    @XmlElement(name = "portForward")
    private Integer portForward;

    @XmlElement(name = "secret")
    private Secret secret;

    @XmlElement(name = "pod")
    private Pod pod;

    @XmlElement(name = "service")
    private Service service;

    @XmlElement(name = "deployment")
    private Deployment deployment;

    @XmlElement(name = "configMap")
    private ConfigMap configMap;

    @XmlElement(name = "persistentVolume")
    private PersistentVolume persistentVolume;

    @XmlElement(name = "persistentVolumeClaim")
    private PersistentVolumeClaim persistentVolumeClaim;

    public Cluster() {
        super();
    }

    public Cluster(String name, String namespace, Integer portForward, Secret secret, Pod pod, Service service, Deployment deployment, ConfigMap configMap, PersistentVolume persistentVolume, PersistentVolumeClaim persistentVolumeClaim) {
        super();
        this.setName(name);
        this.setNamespace(namespace);
        this.setPortForward(portForward);
        this.setSecret(secret);
        this.setPod(pod);
        this.setService(service);
        this.setDeployment(deployment);
        this.setConfigMap(configMap);
        this.setPersistentVolume(persistentVolume);
        this.setPersistentVolumeClaim(persistentVolumeClaim);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Integer getPortForward() {
        return this.portForward;
    }

    public void setPortForward(Integer portForward) {
        this.portForward = portForward;
    }

    public Secret getSecret() {
        return this.secret;
    }

    public void setSecret(Secret secret) {
        this.secret = secret;
    }

    public Pod getPod() {
        return this.pod;
    }

    public void setPod(Pod pod) {
        this.pod = pod;
    }

    public Service getService() {
        return this.service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Deployment getDeployment() {
        return this.deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public ConfigMap getConfigMap() {
        return this.configMap;
    }

    public void setConfigMap(ConfigMap configMap) {
        this.configMap = configMap;
    }

    public PersistentVolume getPersistentVolume() {
        return this.persistentVolume;
    }

    public void setPersistentVolume(PersistentVolume persistentVolume) {
        this.persistentVolume = persistentVolume;
    }

    public PersistentVolumeClaim getPersistentVolumeClaim() {
        return this.persistentVolumeClaim;
    }

    public void setPersistentVolumeClaim(PersistentVolumeClaim persistentVolumeClaim) {
        this.persistentVolumeClaim = persistentVolumeClaim;
    }

    @Override
    public String toString() {
        return "Cluster {\n" +
            "name='" + name + "',\n" +
            "namespace='" + namespace + "',\n" +
            "secret=" + secret + ",\n" +
            "pod=" + pod + ",\n" +
            "service=" + service + ",\n" +
            "deployment=" + deployment + ",\n" +
            "configMap=" + configMap + ",\n" +
            "persistentVolume=" + persistentVolume + ",\n" +
            "persistentVolumeClaim=" + persistentVolumeClaim + '\n' +
            '}';
    }
}
