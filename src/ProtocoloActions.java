
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.json;
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
public class ProtocoloActions extends Thread {

    private Queue<Integer> fila;

    public ProtocoloActions() {
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
        System.out.println("Chegou aqui!");
        enviaPedidos();
    }

    public void enviaPedidos() {

        while (true) {
            //System.out.println("Entrou no while");
            if (this.fila.size() != 0) {
                //System.out.println("Inseriu elemento");
                try {

                    AcessoRest ac = new AcessoRest();
                    String mensagem = ac.exemploGet("http://192.168.0.104:8084/TestHome/webresources//sensor?sensorId==" + this.fila.poll());
                    
                    Gson g = new Gson();
                    SensorAnswer s = new SensorAnswer();
                    Type modelo = new TypeToken<SensorAnswer>() {
                    }.getType();

                    s = g.fromJson(mensagem, modelo);
                    //Esses não precisa mostrar, por enquanto!
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

            }
        }

    }

}
