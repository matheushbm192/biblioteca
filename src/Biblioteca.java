import java.util.Scanner;

public class Biblioteca {

    // passar id do usuario
    public static void realizarEmprestimo() {

        Scanner entrada = new Scanner(System.in);
        boolean bloqueado = usuarioBloqueado();

        if (bloqueado = true) {
            System.out.println(
                    "Você está com atrasos na devolução, portanto, está bloqueado para pegar novos exemplares.");
        } else {

            boolean limite = limiteEmprestimos();
            if (limite = true) {
                System.out.println(
                        "Seu limite de empréstimos já foi alcançado. Realize devolução para pegar novos livros.");
            } else {
                System.out.println("Para realizar um empréstimo, "
                        + "informe o nome ou ID do livro que deseja: ");
                String exemplar = entrada.nextLine();

               boolean disponibilidade = exemplarDisponivel();
               if(disponibilidade = false){
                    System.out.println("Não há mais exemplares para realizar empréstimo.");
               }else{
                //realizar emprestimo;
                atualizarInformacoes();
               }
                
            }
        }
    }

    // passar como parametro a obra (id ou nome)
    public static void consultarObras() {

    }

    // passar exemplar de parametro
    public static boolean exemplarDisponivel() {
        return false;
    }

    // passar id do usuario como parametro
    public static boolean usuarioBloqueado() {
        return false;
    }

    public static void atualizarInformacoes() {

    }

    public static boolean limiteEmprestimos() {
        // verifica qual é o tipo de usuario e se seu limite de emprestimos foi
        // alcançado
        // ( professor - 10; aluno - 2; )
        return false;
    }

}
