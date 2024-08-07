package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Secret;
import jakarta.xml.bind.annotation.*;

import java.util.Map;

/**
 * Represents a Kubernetes Secret.
 */
@XmlRootElement(name = "secret")
@XmlAccessorType(XmlAccessType.FIELD)
public class Secret {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "type")
    private String type;

    @XmlElementWrapper(name = "labels")
    @XmlElement(name = "entry")
    private Map<String, String> labels;

    private Map<String, byte []> data;

    public Secret() {
        super();
    }

    public Secret(String name, String type, Map<String, String> labels) {
        super();
        this.setName(name);
        this.setType(type);
        this.setLabels(labels);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public Map<String, byte []> getData() {
        return data;
    }

    public void setData(Map<String, byte []> data) {
        this.data = data;
    }

    /**
     * Converts this object to a V1Secret object.
     * @return the V1Secret object.
     */
    public V1Secret toV1Secret() {
        return new V1Secret().apiVersion("v1").kind("Secret").metadata(new V1ObjectMeta()
            .name(name).labels(labels)).data(data).type(type);
    }

    @Override
    public String toString() {
        return "Secret {\n" +
            "name='" + name + "',\n" +
            "type='" + type + "',\n" +
            "labels=" + labels + ",\n" +
            "data=" + data + '\n' +
            '}';
    }
}
