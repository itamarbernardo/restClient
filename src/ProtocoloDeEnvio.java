
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
public class ProtocoloDeEnvio extends Thread {

//    private int valorEnvio = 0;
    private List<Integer> pedidos;

    public ProtocoloDeEnvio(List<Integer> pedidos) {
        this.pedidos = new ArrayList<>();
        this.pedidos = pedidos;

    }

    @Override
    public void run() {
        enviaPedidos();
    }

    public void enviaPedidos() {

        while (true) {

            for (Integer p : pedidos) {
                try {
                    AcessoRest ac = new AcessoRest();
                    String mensagem = ac.exemploGet("http://192.168.0.104:8084/TestHome/webresources//sensor?sensorId==" + p);

                    Gson g = new Gson();
                    SensorAnswer sa = new SensorAnswer();
                    Type modelo = new TypeToken<SensorAnswer>() {
                    }.getType();

                    sa = g.fromJson(mensagem, modelo);
                    
                    if (sa.getValue() == 666) {
                        //Lança notificação para o usuário que a casa está pegando fogo
                        System.out.println("Tá pegando fogo");
                    } else if (sa.getValue() == 999) {
                        //Lança notificação para o usuário que está faltando comida
                        System.out.println("O rango tá pouco");
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }

    }

}
