import java.io.*;
import java.util.stream.Stream;

public class LoginTxt {
   private static String enderecoArquivo = "./Persistencia/login.txt";

    public void lerArquivo(){
        try{
            FileReader arquivo  = new FileReader(enderecoArquivo);

            BufferedReader bufLer = new BufferedReader(arquivo);
            Stream<String> linha;
            //do {
                 linha = bufLer.lines();
           // }while(linha != "");





        }catch(IOException ex){
            System.err.println("Arquivo corrompido");
        }
        
    }

    //criar método para adicionar novo usuário no arquivo; 

    // criar método para conferir se usuário esta que esta logando já esta cadastrado;
    
    
}
