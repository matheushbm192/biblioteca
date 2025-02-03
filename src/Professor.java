import java.util.Scanner;

public class Professor extends Usuario {

   private final int limiteEmprestimo = 10;
   private String departamento;

    public Professor(String nome, String email, String senha, String departamento) {
        super(nome, email, senha);
        this.departamento = departamento; 
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public  void menu(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Realizar Empréstimo");//escrever a informação no arquivo dados
        System.out.println("2- Consultar Informações da obra");//feito
        System.out.println("3 - Sair");//feito

        int escolha = entrada.nextInt();

        switch (escolha) {
            case 1:
                Biblioteca.realizarEmprestimo(this);
                break;
            case 2:
                while (true) {

                    System.out.println("Para consultar um livro através do Id, digite 1.");
                    System.out.println("Para consultar um livro através do título, digite 2.");
                    int resposta = entrada.nextInt();
                    entrada.nextLine();
                    if (resposta == 1) {
                        System.out.println("Informe4 o Id do livro que deseja consultar: ");
                        String id = entrada.nextLine();
                        System.out.println("Debug id: "+id);
                        Obra obra = Biblioteca.consultarObraId(id);
                        Biblioteca.imprimirResultadoConsulta(obra);
                        break;
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        String titulo = entrada.nextLine();
                        entrada.nextLine();
                        Obra obra = Biblioteca.consultarObraTitulo(titulo);
                        Biblioteca.imprimirResultadoConsulta(obra);
                        break;
                    }
                }
                break;
            case 3:
                sair();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
        }
        this.menu();
    }
    public static void sair(){
        Main.main();
    }

}
