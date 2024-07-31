package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceSpec;
import jakarta.xml.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Represents a Kubernetes Service.
 */
@XmlRootElement(name = "Service")
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "type")
    private String type;

    @XmlElementWrapper(name = "portConfigList")
    @XmlElement(name = "portConfig")
    private List<PortConfig> portConfigList;

    @XmlElement(name = "externalIP")
    private String externalIP;

    @XmlElementWrapper(name = "labels")
    @XmlElement(name = "entry")
    private Map<String, String> labels;

    @XmlElementWrapper(name = "selector")
    @XmlElement(name = "entry")
    private Map<String, String> selector;

    public Service() {
        super();
    }

    public Service(String name, String type, List<PortConfig> portConfigList, String externalIP, Map<String, String> labels, Map<String, String> selector) {
        super();
        this.setName(name);
        this.setType(type);
        this.setPortConfigList(portConfigList);
        this.setExternalIP(externalIP);
        this.setLabels(labels);
        this.setSelector(selector);
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

    public List<PortConfig> getPortConfigList() {
        return portConfigList;
    }

    public void setPortConfigList(List<PortConfig> portConfigList) {
        this.portConfigList = portConfigList;
    }

    public String getExternalIP() {
        return externalIP;
    }

    public void setExternalIP(String externalIP) {
        this.externalIP = externalIP;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public Map<String, String> getSelector() {
        return selector;
    }

    public void setSelector(Map<String, String> selector) {
        this.selector = selector;
    }

    /**
     * Converts this Service to a V1Service.
     * @return the V1Service
     */
    public V1Service toV1Service() {
        return new V1Service()
            .metadata(new V1ObjectMeta().name(name).labels(labels))
            .spec(new V1ServiceSpec().type(type).selector(selector)
                .externalIPs(List.of(externalIP))
                .ports(portConfigList.stream().map(PortConfig::toV1ServicePort).toList())
            );
    }

    @Override
    public String toString() {
        return "Service {\n" +
            "name='" + name + "',\n" +
            "type='" + type + "',\n" +
            "portConfigList=" + portConfigList + ",\n" +
            "externalIP='" + externalIP + '\n' +
            '}';
    }
}
