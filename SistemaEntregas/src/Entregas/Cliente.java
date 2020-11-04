/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entregas;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Dionmax
 */
public class Cliente extends Agent {

    protected void setup() {
        addBehaviour(new OneShotBehaviour(this) {

            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("Acolhedor", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("IniciarPedido");
                msg.setContent("Encomenda");
                myAgent.send(msg);
            }
        });
    }
}
