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
      
    }

    public static void main(String[] args) throws IOException {
        AnalisadorSintatico a = new AnalisadorSintatico();
        
        if(a.AnaliseToken()){
            a.AnaliseOrder();
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
        AnaliseBloco();
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
        if(tokens.get(2).equals("T_;")){
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
                if(!tokens.get(i).equals(listaVar.get(0))){
                    System.out.println("Erro: Esperado nome da variavel depois da palavra reservada 'VAR' ");
                    erro++;
                    if(!tokens.get(i+1).equals(listaVar.get(1))){
                        System.out.println("Erro: Esperado o delimitador ':' depois de "+listaDeLexemas.get(i));
                        erro++;
                        if(!tokens.get(i+2).equals(listaVar.get(2))){
                            System.out.println("Erro: Esperado tipo da variavel depois de "+listaDeLexemas.get(i+1));
                            erro++;
                            if(!tokens.get(i+2).equals(listaVar.get(3))){
                                System.out.println("Erro: Esperado delimitador ; depois de "+listaDeLexemas.get(i+2));
                                erro++;
                            }   
                        }
                    }
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

    private void AnaliseComandos() {}
    
    private void AnaliseLoop() {}
}
