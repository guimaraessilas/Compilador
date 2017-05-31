package compilador;

import java.io.IOException;


/**
 *
 * @author Silas
 */
public class Compilador {
    public static void main(String[] args) throws IOException {
        AnalisadorLexico a = new AnalisadorLexico();
        a.exec("/home/silas/Documents/NetBeansProjects/compilador/Compilador/src/compilador/teste1.txt");
    
    }
}