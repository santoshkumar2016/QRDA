
package com.nalashaa.poc;

/*import java.io.File;*/

/*import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
*/
public class TestXml {

   /* public static void main(String[] args) {
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlImportConfig.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            // specify the location and name of xml file to be read
            File XMLfile = new File("KeyWordFromConfig.xml");
            // this will create Java object - country from the XML file
            XmlImportConfig importCOnfig = (XmlImportConfig) jaxbUnmarshaller.unmarshal(XMLfile);
            for (IndexFields indexFields : importCOnfig.getListOfIndexFields()) {
                System.out.println("get Index Field value  " + indexFields.getValue());
            }
            System.out.println("length of indexFields " + importCOnfig.getListOfIndexFields());
        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }
    }
*/
}