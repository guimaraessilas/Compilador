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
    Integer delimitadoresInt;
    Integer palavrasReservadasInt;
    Integer numeroLinha;
    Integer numeroNomes;
    
    public static void main(String[] args) throws IOException {
        AnalisadorLexico a = new AnalisadorLexico();
        a.exec("/home/silas/Documents/NetBeansProjects/Compilador/src/compilador/teste1.txt");
        
    }
    
    public AnalisadorLexico() {
        numeroLinha = 0;
        numeroNomes = 0;
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
        tkDelimitador.add("/+");
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
    
    public void lerArquivo(String caminho) throws IOException{
        FileReader arq = null;
        
        try {
            arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            System.out.println("\n--------------------Arquivo aberto--------------------\n\n");

            while (linha != null) {
                numeroLinha++;
                analiseLexica(linha);
                linha = lerArq.readLine();
            }
            arq.close();
        
        } catch (FileNotFoundException ex) {
            System.out.println("\n--------------Arquivo inexistente--------------\n\n");
        
        } finally {
            try {
                arq.close();
            } catch (IOException ex) {
                System.out.println("Shazam carai");
            }
        }
        
    }
    
    public void analiseLexica(String linha){
        String[] cadeia= null;

        for (String tkDelimitador1 : tkDelimitador) {
            cadeia = linha.split(tkDelimitador1);  
        }   
        
        analiseCadeia(cadeia); 
        analiseDelimitador(linha);
    }

    private void analiseCadeia(String[] cadeia) { // Palavra Reservada e nomes
        for (String cadeia1 : cadeia) {
            for (String tkPalavraReservada1 : tkPalavraReservada) {
                if(!cadeia1.equals(tkPalavraReservada1)){
                    palavrasReservadasInt++;
                    System.out.println("Palavra Reservada: "+cadeia1);
                }else if (cadeia1.contains(tkPalavraReservada1)){
                    analiseNome(cadeia1);
                    System.out.println("Nome: "+cadeia1);
                }
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

        numeroNomes++;
    }
    
    private void analiseDelimitador(String linha) {  // Delimitadores
        char[] linhaVetor = linha.toCharArray();
        
        for (int i = 0; i<linhaVetor.length; i++) {
            
            if(String.valueOf(linhaVetor[i]).equals("<") && String.valueOf(linhaVetor[i+1]).equals("-")){
                delimitadoresInt++;
                System.out.println("Delimitador lokasso: " + linhaVetor[i] + linhaVetor[i+1]);
                i+=2;
            }
            
            for (String delimitador1 : tkDelimitador) {
                if(String.valueOf(linhaVetor[i]).equals(delimitador1)){
                    delimitadoresInt++;
                    System.out.println("Delimitadores nao lokasso: "+delimitador1);
                }
            }
        }
    }

}