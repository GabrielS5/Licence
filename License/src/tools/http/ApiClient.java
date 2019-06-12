package tools.http;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import tools.http.models.ApiEntity;
import tools.http.models.ApiResponse;

public class ApiClient {
	private Client client;
	private WebTarget webTarget;

	public ApiClient() {
		ClientConfig config = new ClientConfig();
		client = ClientBuilder.newClient(config);
		webTarget = client.target("http://localhost:51221");
	}

	public List<ApiResponse> getGraphs() {
		WebTarget employeeWebTarget = webTarget.path("api/graphs/active");

		Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);

		List<ApiResponse> response = invocationBuilder.get(new GenericType<List<ApiResponse>>() {
		});

		return response;
	}
	
	public List<ApiResponse> getPrograms() {
		WebTarget employeeWebTarget = webTarget.path("api/programs/active");

		Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);

		List<ApiResponse> response = invocationBuilder.get(new GenericType<List<ApiResponse>>() {
		});

		return response;
	}

	public void postGraph(ApiEntity entity) {
		WebTarget employeeWebTarget = webTarget.path("api/graphs");

		Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);

		invocationBuilder.post(Entity.entity(entity, MediaType.APPLICATION_JSON));
	}
	
	public void postProgram(ApiEntity entity) {
		WebTarget employeeWebTarget = webTarget.path("api/programs");

		Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);

		invocationBuilder.post(Entity.entity(entity, MediaType.APPLICATION_JSON));
	}

}
