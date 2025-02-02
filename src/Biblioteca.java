import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Biblioteca {
    public static String acervo = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\acervo.csv";
    public static  String historicoEmprestimo = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\historicoEmprestimos.txt";
    public static  String usuarioBloqueado = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\usariosBloqueados.txt";
    public static String dados = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\dados.txt";
    public static String login = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\login.txt";
   

    
    public static void menu(Object usuario){
        Scanner entrada = new Scanner(System.in);

        String email = " ";
        //fazer de limite de empréstimo

        if (usuario instanceof Aluno) {
            Aluno usuarioVez = (Aluno) usuario; // Converte para Aluno
            email = usuarioVez.getEmail();
            
        } else if (usuario instanceof Professor) {
            Professor usuarioVez = (Professor) usuario; // Converte para Professor
            email = usuarioVez.getEmail();
        }
    

        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Realizar Empréstimo");//escrever a informação no arquivo dados
        System.out.println("2- Consultar Informações da obra");//feito
        System.out.println("3 - Sair");//feito

        int escolha = entrada.nextInt();

        switch (escolha) {
            case 1:
                realizarEmprestimo(email);
                break;
            case 2:

                boolean respostaValida = false;
                while (!respostaValida) {

                    System.out.println("Para consultar um livro através do Id, digite 1.");
                    System.out.println("Para consultar um livro através do título, digite 2.");
                    int resposta = entrada.nextInt();

                    if (resposta == 1) {
                        System.out.println("Informe o Id do livro que deseja consultar: ");
                        int id = entrada.nextInt();
                        Obra obra = consultarObraId(id);
                        imprimirResultadoConsulta(obra);
                        respostaValida = true;
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        String titulo = entrada.nextLine();
                        Obra obra = consultarObraTitulo(titulo);
                        imprimirResultadoConsulta(obra);

                        respostaValida = true;
                    } else {
                        respostaValida = false;
                    }

                    break;
                }

            case 3:
                sair();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
        }
    }

    public static void sair() {
        // atualizarInformacoes();
        TelaInicio.login();
    }

    // passar id do usuario
    // alterar arquivo acervo;
    public static void realizarEmprestimo(String email) {

        Scanner entrada = new Scanner(System.in);
        boolean bloqueado = usuarioBloqueado(email);

        if (bloqueado) {
            System.out.println(
                    "Você está com atrasos na devolução, portanto, está bloqueado para pegar novos exemplares.");
        } else {

            boolean limite = limiteEmprestimos();
            if (limite) {
                System.out.println(
                        "Seu limite de empréstimos já foi alcançado. Realize devolução para pegar novos livros.");
            } else {

                boolean respostaValida = false;
                boolean disponibilidade = false;
                while (!respostaValida) {

                    System.out.println("Para realizar um empréstimo através do Id, digite 1.");
                    System.out.println("Para realizar um empréstimo através do título, digite 2.");
                    int resposta = entrada.nextInt();

                    if (resposta == 1) {
                        System.out.println("Informe o Id do livro que deseja consultar: ");
                        int id = entrada.nextInt();
                        Obra obra = consultarObraId(id);
                        disponibilidade = exemplarDisponivel(obra);
                        respostaValida = true;
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        String titulo = entrada.nextLine();
                        Obra obra = consultarObraTitulo(titulo);
                        imprimirResultadoConsulta(obra);
                        disponibilidade = exemplarDisponivel(obra);
                        respostaValida = true;
                    } else {
                        respostaValida = false;
                    }

                    if (!disponibilidade) {
                        System.out.println("Não há mais exemplares para realizar empréstimo.");
                    } else {
                        //chamar método que acresenta empréstimo no histórico 
                        //alterar a quantidade de livros no acervo
                        }

                       
                    }

                }
            }

        }
    

    public static ArrayList<Obra> lerAcervo() {

        ArrayList<String[]> valores = new ArrayList<String[]>();
        ArrayList<Obra> resultado = new ArrayList<Obra>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(acervo));
            String linha;
            while ((linha = buffer.readLine()) != null) {
                // todo checar se roda sem o split
                valores.add(linha.split("\n"));
            }

            for (String[] valor : valores) {
                String[] separado = valor[0].split(",");
                Obra formataObra = new Obra(separado[0], separado[1], Integer.parseInt(separado[2]));
                resultado.add(formataObra);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static ArrayList<String[]> lerUsuariosBloqueados(){

        ArrayList<String[]> valores = new ArrayList<String[]>();
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(usuarioBloqueado));
            String linha;
            while ((linha = buffer.readLine()) != null) {
                valores.add(linha.split("\n"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return valores;
    }

    public static ArrayList<Obra> consultarObras() {
        return lerAcervo();

    }

    public static void imprimirResultadoConsulta(Obra obra) {
        if (obra == null) {
            System.out.println("Obra não encontrada.");
        }
        System.out.println("Resultado da pesquisa: ");
        System.out.println("ID: " + obra.getId());
        System.out.println("Título: " + obra.getTitulo());
        System.out.println("Quantidade: " + obra.getQuantidade());
    }

    public static Obra consultarObraId(int id) {
        for (Obra obra : lerAcervo()) {
            if (obra.getId().equals(id)) {
                return obra;
            }
        }
        return null;
    }

    public static Obra consultarObraTitulo(String nomeObra) {
        for (Obra obra : lerAcervo()) {
            if (obra.getTitulo().equals(nomeObra)) {
                return obra;
            }
        }
        return null;
    }

    public static boolean exemplarDisponivel(Obra obra) {
        /*
         * for (Obra obra : lerAcervo()) {
         * if (obra.getTitulo().equals(nomeObra)) {
         * if (obra.getQuantidade() > 0) {
         * return true;
         * }
         * }
         * }
         * return false;
         */
        if (obra.getQuantidade() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public static ArrayList<Emprestimos> lerHistoricoEmprestimos() {
        ArrayList<String[]> valores = new ArrayList<>();
        ArrayList<Emprestimos> resultado = new ArrayList<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(historicoEmprestimo));
            String linha;
            while ((linha = buffer.readLine()) != null) {
                valores.add(linha.split("\n"));
            }
            for (String[] valor : valores) {
                Emprestimos formataEmprestimo = new Emprestimos(valor[0], valor[1], LocalDate.parse(valor[2]), valor[3],
                        Integer.parseInt(valor[4]), valor[5]);
                resultado.add(formataEmprestimo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static ArrayList<String[]> lerDados(){
        ArrayList<String[]> valores = new ArrayList<>();
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(dados));
            String linha;
            while ((linha = buffer.readLine()) != null) {
                valores.add(linha.split("\n"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return valores;
    }

    public static void  gerarUsuariosBloqueados(){
        ArrayList<String[]> linhas = lerDados();

        ArrayList<String[]> livros = new ArrayList<>();

        ArrayList<String[]> atrasados = new ArrayList<>();

        LocalDate hoje = LocalDate.now();

        for (String[] linha : linhas) {

            String[] valores = linha[0].split(",");
            System.out.println(valores[0]);

            for (int i = 2; i < Integer.parseInt(valores[1]) + 2; i++) {
                String[] resultado = valores[i].split("-");
                livros.add(resultado);
            }
            System.out.println(Arrays.toString(livros.getFirst()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (String[] livro : livros) {
                long diasDiferenca = ChronoUnit.DAYS.between(hoje, LocalDate.parse(livro[1],formatter));
                System.out.println(diasDiferenca);
                if ((diasDiferenca * -1) > 7) {
                    String[] usuarioAtrasado = {valores[0], livro[0] + "-" + livro[1]};
                    atrasados.add(usuarioAtrasado);
                }
            }
        }
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(usuarioBloqueado))) {

            for (String[] linha : atrasados) {
                System.out.println(Arrays.toString(linha));
                // Junta os elementos do array com vírgula e espaço entre eles
                String linhaFormatada = String.join(",", linha);
                System.out.println("1 " +linhaFormatada);
                escritor.write(linhaFormatada); // Escreve a linha no arquivo
                escritor.newLine(); // Pula para a próxima linha
            }
            System.out.println("Arquivo escrito com sucesso!");

            } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    //arrumar para passar o usuario da vez(email);
    public static boolean usuarioBloqueado(String email) {
        gerarUsuariosBloqueados();
        ArrayList<String[]>linhas = lerUsuariosBloqueados();

        for(String[] linha: linhas){
            if(linha[0].equals(email)){
                return true;
            }
        }
        return false;
        
    }

    //fazer!
    public static void gerarHistoricoEmpres(){

    }

    public static ArrayList<String[]> lerLogin(){
        ArrayList<String[]> valores = new ArrayList<>();
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(login));
            String linha;
            while ((linha = buffer.readLine()) != null) {
                valores.add(linha.split("\n"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return valores;
    }

    public static boolean validaUsuario(String email, String senha){
        ArrayList<String[]>usuarios = lerLogin();

        for(String[] usuario: usuarios){
            if((usuario[0].equals(email)) && (usuario[1].equals(senha))){
                return true;
            }
        }
        return false;
        

    }

    public static String[] retornaDados(String email){
        ArrayList<String[]>usuarios = lerLogin();

        for(String[] usuario: usuarios){
            if(usuario[0].equals(email)){
                return usuario;
            }
        }
        return null;
        /*for (String[] valor : valores) {
            Emprestimos formataEmprestimo = new Emprestimos(valor[0], valor[1], LocalDate.parse(valor[2]), valor[3],
                    Integer.parseInt(valor[4]), valor[5]);
            resultado.add(formataEmprestimo);*/
    }

    public static void addLoginAluno( Aluno usuario){
        //todo: escrever usuario no txt
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(login,true))) {
                // Junta os elementos do array com vírgula e espaço entre eles

            String[] login = {usuario.getEmail(),usuario.getSenha(),usuario.getNome(),"2",usuario.getMatricula(),usuario.getCurso()};
            String linhaFormatada = String.join(",", login);
            escritor.write(linhaFormatada);

            escritor.write(linhaFormatada); // Escreve a linha no arquivo
            escritor.newLine(); // Pula para a próxima linha

            System.out.println("Arquivo escrito com sucesso!");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    public static void addLoginProfessor( Professor usuario){
        //todo: escrever usuario no txt
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(login,true))) {
            // Junta os elementos do array com vírgula e espaço entre eles

            String[] login = {usuario.getEmail(),usuario.getSenha(),usuario.getNome(),"1",usuario.getDepartamento()};
            String linhaFormatada = String.join(",", login);
            escritor.write(linhaFormatada);

            escritor.write(linhaFormatada); // Escreve a linha no arquivo
            escritor.newLine(); // Pula para a próxima linha

            System.out.println("Arquivo escrito com sucesso!");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    public static void addLoginBibliotecario( Bibliotecario usuario){
        //todo: escrever usuario no txt
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(login,true))) {
            // Junta os elementos do array com vírgula e espaço entre eles

            String[] login = {usuario.getEmail(),usuario.getSenha(),usuario.getNome(),"3",usuario.getTelefone(), String.valueOf(usuario.getQntDevolucoes())};
            String linhaFormatada = String.join(",", login);
            escritor.write(linhaFormatada);

            escritor.write(linhaFormatada); // Escreve a linha no arquivo
            escritor.newLine(); // Pula para a próxima linha

            System.out.println("Arquivo escrito com sucesso!");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    

    public static boolean limiteEmprestimos(String email) {
        //dados
        ArrayList<String[]> logins = lerLogin();

        for(String[] login: logins){
            if(login[0].equals(email)){
                if(){

                }
                return true;
            }
        }
        return false;

        return false;
    }

}
