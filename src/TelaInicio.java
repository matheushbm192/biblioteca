import java.util.ArrayList;
import java.util.Scanner;

public class TelaInicio {

    
    public static Object login() {
        ArrayList<String[]> quantidade = Biblioteca.lerLogin();
        if (quantidade != null) {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Bem vindo a biblioteca!");
            System.out.println();
            System.out.println("Realize seu login abaixo.");
            System.out.println("Email: ");
            String email = entrada.nextLine();
            System.out.println("Senha: ");
            String senha = entrada.nextLine();

            boolean validaUsuario = Biblioteca.validaUsuario(email, senha);

            if (validaUsuario) {
                String[] usuario = Biblioteca.retornaDados(email);

                Object usuarioVez = null;

                if (usuario[3].equals("1")) {
                    usuarioVez = new Aluno(usuario[2], usuario[0], usuario[1], usuario[4]);

                } else if (usuario[3].equals("2")) {
                    usuarioVez = new Professor(usuario[2], usuario[0], usuario[1], usuario[4]);

                } else {
                    usuarioVez = new Bibliotecario(usuario[2], usuario[0], usuario[1], usuario[4]);
                }

                System.out.println("Login realizado com sucesso!\n");
                return usuarioVez;

            } else {
                System.out.println("Login não encontrado no sistema.\n");
                return null;
            }
        } else {
            cadastroBibliotecario();
        }
        return null;
        // verificar se usuario existe e se existir, retornar um array todos os dados
        // presentes em login.txt
        // formatar o array em uma instancia de
        // chamar função que adiciona no arquivo

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
        // chamar função que adiciona no arquivo;
        System.out.println("Cadastro realizado com sucesso!\n");
        login();

    }

}
