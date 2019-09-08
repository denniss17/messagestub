package nl.dennisschroer.messagestub.stub.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.PayloadRoots;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Endpoint
public class SoapStubService {
    @PayloadRoot(localPart = "")
    public void foo(){

    }
}
