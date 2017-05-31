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
    
    private ArrayList<String> tProgram = new ArrayList<String>();
    
    public AnalisadorSintatico() throws IOException {
      AnalisadorLexico analisadorLexico = new AnalisadorLexico();  
      analisadorLexico.exec(caminho);
      
      
      tokens = analisadorLexico.getListaDeLexema();
      Integer count = 0;
      
      
      
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
        if (tokens.get(0).equals(analisadorLexico.))
    }

    private void AnaliseVar() {

    }

    private void AnaliseBloco() {
    
    }
    
}
