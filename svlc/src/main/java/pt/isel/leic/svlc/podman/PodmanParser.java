package pt.isel.leic.svlc.podman;

import jakarta.xml.bind.JAXBException;
import pt.isel.leic.svlc.util.FromXml;
import pt.isel.leic.svlc.util.resources.Pod;

import java.io.FileNotFoundException;

public class PodmanParser {

        private Pod pod;

        public PodmanParser(String fileName) throws JAXBException, FileNotFoundException {
                super();
                this.setPod(FromXml.podFromXml(fileName));
        }

        public Pod getPod() {
                return this.pod;
        }

        public void setPod(Pod pod) {
                this.pod = pod;
        }
}
