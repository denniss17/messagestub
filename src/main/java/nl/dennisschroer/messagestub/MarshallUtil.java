package nl.dennisschroer.messagestub;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import java.io.StringReader;
import java.io.StringWriter;

public class MarshallUtil {
    /**
     * Marshall java naar XML.
     */
    public static String marshall(Object bericht) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(bericht.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(bericht, writer);

        return writer.toString();
    }

    /**
     * Marshall java naar XML en schrijf dit naar result.
     */
    public static void marshall(Object bericht, Result result) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(bericht.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.marshal(bericht, result);
    }

    /**
     * Marshall XML naar java
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshall(String content, Class<T> type) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(type.getPackage().getName());
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return ((JAXBElement<T>) jaxbUnmarshaller.unmarshal(new StringReader(content))).getValue();
    }
}
