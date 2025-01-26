import java.util.Scanner;

public class Professor extends Usuario {

    int limiteEmprestimo; 
    String departamento; 

    public Professor(String nome, String email, String senha, String departamento) {
        super(nome, email, senha);
        this.limiteEmprestimo = 10; 
        this.departamento = departamento; 
    }

    public static void menu(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Cadastrar Usuario");
        System.out.println("2- Registrar devolução");
        System.out.println("3- Desbolquar usuário");
        System.out.println("4- Lista de obras emprestadas");
        System.out.println("5- Lista de usuários com atraso nos empréstimos");
        System.out.println("6- Sair");
        int escolha = entrada.nextInt();

        switch (escolha) {
            case 1:
            realizarEmprestimo();
                break;
            case 2:
            consultarObras();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
                menu();
        }
    
    }
}
