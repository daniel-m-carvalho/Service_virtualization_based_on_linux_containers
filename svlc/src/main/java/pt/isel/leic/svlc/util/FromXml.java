package pt.isel.leic.svlc.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import pt.isel.leic.svlc.util.resources.Cluster;
import pt.isel.leic.svlc.util.resources.Pod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FromXml {

    public static Cluster clusterFromXml(String fileName) throws JAXBException, FileNotFoundException {
        return (Cluster) fromXml(fileName, Cluster.class);
    }

    public static Pod podFromXml(String fileName) throws JAXBException, FileNotFoundException {
        return (Pod) fromXml(fileName, Pod.class);
    }

    private static Object fromXml(String fileName, Class<?> clazz) throws JAXBException, FileNotFoundException {
        // Create JAXB Context
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

        // Create Unmarshaller
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        // Create the input source
        InputStream inStream = new FileInputStream(fileName);

        // Read the instance from XML
        return jaxbUnmarshaller.unmarshal(inStream);
    }
}
