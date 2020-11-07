/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entregas;

import Objetos.Pacote;
import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Dionmax
 */
public class Entregador extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {

            Pacote pacote = new Pacote();

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {

                    String ontology = msg.getOntology();
                    String content = msg.getContent();

                    Gson g = new Gson();
                    pacote = g.fromJson(content, Pacote.class);

                    System.out.println("Pedido " + pacote.getId() + " será levado pelo entregador para o cliente: "
                            + pacote.getCliente() + ", no endereço: " + pacote.getEndereco() + "\n");

                    ACLMessage msgParaOCliente = new ACLMessage(ACLMessage.INFORM);
                    msgParaOCliente.addReceiver(new AID(pacote.getCliente(), AID.ISLOCALNAME));
                    msgParaOCliente.setLanguage("Português");
                    msgParaOCliente.setOntology("Pedido");
                    msgParaOCliente.setContent(content);
                    myAgent.send(msgParaOCliente);
                }
            }
        });
    }
}
