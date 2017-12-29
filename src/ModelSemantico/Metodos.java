/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelSemantico;

import java.util.ArrayList;

/**
 *
 * @author felipe
 */
public class Metodos {
    
    private boolean privado;
    private String nome;
    private String tipo;
    private ArrayList parametrosNome;
    private ArrayList parametrosTipo;
    private ArrayList variaveisLocais;

  
    
    public Metodos(boolean privado, String nome, String tipo, ArrayList parametrosNome,
            ArrayList parametrosTipo, ArrayList variaveisLocais){
        this.privado = privado;
        this.nome = nome;
        this.tipo = tipo;
        this.parametrosTipo = parametrosTipo;
        this.parametrosNome = parametrosNome;
        this.variaveisLocais = variaveisLocais;
    }

    /**
     * @return the privado
     */
    public boolean isPrivado() {
        return privado;
    }

    /**
     * @param privado the privado to set
     */
    public void setPrivado(boolean privado) {
        this.privado = privado;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the parametrosNome
     */
    public ArrayList getParametrosNome() {
        return parametrosNome;
    }

    /**
     * @param parametrosNome the parametrosNome to set
     */
    public void setParametrosNome(ArrayList parametrosNome) {
        this.parametrosNome = parametrosNome;
    }

    /**
     * @return the parametrosTipo
     */
    public ArrayList getParametrosTipo() {
        return parametrosTipo;
    }

    /**
     * @param parametrosTipo the parametrosTipo to set
     */
    public void setParametrosTipo(ArrayList parametrosTipo) {
        this.parametrosTipo = parametrosTipo;
    }
    
    public ArrayList getVariaveisLocais() {
        return variaveisLocais;
    }

    public void setVariaveisLocais(ArrayList variaveisLocais) {
        this.variaveisLocais = variaveisLocais;
    }
}
