/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entregas;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import com.google.gson.Gson;
import Objetos.Pacote;

/**
 *
 * @author Dionmax
 */
public class Empacotador extends Agent {

    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {

                Pacote pacote = new Pacote();

                //Receber mensagem do acolhedor
                ACLMessage msgRecebimento = myAgent.receive();

                if (msgRecebimento != null) {
                    String ontology = msgRecebimento.getOntology();
                    String content = msgRecebimento.getContent();

                    Gson g = new Gson();
                    pacote = g.fromJson(content, Pacote.class);

                    if (ontology.equalsIgnoreCase("Empacotar")) {
                        System.out.println("Pedido " + pacote.getId()
                                + " recebido pelo agente " + myAgent.getLocalName()
                                + " para ser empacotado.\n");

                        // Retornar para o Acolhedor
                        System.out.println("Pacote " + pacote.getId() + " devolvido para o:" + msgRecebimento.getSender().getName() + "\n");

                        ACLMessage msgEmpacotador = new ACLMessage(ACLMessage.INFORM);
                        msgEmpacotador.addReceiver(new AID("Acolhedor", AID.ISLOCALNAME));
                        msgEmpacotador.setLanguage("Português");
                        msgEmpacotador.setOntology("PedidoEmpacotado");
                        msgEmpacotador.setContent(g.toJson(pacote));
                        myAgent.send(msgEmpacotador);
                    }
                }
            }
        });
    }
}
