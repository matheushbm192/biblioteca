import java.util.ArrayList;
import java.util.Scanner;

public class TelaInicio {

    public static Usuario login() {
        ArrayList<String[]> quantidade = Biblioteca.lerLogin();
        System.out.println(quantidade.size());
        if (quantidade.size() != 0) {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Bem vindo a biblioteca!");
            System.out.println();
            System.out.println("Realize seu login abaixo.");
            System.out.println("Email: ");
            String email = entrada.nextLine();
            entrada.nextLine();
            System.out.println("Senha: ");
            String senha = entrada.nextLine();
            entrada.nextLine();
            System.out.println(email);
            System.out.println(senha);
            boolean validaUsuario = Biblioteca.validaUsuario(email, senha);
            System.out.println(validaUsuario);
            if (validaUsuario) {
                String[] usuario = Biblioteca.retornaDados(email);

                if (usuario[3].equals("1")) {
                    System.out.println("Login realizado com sucesso!\n");
                    return new Aluno(usuario[2], usuario[0], usuario[1], usuario[4], usuario[5]);

                } else if (usuario[3].equals("2")) {
                    System.out.println("Login realizado com sucesso!\n");
                    return new Professor(usuario[2], usuario[0], usuario[1], usuario[4]);

                } else {
                    System.out.println("Login realizado com sucesso!\n");
                    return new Bibliotecario(usuario[2], usuario[0], usuario[1], usuario[4]);
                }

            } else {
                System.out.println("Login não encontrado no sistema.\n");
                System.out.println("Tente novamente");
                login();
            }
        } else {
            cadastroBibliotecario();
        }
        return null;

    }

    public static void cadastroBibliotecario() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Cadastre o primeiro bibliotecário.");
        System.out.println();
        System.out.println("Informe o nome do usuário: ");
        String nome = entrada.nextLine();
        entrada.nextLine();
        System.out.println("Informe o email: ");
        String email = entrada.nextLine();
        entrada.nextLine();
        System.out.println("Usuário, digite a senha: ");
        String senha = entrada.nextLine();
        entrada.nextLine();
        System.out.println("Informe o número de telefone: ");
        String telefone = entrada.nextLine();
        entrada.nextLine();
        Bibliotecario novoBibliotecario = new Bibliotecario(nome, email, senha, telefone);
        Biblioteca.addLoginBibliotecario(novoBibliotecario);
        System.out.println("Cadastro realizado com sucesso!\n");
        login();

    }

}
