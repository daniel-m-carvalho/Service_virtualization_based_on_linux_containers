package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.models.V1HostPathVolumeSource;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PersistentVolume;
import io.kubernetes.client.openapi.models.V1PersistentVolumeSpec;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a Kubernetes Persistent Volume.
 */
@XmlRootElement(name = "persistentVolume")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersistentVolume {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "capacity")
    private String capacity;

    @XmlElement(name = "storageClass")
    private String storageClass;

    @XmlElement(name = "accessModes")
    private String accessModes;

    @XmlElement(name = "hostPath")
    private String hostPath;

    public PersistentVolume() {
        super();
    }

    public PersistentVolume(String name, String capacity, String storageClass, String accessModes, String hostPath) {
        super();
        this.setName(name);
        this.setCapacity(capacity);
        this.setStorageClass(storageClass);
        this.setAccessModes(accessModes);
        this.setHostPath(hostPath);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    public String getAccessModes() {
        return accessModes;
    }

    public void setAccessModes(String accessModes) {
        this.accessModes = accessModes;
    }

    public String getHostPath() {
        return hostPath;
    }

    public void setHostPath(String hostPath) {
        this.hostPath = hostPath;
    }

    public V1PersistentVolume toV1PersistentVolume() {
        return new V1PersistentVolume().apiVersion("v1").kind("PersistentVolume").metadata(new V1ObjectMeta().name(name))
            .spec(new V1PersistentVolumeSpec().storageClassName(storageClass).accessModes(
                List.of(accessModes)).capacity(Map.of("storage", new Quantity(capacity)))
                    .hostPath(new V1HostPathVolumeSource().path(hostPath))
            );
    }

    @Override
    public String toString() {
        return "PersistentVolume {\n" +
            "name='" + name + "',\n" +
            "capacity='" + capacity + "',\n" +
            "storageClass='" + storageClass + "',\n" +
            "accessModes='" + accessModes + "',\n" +
            "hostPath='" + hostPath + "'\n" +
            '}';
    }
}
