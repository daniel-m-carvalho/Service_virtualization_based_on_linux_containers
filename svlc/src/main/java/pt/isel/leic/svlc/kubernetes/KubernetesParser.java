package pt.isel.leic.svlc.kubernetes;

import jakarta.xml.bind.JAXBException;
import pt.isel.leic.svlc.util.FromXml;
import pt.isel.leic.svlc.util.resources.Cluster;

import java.io.FileNotFoundException;

public class KubernetesParser {

    private Cluster cluster;

    public KubernetesParser(String filename) throws JAXBException, FileNotFoundException {
        super();
        this.setCluster(FromXml.clusterFromXml(filename));
    }

    public Cluster getCluster() {
        return this.cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

}
