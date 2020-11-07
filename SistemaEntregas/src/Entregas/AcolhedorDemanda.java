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
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author Dionmax
 */
public class AcolhedorDemanda extends Agent {

    int i = 0;

    public int numeroPedidos = 0;

    protected void setup() {
        addBehaviour(new Behaviour(this) {

            public void action() {
                Pacote pacote = new Pacote();
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    String ontology = msg.getOntology();
                    String content = msg.getContent();

                    Gson g = new Gson();
                    pacote = g.fromJson(msg.getContent(), Pacote.class);

                    switch (TypesOntology.valueOf(ontology)) {
                        case IniciarPedido:

                            System.out.println("Pedido " + pacote.getId() + " recebido pelo agente "
                                    + myAgent.getLocalName()
                                    + " vindo do cliente: "
                                    + msg.getSender().getLocalName() + "\n");

                            ACLMessage msgEmpacotador = new ACLMessage(ACLMessage.INFORM);
                            msgEmpacotador.addReceiver(new AID("Empacotador", AID.ISLOCALNAME));
                            msgEmpacotador.setLanguage("Português");
                            msgEmpacotador.setOntology("Empacotar");
                            msgEmpacotador.setContent(content);
                            myAgent.send(msgEmpacotador);
                            numeroPedidos += 2;
                            break;

                        case PedidoEmpacotado:

                            System.out.println("Pedido " + pacote.getId() + " empacotado e recebido pelo agente: "
                                    + myAgent.getLocalName() + "\n"
                            );

                            System.out.println("Pedido " + pacote.getId() + " foi enviado para o entregador.\n");
                            
                            ACLMessage msgEntregador = new ACLMessage(ACLMessage.REQUEST);
                            msgEntregador.addReceiver(new AID("Entregador", AID.ISLOCALNAME));
                            msgEntregador.setLanguage("Português");
                            msgEntregador.setOntology("Entregar");
                            msgEntregador.setContent(content);
                            myAgent.send(msgEntregador);

                            i += 1;
                            break;
                    }
                }
            }

            public boolean done() {

                return i > numeroPedidos;
            }
        });
    }
}
