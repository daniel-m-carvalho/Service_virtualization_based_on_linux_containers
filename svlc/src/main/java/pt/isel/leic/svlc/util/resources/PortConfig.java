package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1ServicePort;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "portConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class PortConfig {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "hostPort")
    private Integer hostPort;

    @XmlElement(name = "targetPort")
    private Integer targetPort;

    @XmlElement(name = "nodePort")
    private Integer nodePort;

    @XmlElement(name = "protocol")
    private String protocol;

    public PortConfig() {
        super();
    }

    public PortConfig(String name, Integer hostPort, Integer targetPort, Integer nodePort, String protocol) {
        super();
        this.setName(name);
        this.setHostPort(hostPort);
        this.setTargetPort(targetPort);
        this.setNodePort(nodePort);
        this.setProtocol(protocol);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHostPort() {
        return hostPort;
    }

    public void setHostPort(Integer hostPort) {
        this.hostPort = hostPort;
    }

    public Integer getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(Integer targetPort) {
        this.targetPort = targetPort;
    }

    public Integer getNodePort() {
        return nodePort;
    }

    public void setNodePort(Integer nodePort) {
        this.nodePort = nodePort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Converts this PortConfig to a V1ServicePort.
     * @return the V1ServicePort
     */
    public V1ServicePort toV1ServicePort() {
        return new V1ServicePort().name(name).targetPort(new IntOrString (targetPort))
                .port(hostPort).nodePort(nodePort).protocol(protocol);
    }

    /**
     * Converts this PortConfig to a V1ContainerPort.
     * @return the V1ContainerPort
     */
    public V1ContainerPort toV1ContainerPort() {
        return new V1ContainerPort().name(name).containerPort(targetPort).hostPort(hostPort).protocol(protocol);
    }

}
