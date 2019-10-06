package nl.dennisschroer.messagestub;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.mockserver.model.XmlSchemaBody.xmlSchema;

@EnableJpaAuditing
@SpringBootApplication
public class MessagestubApplication {
	private ClientAndServer mockServer;

	public static void main(String[] args) {
		SpringApplication.run(MessagestubApplication.class, args);
	}

	@PostConstruct
	public void startMockServer(){
		mockServer = ClientAndServer.startClientAndServer(1080);
		mockServer.when(HttpRequest.request().withBody(xmlSchema("")))
	}

	@PreDestroy
	public void stopMockServer(){
		mockServer.stop();
	}
}
