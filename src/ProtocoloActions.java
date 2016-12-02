
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author corona
 */
public class ProtocoloActions extends Thread{
    
     private List<Integer> pedidos;
    private Queue<Integer> fila;

    public ProtocoloActions() {
        this.pedidos = new ArrayList<>();
        this.pedidos = pedidos;
        this.fila = new LinkedList<Integer>();
    }

    public synchronized void adicionarComandoEnvio(int valorEnvio) {
        this.fila.offer(valorEnvio);
    }

    public synchronized Queue<Integer> getFila() {
        return fila;
    }

    public synchronized void setFila(Queue<Integer> fila) {
        this.fila = fila;
    }

     @Override
    public void run() {
        enviaPedidos();
    }

    public void enviaPedidos() {

        while (true) {
            if (this.fila.size() == 0) {
                continue;
            } else {
                try {
                    Client client = ClientBuilder.newClient();

                    WebTarget webTarget = client.target("http://localhost:8084/HomeService");

                    WebTarget pathdWebTarget = webTarget.path("sensor");
                    WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("sensorId", this.fila.poll());

                    Invocation.Builder invocationBuilder
                            = pathdWebTargetQuery.request(MediaType.APPLICATION_JSON_TYPE);

                    Response response = invocationBuilder.get();

                    String resp = response.readEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    SensorAnswer sa;

                    sa = mapper.readValue(resp, SensorAnswer.class);

                    System.out.println(sa.getValue());

//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }

    }
    
}
