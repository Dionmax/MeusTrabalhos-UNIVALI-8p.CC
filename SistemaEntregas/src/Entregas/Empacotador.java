/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entregas;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Dionmax
 */
public class Empacotador extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    String ontology = msg.getOntology();
                    String content = msg.getContent();
                    if (ontology.equalsIgnoreCase("Empacotar")) {
                        System.out.println("O agente " + msg.getSender().
                                getName()
                                + " enviou um pacote");

                        System.out.println("Pacote recebido pelo: " + myAgent.getLocalName());
                    }
                } else {
                    block();
                }
            }
        });
    }
}
