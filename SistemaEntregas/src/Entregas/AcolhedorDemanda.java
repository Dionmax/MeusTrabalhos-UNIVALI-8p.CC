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

public class AcolhedorDemanda extends Agent {

    int i = 0;

    protected void setup() {
        addBehaviour(new Behaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    String ontology = msg.getOntology();
                    String content = msg.getContent();

                    switch (TypesOntology.valueOf(ontology)) {
                        case IniciarPedido:

                            System.out.println("O cliente " + msg.getSender().
                                    getName()
                                    + " fez um pedido\n");

                            ACLMessage msgEmpacotador = new ACLMessage(ACLMessage.INFORM);
                            msgEmpacotador.addReceiver(new AID("Empacotador", AID.ISLOCALNAME));
                            msgEmpacotador.setLanguage("Português");
                            msgEmpacotador.setOntology("Empacotar");
                            msgEmpacotador.setContent(content);
                            myAgent.send(msgEmpacotador);
                            i = i + 1;
                            break;
                            
                        case PedidoEmpacotado:                            
                            ACLMessage msgEntregador = new ACLMessage(ACLMessage.REQUEST);
                            msgEntregador.addReceiver(new AID("Entregador", AID.ISLOCALNAME));
                            msgEntregador.setLanguage("Português");
                            msgEntregador.setOntology("Entregar");
                            msgEntregador.setContent(content);
                            myAgent.send(msgEntregador);
                            i = i + 1;
                            break;

                        default:
                            i = i + 1;
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
