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
    
    
    private String variavelGlobalNome;
    private String variavelGlobalTipo;
    private boolean isPrivado;
    
    public Variavel(String variavelGlobalTipo, String variavelGlobalNome, boolean isPrivado){
        this.variavelGlobalTipo = variavelGlobalTipo;
        this.variavelGlobalNome = variavelGlobalNome;
        this.isPrivado = isPrivado;
    }

    /**
     * @return the variavelGlobalNome
     */
    public String getVariavelGlobalNome() {
        return variavelGlobalNome;
    }

    /**
     * @param variavelGlobalNome the variavelGlobalNome to set
     */
    public void setVariavelGlobalNome(String variavelGlobalNome) {
        this.variavelGlobalNome = variavelGlobalNome;
    }

    /**
     * @return the variavelGlobalTipo
     */
    public String getVariavelGlobalTipo() {
        return variavelGlobalTipo;
    }

    /**
     * @param variavelGlobalTipo the variavelGlobalTipo to set
     */
    public void setVariavelGlobalTipo(String variavelGlobalTipo) {
        this.variavelGlobalTipo = variavelGlobalTipo;
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
