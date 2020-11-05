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

                    System.out.println(pacote.getEndereco());

                    ACLMessage msgEntrega = new ACLMessage(ACLMessage.INFORM);
                    msgEntrega.addReceiver(new AID("Cliente", AID.ISLOCALNAME));
                    msgEntrega.setLanguage("Português");
                    msgEntrega.setOntology("Pedido");
                    msgEntrega.setContent(g.toJson(pacote));
                    myAgent.send(msgEntrega);

                }
            }
        });
    }
}
