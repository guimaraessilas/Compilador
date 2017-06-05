package compilador;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author silas
 */

public class AnalisadorSintatico {
    private String caminho = "/home/silas/Documents/NetBeansProjects/compilador/Compilador/src/compilador/teste1.txt";
    private ArrayList<String> tokens = new ArrayList<String>();
    private ArrayList<String> listaDeLexemas = new ArrayList<String>();
    private Integer erro = 0;
    private int i = 4;
    
    public AnalisadorSintatico() throws IOException {
      AnalisadorLexico analisadorLexico = new AnalisadorLexico();  
      analisadorLexico.exec(caminho);
      listaDeLexemas = analisadorLexico.getListaDeLexema();
      tokens = analisadorLexico.getTokens();
        System.out.println(tokens);
        System.out.println(listaDeLexemas);
    }

    public void exec() throws IOException {
        
        if(AnaliseToken()){
            AnaliseOrder();
        }else{
            System.out.println("Arquivo vazio");
        }
    }

    public boolean AnaliseToken(){
        return !tokens.isEmpty();
    }
    
    public void AnaliseOrder(){
        AnaliseProgram();
        AnaliseVar();
        //AnaliseBloco();
    }
    
    private void AnaliseProgram() {
        if (!tokens.get(0).equals("T_PROGRAM")){
            System.out.println("Erro: Programa iniciado incorretamente. Esperado a palavra reservada 'PROGRAM'");     
            erro++;
        }
        if(!tokens.get(1).equals("T_NOME")){
            System.out.println("Erro: Esperado nome do programa depois de "+listaDeLexemas.get(0));
            erro++;
        }
        if(!tokens.get(2).equals("T_;")){
            System.out.println("Erro : Delimitador ';' esperado depois de "+listaDeLexemas.get(1));
            erro++;
        }
    }

    private void AnaliseVar() {
      ArrayList<String> listaVar = new ArrayList<String>();
      listaVar.add("T_NOME");
      listaVar.add("T_:");
      listaVar.add("T_TIPO");
      listaVar.add("T_;");
      
        if(tokens.get(3).equals("T_VAR")){
            while(!tokens.get(i).equals("T_BEGIN")){
                if(tokens.get(i).equals(listaVar.get(0))){ 
                    i++;
                    if(tokens.get(i).equals(listaVar.get(1))){
                        i++;
                        if(tokens.get(i).equals(listaVar.get(2))){
                            i++;
                            if(tokens.get(i).equals(listaVar.get(3))){
                                i++;
                                
                            }else{
                                System.out.println("Erro: Esperado delimitador ; depois de "+listaDeLexemas.get(i+2));
                                erro++;
                            }   
                        }else{
                            System.out.println("Erro: Esperado tipo da variavel depois de "+listaDeLexemas.get(i+1));
                            erro++;
                        }
                    }else{
                        System.out.println("Erro: Esperado o delimitador ':' depois de "+listaDeLexemas.get(i));
                        erro++;
                    }
                }else{
                    System.out.println("Erro: Esperado nome da variavel depois da palavra reservada 'VAR' ");
                    erro++;
                }
                
                i=+4;
            }
        }else{
            System.out.println("Erro: Esperado a palavra reservada 'VAR' depois de"+listaDeLexemas.get(2));
            erro++;
        }
    }

    private void AnaliseBloco() {
        //tokens.get(i) tem q conter BEGIN
        AnaliseComandos();
        if(!tokens.get(i).equals("T_END")){
            erro++;
            System.out.println("Erro: faltando a palavra reservada 'END' no fim do programa");
        }
        if(!tokens.get(i+1).equals("T_;")){
            erro++;
            System.out.println("Erro: faltando o delimitador ';' no fim do programa");
        }
        
    }

    private void AnaliseComandos() {
        while(!tokens.get(i).equals("T_END")){
            if(tokens.get(i).equals("T_NOME")){
                AnaliseAtribuicao();
            }else if(tokens.get(i).equals("T_LOOP")){
                i++;
                AnaliseLoop();
            }else{
                System.out.println("Erro: q?");
                erro++;
            }
    
    
        }
                
    }

    private void AnaliseAtribuicao() {
        if(tokens.get(i).equals("T_NOME")){
            i++;
            if(tokens.get(i).equals("T_<-")){
                i++;
                if(tokens.get(i).equals("T_NOME") && tokens.get(i++).equals("T_;")){
                    
                }else{
                    AnaliseSoma();
                }
            }
        }
        
    }


    private void AnaliseSoma() {
        if(tokens.get(i).equals("T_NOME")){
            i++;
            if(tokens.get(i).equals("T_+")){
                i++;
                if(tokens.get(i).equals("T_NOME") && (listaDeLexemas.get(i).equals("1"))){
                    i++;
                    if(tokens.get(i).equals("T_;")){
                        i++;
                    }else{
                        System.out.println("Erro: esperado delimitador ';' depois de: "+ listaDeLexemas.get(i-1));
                        erro++;
                    }
                }else{
                      System.out.println("Erro: Valor " + listaDeLexemas.get(i) + "invalido para esta operaçao!!!");
                      erro++;
                }
            }else{
                System.out.println("Erro: operaçao invalida: operador nao reconhecido!!");
                erro++;
            }
            
        }else{
            System.out.println("Erro: qq?");
            erro++;
        }
        
    }
    
    private void AnaliseLoop() {

        if (tokens.get(i).equals("T_NOME")){
             while(!tokens.get(i).equals("T_END")){
                 AnaliseAtribuicao();
                 i++;
             }
             i++;
        }else{
            System.out.println("Erro: valor invalido depois de 'LOOP'");
            erro++;
        }
    }

 }
