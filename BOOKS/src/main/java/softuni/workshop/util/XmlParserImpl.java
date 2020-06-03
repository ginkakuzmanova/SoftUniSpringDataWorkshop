package softuni.workshop.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParserImpl implements XmlParser {

    @Override
    public <T> T unmarshalling(Class<T> klass, String xmlPath) throws JAXBException {
        File file = new File(xmlPath);
        JAXBContext jaxbContext = JAXBContext.newInstance(klass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        T obj = (T) unmarshaller.unmarshal(file);

        return (T) obj;
    }

    @Override
    public <T> void marshalling(Class<T> klass, T object, String xmlPath) throws JAXBException {
        File file = new File(xmlPath);
        JAXBContext jaxbContext = JAXBContext.newInstance(klass);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, file);

    }
}
