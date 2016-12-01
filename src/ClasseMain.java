
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author corona
 */
public class ClasseMain {
	public static void main(String[] args) throws IOException {

		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target("http://localhost:8080/HomeService");

		WebTarget pathdWebTarget = webTarget.path("sensor");
		WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("sensorId", 1);

		Invocation.Builder invocationBuilder = 
				pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);

		Response response = invocationBuilder.get();

		String resp = response.readEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		
		SensorAnswer sa = mapper.readValue(resp, SensorAnswer.class);

		System.out.println(sa.getValue());
		
		client = ClientBuilder.newClient();

		webTarget = client.target("http://localhost:8080/HomeService/");

		pathdWebTarget = webTarget.path("sensor");
		pathdWebTargetQuery = pathdWebTarget.queryParam("sensorId", 2);

		invocationBuilder = pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);

		response = invocationBuilder.get();

		resp = response.readEntity(String.class);
		
		sa = mapper.readValue(resp, SensorAnswer.class);

		System.out.println(sa.getValue());
		
	}

}
