import java.util.Scanner;

public class TelaInicio {

    public static void inicio() {
    //verificar se já existe algum bibliotecário cadastrado!
    // caso tenha:
    }
    public static String login(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Bem vindo a biblioteca!");
        System.out.println();
        System.out.println("Realize seu login abaixo.");
        System.out.println("Email: ");
        String email = entrada.nextLine();
        System.out.println("Senha: ");
        String senha = entrada.nextLine();
        //verificar se usuario existe e se existir, retornar um array todos os dados presentes em login.txt
        // formatar o array em uma instancia de
        //chamar função que adiciona no arquivo 
        System.out.println("Cadastro realizado com sucesso!\n");
        Biblioteca.menu();
        return email;
    }

    public static void cadastroBibliotecario() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Cadastre o primeiro bibliotecário.");
        System.out.println();
        System.out.println("Informe o nome do usuário: ");
        String nome = entrada.nextLine();
        System.out.println("Informe o email: ");
        String email = entrada.nextLine();
        System.out.println("Usuário, digite a senha: ");
        String senha = entrada.nextLine();
        System.out.println("Informe o número de telefone: ");
        String telefone = entrada.nextLine();
        Bibliotecario novoBibliotecario = new Bibliotecario(nome, email, senha, telefone);
        //chamar função que adiciona no arquivo;
        System.out.println("Cadastro realizado com sucesso!\n");
        Bibliotecario.menu();
    }




/*public static void cadastrarBibliotecario(){
    Scanner entrada = new Scanner(System.in);
        System.out.println("Informe o nome do usuário: ");
        String nome = entrada.nextLine();
        System.out.println("Informe o email: ");
        String email = entrada.nextLine();
        System.out.println("Usuário, digite a senha: ");
        String senha = entrada.nextLine(); 
        System.out.println("Informe o número de telefone: ");
        String telefone = entrada.nextLine();
        Bibliotecario novoBibliotecario = new Bibliotecario(nome, email, senha, telefone);

        //analisar qual tipo de usuário entrou para chamar seu determinado menu; 

}*/

}
