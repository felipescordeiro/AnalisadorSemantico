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
public class Classe {
    
    private boolean privado;
    private String nome;
    private String tipo;
    private ArrayList parametrosNome;
    private ArrayList parametrosTipo;
    private ArrayList<Metodos> metodosList;
    private ArrayList<Variavel> variavelList;
    
    public Classe(boolean privado, String nome, String tipo, ArrayList<Metodos> metodosList,
            ArrayList<Variavel> variavelList){
        this.privado = privado;
        this.tipo = tipo;
        this.nome = nome;
        this.metodosList = metodosList;
        this.variavelList = variavelList;
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

    /**
     * @return the metodosList
     */
    public ArrayList<Metodos> getMetodosList() {
        return metodosList;
    }

    /**
     * @param metodosList the metodosList to set
     */
    public void setMetodosList(ArrayList<Metodos> metodosList) {
        this.metodosList = metodosList;
    }

    /**
     * @return the variavelList
     */
    public ArrayList<Variavel> getVariavelList() {
        return variavelList;
    }

    /**
     * @param variavelList the variavelList to set
     */
    public void setVariavelList(ArrayList<Variavel> variavelList) {
        this.variavelList = variavelList;
    }
    
}
