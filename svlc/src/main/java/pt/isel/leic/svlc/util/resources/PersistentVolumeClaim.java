package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PersistentVolumeClaim;
import io.kubernetes.client.openapi.models.V1PersistentVolumeClaimSpec;
import io.kubernetes.client.openapi.models.V1VolumeResourceRequirements;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Map;

/**
 * Represents a Kubernetes Persistent Volume Claim.
 */
@XmlRootElement(name = "PersistentVolumeClaim")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersistentVolumeClaim {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "storageClass")
    private String storageClass;

    @XmlElement(name = "accessModes")
    private String accessModes;

    @XmlElement(name = "storage")
    private String storage;

    public PersistentVolumeClaim() {
        super();
    }

    public PersistentVolumeClaim(String name, String storageClass, String accessModes, String storage) {
        super();
        this.setName(name);
        this.setStorageClass(storageClass);
        this.setAccessModes(accessModes);
        this.setStorage(storage);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    /**
     * Converts this PersistentVolumeClaim to a V1PersistentVolumeClaim.
     * @return the V1PersistentVolumeClaim
     */
    public V1PersistentVolumeClaim toV1PersistentVolumeClaim() {
        return new V1PersistentVolumeClaim().metadata(new V1ObjectMeta().name(this.getName()))
            .spec(new V1PersistentVolumeClaimSpec()
                .storageClassName(this.getStorageClass())
                .accessModes(List.of(this.getAccessModes()))
                .resources(new V1VolumeResourceRequirements()
                    .requests(Map.of("storage", new Quantity(this.getStorage())))
                )
            );
    }

    @Override
    public String toString() {
        return "PersistentVolumeClaim {\n" +
            "name='" + name + "',\n" +
            "storageClass='" + storageClass + "',\n" +
            "accessModes='" + accessModes + "',\n" +
            "storage='" + storage + "'\n" +
            '}';
    }
}
