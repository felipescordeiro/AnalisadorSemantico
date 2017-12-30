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
    ArrayList<Metodos> metodos;
    ArrayList<Classe> classes;
    
    
    boolean classe = false;
    String nameClasse = null;
    int metodo = 0;
    boolean metodoBool = false;
    boolean isMain = true;
    Stack  pilha;
    public AnalisadorSemantico() {
        
       
    }

    public void start(String nameArchive){
        tokenMap = new HashMap<Integer, ArrayList<String>>();
        dataLineMap = new HashMap<Integer, ArrayList<String>>();
        pilha = new Stack();
        
        classes = new ArrayList<Classe>();
        variaveisGlobais = new ArrayList<>();
        metodos = new ArrayList<>();
        errors = new ArrayList<String>();
        
        readFiles(nameArchive);
        parser();
    }
    
    public void readFiles(String nameArchive){
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
                           metodoBool = true;
                        }
                    }else if(tokenMap.get(i).get(j).contains("RESERVADA")){
                        if(dataLineMap.get(i).get(j).contains("class")){
                            classe(i, j);
                            classe = !classe;
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
                        
                    }else if(tokenMap.get(i).get(j).contains("IDENTIFICADOR")){
                    	nextWord(i, j);
                    }
                   
                }
            }
        }
        classe = true;
        inserirClasse(false, nameClasse, null);
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
        int x = j +1;
        if(tokenMap.get(i).get(x).contains("IDENTIFICADOR")){
        	nome = dataLineMap.get(i).get(x);
        	inserirClasse(true, nome , tipo);
        }
        
        	nameClasse = nome;
               
    }
    
    public void inserirClasse(boolean privado, String nome, String tipo){
    	if(buscarClasse(tipo, nome) && classe){
    		ArrayList<Metodos> metodos = new ArrayList<>();
    		metodos = this.metodos;
    		//System.out.println("Classe: " + nome);
    		Classe classe = new Classe(privado, nameClasse, tipo, metodos, variaveisGlobais);
    		classes.add(classe);
    		//this.metodos = new ArrayList<Metodos>();
    		//variaveisGlobais.clear();
    	}
    }

  
	private boolean buscarClasse(String tipo, String nome) {
		for(int i = 0; i < classes.size(); i++){
			 if(classes.get(i).getNome().contains(nome)){
	 			errors.add("Erro - Já existe uma classe com esse nome");		
				 return false;
     		 }	 
		}
		return true;
	}
	 public void printClasse(){
	    	for(int x = 0; x < classes.size(); x++){
	            System.out.println("Classe " + classes.get(x).getNome());
	            
	            for(int g = 0; g < classes.get(x).getVariavelList().size(); g++){
	            	 System.out.println(" TipoGlobal: " + classes.get(x).getVariavelList().get(g).getVariavelTipo()
	            			 + 
	 	                    " VariavelGlobal " + classes.get(x).getVariavelList().get(g).getVariavelNome());
	            }
	            for(int i = 0; i < classes.get(x).getMetodosList().size(); i++){
	                System.out.println("nome: " + classes.get(x).getMetodosList().get(i).getNome() + 
	                        " tipo " + classes.get(x).getMetodosList().get(i).getTipo());
	                
	                for(int p = 0; p < classes.get(x).getMetodosList().get(i).getParametrosNome().size(); p++){
	                System.out.println("nome parametro: " + classes.get(x).getMetodosList().get(i).getParametrosNome().get(p) + 
	                        " tipo parametro: " + classes.get(x).getMetodosList().get(i).getParametrosTipo().get(p));    
	                }
	                for(int j = 0; j < classes.get(x).getMetodosList().get(i).getVariaveisLocais().size(); j++){
	              	  System.out.println("nome variavel local: " + classes.get(x).getMetodosList().get(i).getVariaveisLocais().get(j).getVariavelNome() + 
	                            " tipo variavel local: " + classes.get(x).getMetodosList().get(i).getVariaveisLocais().get(j).getVariavelTipo());
	                }
	            }
	            System.out.println("\n");
	        }
	    }

	
	/********************* Variaveis **************************************************/
    public void nextWord(int i, int j){
       
        for(int x = j +1; x < tokenMap.get(i).size(); x++){
           // System.out.println(x + " " + dataLineMap.get(i).get(x) + " " +tokenMap.get(i).get(x));
            if(tokenMap.get(i).get(x).contains("IDENTIFICADOR")){
               if(tokenMap.get(i).get(x -1 ).contains("RESERVADA") || dataLineMap.get(i).get(x - 1 ).contains(",")){
                    inserirVariavelGlobal(false, dataLineMap.get(i).get(j), dataLineMap.get(i).get(x));
                }else if(tokenMap.get(i).get(x -1 ).contains("IDENTIFICADOR") || dataLineMap.get(i).get(x - 1 ).contains(",")){
                	//System.out.println(x + " " + dataLineMap.get(i).get(x) + " " + tokenMap.get(i).get(x));
                	inserirVariavelGlobal(false, dataLineMap.get(i).get(j), dataLineMap.get(i).get(x));
                }
            }
        }
    }
        
    public void printVariable(){
      
        for(int i = 0; i < variaveisGlobais.size(); i++){
            System.out.println("TipoGlobal: " + variaveisGlobais.get(i).getVariavelTipo() + 
                    " VariavelGlobal " + variaveisGlobais.get(i).getVariavelNome());
        }
    }
    public void printLine(){
		
        for(int i = 1; i <= lastNumber; i++){
            if(tokenMap.containsKey(i)){

            System.out.println(">>>>>>>>>>>> " + tokenMap.get(i));
                for(int j = 0; j < tokenMap.get(i).size(); j++){
                        System.out.println("linha: " + i + "  tipoGlobal " + tokenMap.get(i).get(j) +" VariavelGlobal " + dataLineMap.get(i).get(j));		
//					System.out.println("linha: " + i  + " " + tokenMap.get(i).get(j));
                        //System.out.println("===============" + lineMap.get(i).get(j).trim());

                }
            }
        }
    }

	public void inserirVariavelGlobal(boolean isPravado,String tipo, String nome){
        if(buscaVariavelGlobal(tipo, nome) && !metodoBool){
	    	Variavel variavel = new Variavel(tipo, nome, isPravado);
	        variaveisGlobais.add(variavel);
        }
    }
    
    private boolean buscaVariavelGlobal(String tipo, String nome) {
    	for(int i = 0; i < variaveisGlobais.size(); i++){
    		if(variaveisGlobais.get(i).getVariavelNome().equals(nome)){
    			if(variaveisGlobais.get(i).getVariavelTipo().equals(tipo)){
					return false;
    	    	}
    		}
    	}
		return true;
	}

    /**********************  Metodo *******************************************************/
	public ArrayList<Variavel> inserirVariavelLocal(boolean isPravado,String tipo, String nome, ArrayList<Variavel> variaveisLocal){
        if(buscaVariavelLocal(tipo, nome, variaveisLocal)){
			Variavel variavel = new Variavel(tipo, nome, isPravado);
	        variaveisLocal.add(variavel);
        }
        return variaveisLocal;
    }
 
    
    public boolean buscaVariavelLocal(String tipo, String nome, ArrayList<Variavel> variaveisLocal){
    	
    	for(int i = 0; i < variaveisLocal.size(); i++){
    		if(variaveisLocal.get(i).getVariavelNome().equals(nome)){
    			//if(variaveisLocal.get(i).getVariavelTipo().equals(tipo)){
					errors.add("Erro- Essa variável já foi declarada");
    				return false;
    	    	//}
    		}
    	}
		return true;
    }
    
    
    private void novoMetodo(int i, int j) {
        int x = j + 2;
        
        String nome = null;
        String tipo = null;
        ArrayList<String> parametroNome = new ArrayList<String>();;
        ArrayList<String> parametroTipo = new ArrayList<String>();
         
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
             	inserirMetodo(true, nome, tipo, parametroNome, parametroTipo, i, j);       	
             }
         
     }
    
    
    public boolean buscaMetodo(String nome, String tipo, int j){
        
    	for(int i = 0; i < metodos.size(); i++){
    		
    		if(isMain && nome.contains("main")){
    			//System.out.println("ERRO - Já existe uma main");
    			errors.add("ERRO - Já existe uma main");
    			return false;
    		}else if(metodos.get(i).getNome().contains(nome)){
    			if(metodos.get(i).getTipo().equals(tipo)){
    				if(metodos.get(i).getParametrosNome().size() == j){
    	    			return false;
    	    		}
    				
        		}
    		}
    	}
    	
        return true;
    }
   
    public void inserirMetodo(boolean privado, String nome, String tipo, ArrayList<String> parametrosNome, ArrayList<String> parametrosTipo, int i, int j){
    	//System.out.println("nome " + nome + " "+  buscaMetodo(nome, tipo, parametrosNome.size()));
    	if(buscaMetodo(nome, tipo, parametrosNome.size())){
    	    //Variaveis locais
    		ArrayList<Variavel> variaveisLocal = new ArrayList<>();
    		variaveisLocal = escopoMetodo(i, j, variaveisLocal);
    		//System.out.println("Parametros " + parametrosNome.size());
    		Metodos metodo = new Metodos(privado, nome, tipo, parametrosNome, parametrosTipo, variaveisLocal);
	        metodos.add(this.metodo,metodo);
	        this.metodo++;
	       // System.out.println("nome " + nome + " tipo " + tipo + " parametros " + parametrosNome.size());
    	}
    }
    
    private ArrayList<Variavel> escopoMetodo(int l, int c,
			ArrayList<Variavel> variaveisLocal) {
    	boolean isEscopo = false;
		for(int i = l; i < lastNumber; i++){
			 if(dataLineMap.containsKey(i)){
				for(int j = 0; j < tokenMap.get(i).size(); j++){
					
						if(dataLineMap.get(i).get(j).contains("{") && !isEscopo){
							//if(dataLineMap.get(i).get(j-1).contains(")")){
							//System.out.println(" anterior: " + dataLineMap.get(i).get(j-1));
								isEscopo = true;
							//}
						}else if(isEscopo){
							//System.out.println(" anterior: " + dataLineMap.get(i).get(j));
							if(!dataLineMap.get(i).get(j).contains(":") ){
								//System.out.println(" anterior: " + dataLineMap.get(i).get(j));
								if(dataLineMap.get(i).get(j).contains("class")){
									i = lastNumber;
								}else{
									variaveisLocal = nextWordLocal(i, j, variaveisLocal);
								}
							}else{
								 i = lastNumber;
							}
						}
				}
			 }
		}
    	return variaveisLocal;
	}

    public ArrayList<Variavel> nextWordLocal(int i, int j, ArrayList<Variavel> variaveisLocal){
        
        for(int x = j +1; x < tokenMap.get(i).size(); x++){
           //System.out.println(x + " " + dataLineMap.get(i).get(x) + " " + t.get(i).get(x));
            if(tokenMap.get(i).get(x).contains("IDENTIFICADOR")){
            	
               if(tokenMap.get(i).get(x -1 ).contains("RESERVADA") || dataLineMap.get(i).get(x - 1 ).contains(",")){
                   if(dataLineMap.get(i).get(j).contains("int") || dataLineMap.get(i).get(j).contains("float") || dataLineMap.get(i).get(j).contains("string") 
                		   || dataLineMap.get(i).get(j).contains("bool") ){
                	   variaveisLocal = inserirVariavelLocal(false, dataLineMap.get(i).get(j), dataLineMap.get(i).get(x), variaveisLocal);
                   }
                }else if(tokenMap.get(i).get(x -1 ).contains("IDENTIFICADOR") || dataLineMap.get(i).get(x - 1 ).contains(",")){
                	//System.out.println(x + " " + dataLineMap.get(i).get(x) + " " + tokenMap.get(i).get(x));
                	variaveisLocal = inserirVariavelLocal(false, dataLineMap.get(i).get(j), dataLineMap.get(i).get(x), variaveisLocal);
                }else if(tokenMap.get(i).get(x).contains("IDENTIFICADOR") && dataLineMap.get(i).get(x - 1 ).contains(",")) {
                	//System.out.println(x + " " + dataLineMap.get(i).get(x) + " " + tokenMap.get(i).get(x));
                	variaveisLocal = inserirVariavelLocal(false, dataLineMap.get(i).get(j), dataLineMap.get(i).get(x), variaveisLocal);
                }
            }
        }
        return variaveisLocal;
    }
    
	void printMetodos() {
  	  System.out.println("metodos " + metodos.size());
     for(int i = 0; i < metodos.size(); i++){
          System.out.println("nome: " + metodos.get(i).getNome() + 
                  " tipo " + metodos.get(i).getTipo());
          for(int j = 0; j < metodos.get(i).getParametrosNome().size(); j++){
          System.out.println("nome parametro: " + metodos.get(i).getParametrosNome().get(j) + 
                  " tipo parametro: " + metodos.get(i).getParametrosTipo().get(j));    
          }
          for(int j = 0; j < metodos.get(i).getVariaveisLocais().size(); j++){
        	  System.out.println("nome variavel local: " + metodos.get(i).getVariaveisLocais().get(j).getVariavelNome() + 
                      " tipo variavel local: " + metodos.get(i).getVariaveisLocais().get(j).getVariavelTipo());
          }
      }
  }

	public void operacao() {
		String op1 = null;
		String op1Tipo = null;
		String op2 = null;
		String op2Tipo = null;
		String op3 = null;
		String op3Tipo = null;
		for(int i = 1; i <= lastNumber; i++){
	          
            if(dataLineMap.containsKey(i)){
               // System.out.println("linha: " + i + "  dado linha " + dataLineMap.get(i));
                for(int j = 0; j < tokenMap.get(i).size(); j++){
                    if(tokenMap.get(i).get(j).contains("IDENTIFICADOR")){
                    	op1 = dataLineMap.get(i).get(j);
                    	System.out.println("OP1" + op1);
                    	if(tokenMap.get(i).get(j - 1).contains("RELACIONAL")){
                    		op2 = dataLineMap.get(i).get(j - 2);
                    		System.out.println("OP2" + op2);
                    		for(int x = 0; x < classes.size(); x++){//verificacao de variaveis declaradas
                	          for(int g = 0; g < classes.get(x).getVariavelList().size(); g++){//globais
                	        	  if(op1.equals(classes.get(x).getVariavelList().get(g).getVariavelNome())){
                	            		 op1Tipo = classes.get(x).getVariavelList().get(g).getVariavelTipo();
                	            		 System.out.println("OP1" + op1Tipo);
                	            	 }
                	            	 if(op2.equals(classes.get(x).getVariavelList().get(g).getVariavelNome())){
                	            		 op2Tipo = classes.get(x).getVariavelList().get(g).getVariavelTipo();
                	            		 System.out.println("OP2" + op2Tipo);
                	            	 }
                	            }
                	            for(int l = 0; l < classes.get(x).getMetodosList().get(i).getVariaveisLocais().size(); l++){//locais
            	              	   
            	                            System.out.println("BUCETA " + classes.get(x).getMetodosList().get(i).getVariaveisLocais().get(l).getVariavelTipo());
            	                             if(op1.equals(classes.get(x).getMetodosList().get(i).getVariaveisLocais().get(l).getVariavelNome())){
                        	            		 op1Tipo = classes.get(x).getMetodosList().get(i).getVariaveisLocais().get(l).getVariavelTipo();
                        	            	 }
                        	            	 if(op2.equals(classes.get(x).getMetodosList().get(i).getVariaveisLocais().get(l).getVariavelNome())){
                        	            		 op2Tipo = classes.get(x).getMetodosList().get(i).getVariaveisLocais().get(l).getVariavelTipo();
                        	            	 }       
            	                }
                	            
                	        }//verificação de atribuição de variável
                    		System.out.println( op1Tipo + "   " + op2Tipo);
                    		if(op1Tipo.equals(op2Tipo)|| op1Tipo == null){
                    			System.out.println("ERRO- Variaveis de tipos diferentes" + " " +op1 + " " + op2);
                    			System.out.println("Variavel nao declarada");
                    			errors.add("ERRO- Variaveis de tipos diferentes");
                    		}
                        }
                    }
                }
            }
		}
		
	}
    
}
