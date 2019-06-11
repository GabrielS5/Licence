package tools.http;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

import org.glassfish.jersey.client.ClientConfig;

import tools.http.models.ApiEntity;

public class ApiClient {
	private Client client;
	private WebTarget webTarget;

	public ApiClient() {
		ClientConfig config = new ClientConfig();
		client = ClientBuilder.newClient(config);
		 webTarget = client.target("http://localhost:51221");
	}

	public void call() {
		WebTarget employeeWebTarget 
		  = webTarget.path("api/programs/active");
		
		Invocation.Builder invocationBuilder 
		  = employeeWebTarget.request(MediaType.APPLICATION_JSON);
		
		List<ApiEntity> response  = invocationBuilder.get(new GenericType<List<ApiEntity>>(){});
		
		System.out.println(response.size());
	}
	
}
