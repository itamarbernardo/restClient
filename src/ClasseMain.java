
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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

        int pergunta;
        List<Integer> numeros = new ArrayList<>();
        numeros.add(1);
        numeros.add(1);
        numeros.add(2);

        ProtocoloDeEnvio p = new ProtocoloDeEnvio(numeros);
        p.start();
        
        ProtocoloActions p2 = new ProtocoloActions();
        p2.start();
        
        while (true) {
        pergunta = Integer.parseInt(JOptionPane.showInputDialog("Digite um número:"));
                    if(pergunta < 0 || pergunta > 5){
                        continue;
                    }
        
            p2.adicionarComandoEnvio(pergunta);
           

        }
    }

}
