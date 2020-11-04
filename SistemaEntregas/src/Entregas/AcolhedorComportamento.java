/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entregas;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

/**
 *
 * @author Dionmax
 */
public class AcolhedorComportamento extends Behaviour{
    int i = 0;

    public AcolhedorComportamento(Agent a) {
        super(a);
    }

    public void action() {
        System.out.println("* Olá meu bom! ... Meu nome é " + myAgent.
                getLocalName());
        i = i + 1;
    }

    public boolean done() {
        return i > 3;
    }
}
