import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;


public class Biblioteca {
    public static String acervo = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\acervo.csv";
    public static  String historicoEmprestimo = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\historicoEmprestimos.txt";
    public static  String usarioBloqueado = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\usariosBloqueados";

    public static void menu(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Realizar Empréstimo");
        System.out.println("2- Consultar Informações da obra");
        System.out.println("3 - Sair");

        int escolha = entrada.nextInt();

        switch (escolha) {
            case 1:
                realizarEmprestimo();
                break;
            case 2:
                //consultarObras();
                break;
            case 3:
                sair();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
                menu();
        }
    }

    public static void sair(){
      //  atualizarInformacoes();
        TelaInicio.inicio();
    }

    // passar id do usuario
    //alterar arquivo acervo; 
    public static void realizarEmprestimo() {

        Scanner entrada = new Scanner(System.in);
       // boolean bloqueado = usuarioBloqueado();

       // if (bloqueado = true) {
            System.out.println(
                    "Você está com atrasos na devolução, portanto, está bloqueado para pegar novos exemplares.");
      //  } else {

            boolean limite = limiteEmprestimos();
            if (limite = true) {
                System.out.println(
                        "Seu limite de empréstimos já foi alcançado. Realize devolução para pegar novos livros.");
            } else {
                System.out.println("Para realizar um empréstimo, "
                        + "informe o nome ou ID do livro que deseja: ");
                String exemplar = entrada.nextLine();

               // boolean disponibilidade = exemplarDisponivel();
               // if(disponibilidade = false){
                    System.out.println("Não há mais exemplares para realizar empréstimo.");
                }//else{
                    //realizar emprestimo;
                    atualizarInformacoes();
              //  }

            }
      //  }

        //chamar menu do professor ou aluno
   // }
    public static ArrayList<Obra> lerAcervo(){

        ArrayList<String[]> valores = new ArrayList<String[]>();
        ArrayList<Obra> resultado = new ArrayList<Obra>();

        try  {
            BufferedReader buffer = new BufferedReader(new FileReader(acervo));
            String linha;
            while ((linha = buffer.readLine()) != null) {
                //todo checar se roda sem o split
                valores.add(linha.split("\n")) ;
            }

            for (String[] valor : valores) {
                String[] separado = valor[0].split(",");
                Obra formataObra = new Obra(separado[0],separado[1], Integer.parseInt(separado[2]));
                resultado.add(formataObra);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static ArrayList<Obra> consultarObras(){
        return lerAcervo();
    }

    // passar como parametro a obra (id ou nome)
    public static Obra consultarObraId(int id) {
        return lerAcervo().get(id-1);
    }

    public static Obra consultarObraTitulo(String nomeObra){
        for (Obra obra : lerAcervo()){
            if(obra.getTitulo().equals(nomeObra)){
                return obra;
            }
        }
        return null;
    }

    // passar exemplar de parametro
    public static boolean exemplarDisponivel(String nomeObra) {
        //realizar busca no txt pelo nome
        for (Obra obra : lerAcervo()){
            if(obra.getTitulo().equals(nomeObra)){
                if(obra.getQuantidade() > 0){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Emprestimos> lerHistoricoEmprestimos(){
        ArrayList<String[]> valores = new ArrayList<>();
        ArrayList<Emprestimos> resultado = new ArrayList<>();

        try{
            BufferedReader buffer = new BufferedReader(new FileReader(historicoEmprestimo));
            String linha;
            while ((linha = buffer.readLine()) != null) {
                valores.add(linha.split("\n"));
            }
            for(String[] valor : valores){
                Emprestimos formataEmprestimo = new Emprestimos(valor[0],valor[1], LocalDate.parse(valor[2]),valor[3],Integer.parseInt(valor[4]),valor[5]);
                resultado.add(formataEmprestimo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void  gerarUsuariosBlqueados(){
        ArrayList<Emprestimos> emprestimos = lerHistoricoEmprestimos();
        LocalDate hoje = LocalDate.now();
        for (Emprestimos emprestimo : emprestimos){
            long diasDiferenca = ChronoUnit.DAYS.between(hoje, emprestimo.getData());
            if(diasDiferenca > 7){
                try{
                BufferedWriter buffer = new BufferedWriter(new FileWriter(usarioBloqueado));

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    // passar id do usuario como parametro


    public static void atualizarInformacoes() {
        //não entendi esse!
    }

    public static boolean limiteEmprestimos() {
        // verifica qual é o tipo de usuario e se seu limite de emprestimos foi
        // alcançado
        // ( professor - 10; aluno - 2; )
        return false;
    }

}
