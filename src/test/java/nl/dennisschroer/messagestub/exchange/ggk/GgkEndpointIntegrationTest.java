package nl.dennisschroer.messagestub.exchange.ggk;

import nl.dennisschroer.messagestub.SoapActionRequestCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

import static org.springframework.ws.test.server.ResponseMatchers.noFault;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GgkEndpointIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void verstuurDi01() {
        // Given
        Source requestPayload = new StreamSource(getResource("exchange/ggk/soap-envelope-di01.xml"));

        // When
        mockClient
                .sendRequest(SoapActionRequestCreator.builder()
                        .soapEnvelope(requestPayload)
                        .soapAction("http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Di01")
                        .build())

                // Then
                .andExpect(noFault());
    }

    @Test
    public void verstuurDu01() {
        // Given
        Source requestPayload = new StreamSource(getResource("exchange/ggk/soap-envelope-du01.xml"));

        // When
        mockClient
                .sendRequest(SoapActionRequestCreator.builder()
                        .soapEnvelope(requestPayload)
                        .soapAction("http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Du01")
                        .build())

                // Then
                .andExpect(noFault());
    }

    @Test
    public void verstuurFo01() {
        // Given
        Source requestPayload = new StreamSource(getResource("exchange/ggk/soap-envelope-fo01.xml"));

        // When
        mockClient
                .sendRequest(SoapActionRequestCreator.builder()
                        .soapEnvelope(requestPayload)
                        .soapAction("http://www.egem.nl/StUF/StUF0301/Fo01")
                        .build())

                // Then
                .andExpect(noFault());
    }

    private InputStream getResource(String name) {
        return this.getClass().getClassLoader().getResourceAsStream(name);
    }
}
