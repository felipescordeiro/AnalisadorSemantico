/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelSemantico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author felipe
 */
public class AnalisadorSemantico {

    static int numberLines = 1;
    int lastNumber = 0;
    HashMap<Integer, ArrayList<String>> tokenMap;
    HashMap<Integer, ArrayList<String>> dataLineMap;
    
    ArrayList<String> lineArchive;
    ArrayList<String> words;
    ArrayList<String> tokenWords;
    ArrayList<String> errors;
    ArrayList<Variavel> variaveisGlobais;
    ArrayList<Variavel> variaveisLocal;
    ArrayList<Metodos> metodos;
    Stack  pilha;
    public AnalisadorSemantico() {
        
       
    }

    public void start(String nameArchive){
        tokenMap = new HashMap<Integer, ArrayList<String>>();
        dataLineMap = new HashMap<Integer, ArrayList<String>>();
        pilha = new Stack();
        
        variaveisGlobais = new ArrayList<>();
        variaveisLocal = new ArrayList<>();
        metodos = new ArrayList<>();
        readFiles(nameArchive);
        parser();
    }
    
    public void readFiles(String nameArchive){
        HashMap<Integer, String> hashLine = new  HashMap<Integer, String>();
        try {      
                FileReader fileRead = new FileReader("saida/" + nameArchive);
                BufferedReader file = new BufferedReader(fileRead);
                String line;
                String token;
                String[] lineSplits;
                line = file.readLine();
                while (line != null) {

                    if(!line.contains("ERRO") && !line.contains("MAL")){
                        
                    	token = line.split("-")[2];
                        String numberLine = line.split("-")[0];
                        numberLine = numberLine.replaceAll(" ", "");
                        int number = Integer.parseInt(numberLine);    
                        line = line.split("-")[1];
                        lineSplits = line.split("-");
                        line = lineSplits[0].replaceAll(" ", "");
                        //token = lineSplits[1].replaceAll(" ", "");

                        

                        if(tokenMap.containsKey(number)){
                                tokenMap.get(number).add(line);
                                dataLineMap.get(number).add(token);
                        }else{
                                //System.out.println("  aa" + number);
                                words = new ArrayList<String>();
                                words.add(line);
                                tokenMap.put(number, words);
                                lastNumber = number;

                                tokenWords = new ArrayList<String>();
                                tokenWords.add(token);
                                dataLineMap.put(number, tokenWords);
                        }
                        //System.out.println("SINTATICO " + line + " linha " + number );
                        tokenMap.get(tokenMap.size() -1 + "");
                    }
                    line = file.readLine(); // le da segunda linha ate a ultima linha
                    
                }
                fileRead.close();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
           
    }
    
    public void parser(){
       
        for(int i = 1; i <= lastNumber; i++){
          
            if(dataLineMap.containsKey(i)){
               // System.out.println("linha: " + i + "  dado linha " + dataLineMap.get(i));
                for(int j = 0; j < tokenMap.get(i).size(); j++){
                    if(tokenMap.get(i).get(j).contains("DELIMITADOR")){
                        
                        if(dataLineMap.get(i).get(j).contains(":")){ //criar metodos
                           novoMetodo(i, j);
                           j++;
                        }else {
                        	pilha.add(dataLineMap.get(i).get(j));
                        }
                    }else if(tokenMap.get(i).get(j).contains("RESERVADA")){
                        if(dataLineMap.get(i).get(j).contains("class")){
                            classe(i, j);
                        }else if(dataLineMap.get(i).get(j).contains("final")){
                        }else if(dataLineMap.get(i).get(j).contains("if")){
                        }else if(dataLineMap.get(i).get(j).contains("else")){
                        }else if(dataLineMap.get(i).get(j).contains("for")){
                        }else if(dataLineMap.get(i).get(j).contains("scan")){
                        }else if(dataLineMap.get(i).get(j).contains("print")){
                        }else if(dataLineMap.get(i).get(j).contains("int")){ 
                            nextWord(i, j);
                        }else if(dataLineMap.get(i).get(j).contains("float")){
                            nextWord(i, j);
                        }else if(dataLineMap.get(i).get(j).contains("bool")){
                            nextWord(i, j);
                        }else if(dataLineMap.get(i).get(j).contains("main")){
                        }else if(dataLineMap.get(i).get(j).contains("true")){
                            nextWord(i, j);
                        }else if(dataLineMap.get(i).get(j).contains("false")){
                            nextWord(i, j);
                        }else if(dataLineMap.get(i).get(j).contains("string")){
                            nextWord(i, j);
                        }
                        
                    }
                   
                }
            }
        }
    }
    
    private void novoMetodo(int i, int j) {
       int x = j + 2;
       
       String nome = null;
       String tipo = null;
       ArrayList parametroNome = new ArrayList();;
       ArrayList parametroTipo = new ArrayList();
        //for(int x = j +1; x < tokenMap.get(i).size(); x++){
            if(tokenMap.get(i).get(x).contains("IDENTIFICADOR")){
                
                    if(tokenMap.get(i).get(x + 1).contains("IDENTIFICADOR")){
                        nome = dataLineMap.get(i).get(x + 1);
                        for(int w = x +2; w < tokenMap.get(i).size(); w++){
                            if(tokenMap.get(i).get(w).contains("RESERVADA")){
                                parametroTipo.add(dataLineMap.get(i).get(w));
                            }else if(tokenMap.get(i).get(w).contains("IDENTIFICADOR")){
                                parametroNome.add(dataLineMap.get(i).get(w));
                            }
                        }
                    }else if(tokenMap.get(i).get(x + 1).contains("RESERVADA")){
                       nome = dataLineMap.get(i).get(x + 1);
                        for(int w = x +2; w < tokenMap.get(i).size(); w++){
                            if(tokenMap.get(i).get(w).contains("RESERVADA")){
                                parametroTipo.add(dataLineMap.get(i).get(w));
                            }else if(tokenMap.get(i).get(w).contains("IDENTIFICADOR")){
                                parametroNome.add(dataLineMap.get(i).get(w));
                            }
                        }
                    }else{
                        nome = dataLineMap.get(i).get(x);
                        for(int w = x +2; w < tokenMap.get(i).size(); w++){
                            if(tokenMap.get(i).get(w).contains("RESERVADA")){
                                parametroTipo.add(dataLineMap.get(i).get(w));
                            }else if(tokenMap.get(i).get(w).contains("IDENTIFICADOR")){
                                parametroNome.add(dataLineMap.get(i).get(w));
                            }
                        }
                    }
            }else if(tokenMap.get(i).get(x).contains("RESERVADA")){
            	tipo = dataLineMap.get(i).get(x);
            	if(tokenMap.get(i).get(x + 1).contains("RESERVADA")){
                    nome = dataLineMap.get(i).get(x + 1);
                     for(int w = x +2; w < tokenMap.get(i).size(); w++){
                         if(tokenMap.get(i).get(w).contains("RESERVADA")){
                             parametroTipo.add(dataLineMap.get(i).get(w));
                         }else if(tokenMap.get(i).get(w).contains("IDENTIFICADOR")){
                             parametroNome.add(dataLineMap.get(i).get(w));
                         }
                     }
                  }else{
                      nome = dataLineMap.get(i).get(x);
                      for(int w = x +2; w < tokenMap.get(i).size(); w++){
                          if(tokenMap.get(i).get(w).contains("RESERVADA")){
                              parametroTipo.add(dataLineMap.get(i).get(w));
                          }else if(tokenMap.get(i).get(w).contains("IDENTIFICADOR")){
                              parametroNome.add(dataLineMap.get(i).get(w));
                          }
                      }
                  }
            }else{
                nome = dataLineMap.get(i).get(x);
                for(int w = x +2; w < tokenMap.get(i).size(); w++){
                    if(tokenMap.get(i).get(w).contains("RESERVADA")){
                        parametroTipo.add(dataLineMap.get(i).get(w));
                    }else if(tokenMap.get(i).get(w).contains("IDENTIFICADOR")){
                        parametroNome.add(dataLineMap.get(i).get(w));
                    }
                }
            }
            if(tipo == null){
            	tipo = "void";
            }
            
            if(!dataLineMap.get(i).get(tokenMap.get(i).size() -1).contains(";") ){
            	inserirMetodo(true, nome, tipo, parametroNome, parametroTipo);
            }
        //}
    }
    
    /*public void parametro(int i, int j){
        for(int x = j +1; x < tokenMap.get(i).size(); x++){
            if(tokenMap.get(i).get(x + 1).contains("RESERVADA")){
                
            }
        }
    }*/
    
    public void classe(int i, int j){
        String nome = null;
        String tipo = null;
        for(int x = i; x <= lastNumber; x++){
          
            if(dataLineMap.containsKey(x)){
               // System.out.println("linha: " + i + "  dado linha " + dataLineMap.get(i));
                for(int z = 0; z < tokenMap.get(x).size(); z++){
                   //* if(tokenMap.get(i).get(x).contains("IDENTIFICADOR")){
                      //  if(tokenMap.get(i).get(x).contains("DELIMITADOR")){
                            
                       // }
                    //}
                }
            }
        }
        novaClasse(true, nome , tipo);
    }
    
    public void nextWord(int i, int j){
       
        for(int x = j +1; x < tokenMap.get(i).size(); x++){
           // System.out.println(x + " " + dataLineMap.get(i).get(x) + " " +tokenMap.get(i).get(x));
            if(tokenMap.get(i).get(x).contains("IDENTIFICADOR")){
               if(tokenMap.get(i).get(x -1 ).contains("RESERVADA") || dataLineMap.get(i).get(x - 1 ).contains(",")){
                    inserirVariavelGlobal(false, dataLineMap.get(i).get(j), dataLineMap.get(i).get(x));
                }
            }
        }
    }
    
      void printMetodos() {
    	  System.out.println();
       for(int i = 0; i < metodos.size(); i++){
            System.out.println("nome: " + metodos.get(i).getNome() + 
                    " tipo " + metodos.get(i).getTipo());
            for(int j = 0; j < metodos.get(i).getParametrosNome().size(); j++){
            System.out.println("nome parametro: " + metodos.get(i).getParametrosNome().get(j) + 
                    " tipo parametro: " + metodos.get(i).getParametrosTipo().get(j));    
            }
        }
    }
    
    public void printVariable(){
      
        for(int i = 0; i < variaveisGlobais.size(); i++){
            System.out.println("token: " + variaveisGlobais.get(i).getVariavelGlobalTipo() + 
                    " dado " + variaveisGlobais.get(i).getVariavelGlobalNome());
        }
    }
    public void printLine(){
		
        for(int i = 1; i <= lastNumber; i++){
            if(tokenMap.containsKey(i)){

            System.out.println(">>>>>>>>>>>> " + tokenMap.get(i));
                for(int j = 0; j < tokenMap.get(i).size(); j++){
                        System.out.println("linha: " + i + "  token " + tokenMap.get(i).get(j) +" dado " + dataLineMap.get(i).get(j));		
//					System.out.println("linha: " + i  + " " + tokenMap.get(i).get(j));
                        //System.out.println("===============" + lineMap.get(i).get(j).trim());

                }
            }
        }
    }
    
    public void novaClasse(boolean privado, String nome, String tipo){
        variaveisGlobais = new ArrayList<>();
        metodos = new ArrayList<>();
        Classe classe = new Classe(privado, nome, tipo, metodos, variaveisLocal);
    }

    public void inserirVariavelGlobal(boolean isPravado,String tipo, String nome){
        Variavel variavel = new Variavel(tipo, nome, isPravado);
        variaveisGlobais.add(variavel);
    }
    
    public void inserirVariavelMetodo(boolean isPravado,String tipo, String nome){
        Variavel variavel = new Variavel(tipo, nome, isPravado);
        variaveisLocal.add(variavel);
    }
    public void inserirMetodo(boolean privado, String nome, String tipo, ArrayList parametrosNome, ArrayList parametrosTipo){
    
    	if(buscaMetodo(nome, tipo, parametrosNome.size())){
	    	variaveisLocal = new ArrayList<>();
	        Metodos metodo = new Metodos(privado, nome, tipo, parametrosNome, parametrosTipo, variaveisLocal);
	        metodos.add(metodo);
	        System.out.println("nome " + nome + " tipo " + tipo + " parametros " + parametrosNome.size());
    	}
    }
    
    public boolean buscaVariavel(){
        
        return false;
    }
    
    public boolean buscaMetodo(String nome, String tipo, int j){
        
    	for(int i = 0; i < metodos.size(); i++){
    		if(metodos.get(i).getNome().equals(nome)){
    			if(metodos.get(i).getTipo().equals(tipo)){
    				if(metodos.get(i).getParametrosNome().size() == j){
    	    			return false;
    	    		}
        		}
    		}
    	}
    	
        return true;
    }

  

    

   
    
    
    
}
