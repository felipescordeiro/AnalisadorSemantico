/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelSemantico;

import ModelLexico.AnalisadorLexico;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author felipe
 */
public class Start {
 
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        AnalisadorLexico analisadorLexico = new AnalisadorLexico();
        analisadorLexico.analisador();
        AnalisadorSemantico semantico = new AnalisadorSemantico();
        
        File archive[];
        File directory = new File ("saida/");
        archive = directory.listFiles();
        //System.out.println("tamanho : " + archive.length);
        
        for(int i = 0; i < archive.length; i++){
        	System.out.println(" " + archive[i].getName());
        	semantico.start(archive[i].getName());
            //semantico.printLine();
           //semantico.printVariable();
            semantico.printMetodos();
        }
    }
}
