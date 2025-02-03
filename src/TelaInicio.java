import java.util.ArrayList;
import java.util.Scanner;

public class TelaInicio {

    public static Usuario login() {
        //armazena a lista de pessoas presentes no arquivo de login 
        ArrayList<String[]> quantidade = Biblioteca.lerLogin();
        //verifica se existe pessoas no arquivo
        if (quantidade.size() != 0) {
            //se tiver pessoas, roda o login 
            Scanner entrada = new Scanner(System.in);

            //armazena email e senha escritas pelo usuário
            System.out.println("Bem vindo a biblioteca!");
            System.out.println();
            System.out.println("Realize seu login abaixo.");
            System.out.println("Email: ");
            String email = entrada.nextLine();
            System.out.println("Senha: ");
            String senha = entrada.nextLine();

            //chama função que procura email e senha do usuário no arquivo de login
            boolean validaUsuario = Biblioteca.validaUsuario(email, senha);

            //se usuário for encontrado
            if (validaUsuario) {
                //armazena dados do usuário da vez num array de String,
                // retornado pela função que busca dados do usuário
                String[] usuario = Biblioteca.retornaDados(email);

                //verifica o tipo do usuário, presente em usuario[3]
                if (usuario[3].equals("1")) {
                    System.out.println("Login realizado com sucesso!\n");
                    //se for Professor, retorna um objeto dele
                    return new Professor(usuario[2], usuario[0], usuario[1], usuario[4]);

                } else if (usuario[3].equals("2")) {
                    System.out.println("Login realizado com sucesso!\n");
                    //se for Aluno, retorna um objeto dele
                    return new Aluno(usuario[2], usuario[0], usuario[1], usuario[4], usuario[5]);


                } else {
                    System.out.println("Login realizado com sucesso!\n");
                    //se for Bibliotecário, retorna um objeto dele
                    return new Bibliotecario(usuario[2], usuario[0], usuario[1], usuario[4]);
                }

            } else {
                //se usuário não for encontrado
                System.out.println("Login não encontrado no sistema.\n");
                System.out.println("Tente novamente");
                //caso usuário erre o login, login() é chamado para ele tentar novamente
                return login();
            }
        } else {
            //caso não tenha usuários no arquivo, 
            //o primeiro cadastro tem que pertencer a um bibliotecário 
            cadastroBibliotecario();
        }
        //se nada acontecer, retorna null
        return null;

    }

    public static void cadastroBibliotecario() {
        //cadastra um bibliotecário
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
        //cria um novo objeto de bibliotecário
        Bibliotecario novoBibliotecario = new Bibliotecario(nome, email, senha, telefone);
        //adiciona no arquivo de login 
        Biblioteca.addLoginBibliotecario(novoBibliotecario);
        System.out.println("Cadastro realizado com sucesso!\n");
        //chama login novamente 
        login();

    }

}
