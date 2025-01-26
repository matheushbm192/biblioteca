import java.util.Scanner;

public class Biblioteca {

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
            consultarObras();
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
        atualizarInformacoes();
        TelaInicio.inicio();
    }

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

        //chamar menu do professor ou aluno
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
