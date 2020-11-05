/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author Dionmax
 */
public class Pacote {

    private String id;
    private String cliente;
    private String endereco;
    private String conteudo;

    public Pacote() {

    }

    public Pacote(String id, String cliente, String endereco, String conteudo) {
        this.id = id;
        this.cliente = cliente;
        this.conteudo = conteudo;
        this.endereco = endereco;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getConteudo() {
        return conteudo;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.cliente + " - " + this.endereco + " - " + this.conteudo;
    }

}
