package pt.isel.leic.svlc.util.resources;

import io.kubernetes.client.openapi.models.V1Container;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "container")
@XmlAccessorType(XmlAccessType.FIELD)
public class Container {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "image")
    private String image;

    @XmlElement(name = "portConfigList")
    private List<PortConfig> portConfigList;

    public Container() {
        super();
    }

    public Container(String name, String image, List<PortConfig> portConfigList) {
        super();
        this.setName(name);
        this.setImage(image);
        this.setPortConfigList(portConfigList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<PortConfig> getPortConfigList() {
        return portConfigList;
    }

    public void setPortConfigList(List<PortConfig> portConfigList) {
        this.portConfigList = portConfigList;
    }

    /**
     * Converts this Container to a V1Container.
     * @return the V1Container
     */
    public V1Container toV1Container() {
        return new V1Container().name(name + "-container").image(image).ports(
                portConfigList.stream().map(PortConfig::toV1ContainerPort).collect(Collectors.toList())
        );
    }
}
