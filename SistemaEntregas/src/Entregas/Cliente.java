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
public class Cliente extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {

            Pacote pacote = new Pacote();

            public void action() {

                ACLMessage pedidoRecebido = myAgent.receive();

                if (pedidoRecebido != null) {
                    String ontology = pedidoRecebido.getOntology();
                    String content = pedidoRecebido.getContent();

                    Gson g = new Gson();
                    pacote = g.fromJson(content, Pacote.class);

                    if (ontology.equalsIgnoreCase("Pedido")) {
                        System.out.println("O agente " + pedidoRecebido.getSender().
                                getName()
                                + " enviou um pacote");

                        System.out.println("Pacote: " + pacote.toString() + "\nRecebido pelo: " + myAgent.getLocalName() + "aaaaaa");
                        block();
                    }
                } else {
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.addReceiver(new AID("Acolhedor", AID.ISLOCALNAME));
                    msg.setLanguage("Português");
                    msg.setOntology("IniciarPedido");
                    msg.setContent("{ id: 1, cliente: \"Dion\", endereco: \"Rua Uruguai\", conteudo:\"Banana\"}");
                    myAgent.send(msg);
                }
            }
        });
    }
}
