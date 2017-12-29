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
public class Variavel {
    
    
    private String variavelNome;
    private String variavelTipo;
    private boolean isPrivado;
    
    public Variavel(String variavelGlobalTipo, String variavelGlobalNome, boolean isPrivado){
        this.variavelTipo = variavelGlobalTipo;
        this.variavelNome = variavelGlobalNome;
        this.isPrivado = isPrivado;
    }

    /**
     * @return the variavelGlobalNome
     */
    public String getVariavelNome() {
        return variavelNome;
    }

    /**
     * @param variavelGlobalNome the variavelGlobalNome to set
     */
    public void setVariavelNome(String variavelGlobalNome) {
        this.variavelNome = variavelGlobalNome;
    }

    /**
     * @return the variavelGlobalTipo
     */
    public String getVariavelTipo() {
        return variavelTipo;
    }

    /**
     * @param variavelGlobalTipo the variavelGlobalTipo to set
     */
    public void setVariavelTipo(String variavelGlobalTipo) {
        this.variavelTipo = variavelGlobalTipo;
    }

    /**
     * @return the isPrivado
     */
    public boolean isIsPrivado() {
        return isPrivado;
    }

    /**
     * @param isPrivado the isPrivado to set
     */
    public void setIsPrivado(boolean isPrivado) {
        this.isPrivado = isPrivado;
    }
}
