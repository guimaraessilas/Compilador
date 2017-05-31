package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author silas
 */
public class AnalisadorLexico {

    private ArrayList<String> tkPalavraReservada = new ArrayList<String>();
    private ArrayList<String> tkDelimitador = new ArrayList<String>();
    
    private ArrayList<String> listaNomes = new ArrayList<String>();
    private ArrayList<String> listaDelimitadores = new ArrayList<String>();
    private ArrayList<String> listaPalavrasReservadas = new ArrayList<String>();
    
    private Integer delimitadoresInt;
    private Integer palavrasReservadasInt;
    private Integer numeroLinha;
    private Integer nomesInt;
    private boolean frag = false;
    private ArrayList<String> listaDeLexema = new ArrayList<String>();

    
    public AnalisadorLexico() {
        numeroLinha = 0;
        nomesInt = 0;
        delimitadoresInt = 0;
        palavrasReservadasInt = 0;
        tkPalavraReservada.add("BEGIN");
        tkPalavraReservada.add("PROGRAM");
        tkPalavraReservada.add("VAR");
        tkPalavraReservada.add("LOOP");
        tkPalavraReservada.add("INTEGER");
        tkPalavraReservada.add("END");

        tkDelimitador.add(";");
        tkDelimitador.add(":");
        tkDelimitador.add("+");
        tkDelimitador.add("=");
        tkDelimitador.add("-");
        tkDelimitador.add(" ");
        tkDelimitador.add("\n");
        tkDelimitador.add("<-");
        tkDelimitador.add(" ");

    }

    public void exec(String caminho) throws IOException{
        lerArquivo(caminho);
    }
    
    private void lerArquivo(String caminho) throws IOException{
        FileReader arq = null;
        
        try {
            arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();

            while (linha != null) {
                numeroLinha++;
                analiseLexica(linha);
                linha = lerArq.readLine();
            }
            analiseCadeia(listaDeLexema);
            analiseDelimitador(listaDeLexema);
            
            arq.close();
        
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        
        } finally {
            try {
                arq.close();
            } catch (IOException ex) {
                System.out.println("ERROR: "+ex.getMessage());
            }
        }
        
    }
    
    private void analiseLexica(String linha){
        String[] cadeia= null;
        char[] linhaCharArray = linha.trim().toCharArray();
        
        String lexema =  "";
        int i=0;
        
        for (char charInlinhaCharArray : linhaCharArray) {
            
            if (charInlinhaCharArray== ';' || charInlinhaCharArray==' ' || charInlinhaCharArray==':' || charInlinhaCharArray=='+' || charInlinhaCharArray=='<') {
                    
                if (charInlinhaCharArray == '<'){
                     frag=true;
                 }

                String charInlinhaCharArrayString = String.valueOf(charInlinhaCharArray);
                listaDeLexema.add(lexema);
                if (!Character.isWhitespace(charInlinhaCharArray) && charInlinhaCharArray!='<') {
                        listaDeLexema.add(charInlinhaCharArrayString);
                }
                lexema = new String();

            }else{
                
                if(charInlinhaCharArray!='-'){
                    lexema += charInlinhaCharArray;
                }

            }
            if (frag==true && charInlinhaCharArray=='-'){
                    listaDeLexema.add("<-");
                frag=false;       
            }
        }
    }

    private void analiseCadeia(ArrayList<String> Listalexema) { 
        
        for (String lexema : Listalexema) {
              if(tkPalavraReservada.contains(lexema)){
                  listaPalavrasReservadas.add(lexema);
                  palavrasReservadasInt++;
              }else if(!tkDelimitador.contains(lexema)){
                  analiseNome(lexema);
              }
        }
    }
    
    private void analiseNome(String cadeia1) {

        char[] cadeiaVetor = cadeia1.toCharArray();

        if(!Character.isLetter(cadeiaVetor[0]) && cadeiaVetor.length > 1){
            System.out.println("Erro na linha: "+numeroLinha+". Nome invalido. O nome "
                    +cadeia1+ " nao pode iniciar com numero. ");
            
            return;
        }
        
        if(!Character.isLetter(cadeiaVetor[0]) && cadeiaVetor[0] != '1' && cadeiaVetor.length == 1){
        
            System.out.println("Erro na linha: "+numeroLinha+" ."
                    +cadeia1+" nao e um valor valido");
            return;
        }

        for (char caracterCadeia : cadeiaVetor) {
            if(Character.isLowerCase(caracterCadeia)){
                System.out.println("Erro na linha: "+numeroLinha+". Nome invalido. "
                    + "O nome: " +cadeia1+" nao pode possuir caracteres minusculos");
            
                return;
            }
        }
        listaNomes.add(cadeia1);
        nomesInt++;
    }
    
    private void analiseDelimitador(ArrayList<String> listaLexema) {  // Delimitadores
        
        for (String delimitador : listaLexema) {
              if(tkDelimitador.contains(delimitador)){
                  listaDelimitadores.add(delimitador);
                  delimitadoresInt++;
              }
        }
    }

    public ArrayList<String> getListaNomes() {
        return listaNomes;
    }

    public void setListaNomes(ArrayList<String> listaNomes) {
        this.listaNomes = listaNomes;
    }

    public ArrayList<String> getListaDelimitadores() {
        return listaDelimitadores;
    }

    public void setListaDelimitadores(ArrayList<String> listaDelimitadores) {
        this.listaDelimitadores = listaDelimitadores;
    }

    public ArrayList<String> getListaPalavrasReservadas() {
        return listaPalavrasReservadas;
    }

    public void setListaPalavrasReservadas(ArrayList<String> listaPalavrasReservadas) {
        this.listaPalavrasReservadas = listaPalavrasReservadas;
    }

    public Integer getDelimitadoresInt() {
        return delimitadoresInt;
    }

    public void setDelimitadoresInt(Integer delimitadoresInt) {
        this.delimitadoresInt = delimitadoresInt;
    }

    public Integer getPalavrasReservadasInt() {
        return palavrasReservadasInt;
    }

    public void setPalavrasReservadasInt(Integer palavrasReservadasInt) {
        this.palavrasReservadasInt = palavrasReservadasInt;
    }

    public Integer getNumeroLinha() {
        return numeroLinha;
    }

    public void setNumeroLinha(Integer numeroLinha) {
        this.numeroLinha = numeroLinha;
    }

    public Integer getNumeroNomes() {
        return nomesInt;
    }

    public void setNumeroNomes(Integer nomesInt) {
        this.nomesInt = nomesInt;
    }
}