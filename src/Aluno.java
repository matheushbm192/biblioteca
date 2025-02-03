import java.util.Scanner;

public class Aluno extends Usuario {
    private String matricula;
    private String curso;
    private final int limiteEmprestimo = 2;

    public Aluno(String nome, String email, String senha, String matricula, String curso) {
        super(nome, email, senha);
        this.matricula = matricula;
        this.curso = curso;

    }

    public Aluno(String nome, String email, String senha, String matricula){
       super(nome, email, senha);
        this.matricula = matricula;

    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }

    @Override
    public String getEmail() {
        return super.getEmail();
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
                        System.out.println("Informes o Id do livro que deseja consultar: ");
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
        Main.main(new String[] {});
    }

}
