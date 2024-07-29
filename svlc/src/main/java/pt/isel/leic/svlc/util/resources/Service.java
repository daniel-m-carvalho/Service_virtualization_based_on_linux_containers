package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceSpec;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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

    @XmlElement(name = "portConfigList")
    private List<PortConfig> portConfigList;

    @XmlElement(name = "externalIP")
    private String externalIP;

    public Service() {
        super();
    }

    public Service(String name, String type, List<PortConfig> portConfigList, String externalIP) {
        super();
        this.setName(name);
        this.setType(type);
        this.setPortConfigList(portConfigList);
        this.setExternalIP(externalIP);
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

    public String getExternalIPList() {
        return externalIP;
    }

    public void setExternalIP(String externalIP) {
        this.externalIP = externalIP;
    }

    public V1Service toV1Service() {
        return new V1Service().apiVersion("v1").kind("Service").metadata(new V1ObjectMeta().name(name))
            .spec(new V1ServiceSpec().type(type).ports(
                portConfigList.stream().map(PortConfig::toV1ServicePort).toList()
            ).selector(Map.of("app", name)).externalIPs(List.of(externalIP)));
    }
}
