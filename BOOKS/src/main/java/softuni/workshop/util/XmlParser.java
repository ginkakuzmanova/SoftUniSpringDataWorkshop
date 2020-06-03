package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    //path is used to read/write in the same file
    <T> T unmarshalling(Class<T> klass, String xmlPath) throws JAXBException;

    <T> void marshalling(Class<T> klass,T object, String xmlPath) throws JAXBException;

}
