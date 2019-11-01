package nl.dennisschroer.messagestub;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.StringReader;
import java.io.StringWriter;

public class MarshallUtil {
    /**
     * Marshall java naar XML.
     */
    public static String marshall(Object bericht) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(bericht instanceof JAXBElement ? ((JAXBElement) bericht).getDeclaredType() : bericht.getClass());
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
        Unmarshaller jaxbUnmarshaller = unmarshaller(type);
        return ((JAXBElement<T>) jaxbUnmarshaller.unmarshal(new StringReader(content))).getValue();
    }

    /**
     * Geeft de {@link Unmarshaller} waarmee het gegeven type ge-unmarshalled kan worden.
     */
    public static Unmarshaller unmarshaller(Class<?> type) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(type.getPackage().getName());
        return jaxbContext.createUnmarshaller();
    }

    /**
     * Haal uit een {@link Source} de XML van het onderliggende bericht.
     */
    public static String getXmlFromSource(Source source, QName name) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(name.getNamespaceURI());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object object = unmarshaller.unmarshal(source);

        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);

        return writer.toString();
    }
}
