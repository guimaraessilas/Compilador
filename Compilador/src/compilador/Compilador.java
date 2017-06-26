package compilador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

/**
 *
 * @author Silas
 */
public class Compilador {

    public static void main(String[] args) throws IOException {

        AnalisadorSintatico a = new AnalisadorSintatico();
        a.exec();
        System.out.println(a.getTokens());
        System.out.println(a.getListaDeLexemas());

        try {
            FileWriter arq;
            arq = new FileWriter("codigo.c");
            PrintWriter gravarArq = new PrintWriter(arq);

            //for (int i = 0; i < a.getTokens().size(); i++) {
                int i=0;
                if (a.getTokens().get(i).equals("T_PROGRAM") && (a.getTokens().get(i+1).equals("T_NOME")) && (a.getTokens().get(i+2).equals("T_;"))) {
                    gravarArq.printf("#include <studio.h> %n");
                    gravarArq.printf("int main(void){ %n");
                     i=i+3;
                }
                
                if(a.getTokens().get(i).equals("T_VAR")){
                    i++;
                    while(!(a.getTokens().get(i).equals("T_BEGIN"))){
                        if(a.getTokens().get(i).equals("T_NOME")){
                            i++;
                            if(a.getTokens().get(i).equals("T_:")){
                                i++;
                                if(a.getTokens().get(i).equals("T_TIPO")){
                                    i++;
                                    if(a.getTokens().get(i).equals("T_;")){
                                        gravarArq.printf("int "+ a.getListaDeLexemas().get(i-3) +"; %n");
                                        i++;
                                    }
                                }
                            }
                            
                        }
                    }
                    i++;
                    
                    while (!a.getTokens().get(i).equals("T_END")) {
                        if (a.getTokens().get(i).equals("T_NOME")) {
                            i++;
                            if(a.getTokens().get(i).equals("T_<-")){
                                i++;
                                if(a.getTokens().get(i).equals("T_NOME")){
                                    i++;
                                    if(a.getTokens().get(i).equals("T_;")){
                                        gravarArq.printf(a.getListaDeLexemas().get(i-3) +"="+a.getListaDeLexemas().get(i-1)+"; %n");
                                        i++;
                                    }
                                }
                               
                            }
                            i++;
                        }
                        System.out.println(a.getTokens().get(i));
                        i++;
                        if (a.getTokens().get(i).equals("T_LOOP")) {
                            System.out.println(a.getTokens().get(i));
                            while(!a.getTokens().get(i).equals("T_END")){
                                gravarArq.printf("int i; %n");
                                gravarArq.printf("for(i=0; i<="+a.getListaDeLexemas().get(i+1)+";i++){");
                                i=i+3;
                            }
                            
                                                      
                            
                            
                            i++;
                        }
                    }
        
            }
            arq.close();
            gravarArq.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
