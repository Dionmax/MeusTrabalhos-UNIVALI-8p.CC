/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entregas;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Dionmax
 */
enum typesPacotes {
    IniciarPedido,
    Pedido
}

public class AcolhedorDemanda extends Agent {

    int i = 0;

    protected void setup() {
        addBehaviour(new Behaviour(this) {

//            public void action() {
//                ACLMessage msg = myAgent.receive();
//
//                if (msg != null) {
//                    String ontology = msg.getOntology();
//                    String content = msg.getContent();
//                    if (ontology.equalsIgnoreCase("Pedido") && content.equalsIgnoreCase("Encomenda")) {
//                        System.out.println("O agente " + msg.getSender().
//                                getName()
//                                + " pediu um pacote");
//
//                        System.out.println("Pacote recebido");
//                    }
//                } else {
//                    block();
//                }
//
//            }
            public void action() {
                ACLMessage msg = myAgent.receive();
                
                if (msg != null) {
                    String ontology = msg.getOntology();

                    switch (typesPacotes.valueOf(ontology)) {
                        case IniciarPedido:
                            
                            System.out.println("O agente " + msg.getSender().
                                getName()
                                + " fez um pedido");
                            
                            ACLMessage msgEmpacotador = new ACLMessage(ACLMessage.INFORM);
                            msgEmpacotador.addReceiver(new AID("Empacotador1", AID.ISLOCALNAME));
                            msgEmpacotador.setLanguage("Português");
                            msgEmpacotador.setOntology("Empacotar");
                            msgEmpacotador.setContent("Pacote");
                            myAgent.send(msgEmpacotador);
                            i = i + 1;
                            break;

                        default:
                            break;
                    }
                }
            }

            public boolean done() {
                return i > 3;
            }
        });
    }
}
