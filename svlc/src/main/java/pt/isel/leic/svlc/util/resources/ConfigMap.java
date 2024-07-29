package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.openapi.models.V1ConfigMap;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Map;

/**
 * Represents a Kubernetes ConfigMap.
 */
@XmlRootElement(name = "configMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigMap {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "data")
    private Map<String, String> data;

    public ConfigMap() {
        super();
    }

    public ConfigMap(String name, Map<String, String> data) {
        super();
        this.setName(name);
        this.setData(data);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public V1ConfigMap toV1ConfigMap() {
        return new V1ConfigMap().apiVersion("v1").kind("ConfigMap")
            .metadata(new V1ObjectMeta().name(name)).data(data);
    }
}
