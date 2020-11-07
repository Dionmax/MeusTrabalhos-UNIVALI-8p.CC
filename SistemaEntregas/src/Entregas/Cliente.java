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

                switch (pedidoRecebido != null
                        ? TypesOntology.valueOf(pedidoRecebido.getOntology())
                        : TypesOntology.IniciarPedido) {

                    case IniciarPedido:

                        System.out.println("O cliente: " + myAgent.getLocalName() + " fez um pedido\n");

                        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                        msg.addReceiver(new AID("Acolhedor", AID.ISLOCALNAME));
                        msg.setLanguage("Português");
                        msg.setOntology("IniciarPedido");
                        msg.setContent("{ id:" + (int) (Math.random() * 500 + 1) + ", cliente: \""
                                + myAgent.getLocalName() + "\", endereco: \"Rua Uruguai " + (int) (Math.random() * 50 + 1)
                                + "\", conteudo:\"Banana\"}");

                        myAgent.send(msg);
                        block();

                        break;

                    case Pedido:
                        Gson g = new Gson();
                        pacote = g.fromJson(pedidoRecebido.getContent(), Pacote.class);

                        if (pedidoRecebido.getOntology().equalsIgnoreCase("Pedido")) {
                            System.out.println("Pedido: " + pacote.getId()+ " recebido pelo: " + myAgent.getLocalName() + "\n");
                            block();
                        }
                        break;
                }
            }
        });
    }
}
