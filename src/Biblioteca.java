import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    public static String acervo = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\acervo.csv";
    public static  String historicoEmprestimo = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\historicoEmprestimos.txt";
    public static  String usuarioBloqueado = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\usariosBloqueados.txt";
    public static String dados = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\dados.txt";
    public static String login = "C:\\Users\\55319\\Desktop\\TP3\\Biblioteca\\Persistencia\\login.txt";


   /* public static String acervo = "C:\\Users\\paola\\biblioteca\\Persistencia\\acervo.csv";
    public static  String historicoEmprestimo = "C:\\Users\\paola\\biblioteca\\Persistencia\\historicoEmprestimos.txt";
    public static  String usuarioBloqueado = "C:\\Users\\paola\\biblioteca\\Persistencia\\usariosBloqueados.txt";
    public static String dados = "C:\\Users\\paola\\biblioteca\\Persistencia\\dados.txt";
    public static String login = "C:\\Users\\paola\\biblioteca\\Persistencia\\login.txt";
    */
    public static <T extends Usuario> void realizarEmprestimo(T usuario) {

        String id = " ";
        String titulo = " ";
        int quantidade = 0;
        String email = usuario.getEmail();

        Scanner entrada = new Scanner(System.in);
        boolean bloqueado = usuarioBloqueado(usuario.getEmail());

        if (bloqueado) {
            System.out.println(
                    "Você está com atrasos na devolução, portanto, está bloqueado para pegar novos exemplares.");
        } else {

            boolean limite = limiteEmprestimos(usuario.getEmail(), usuario.getLimiteEmprestimo());
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
                    entrada.nextLine();

                    if (resposta == 1) {
                        System.out.println("Informe o Id do livro que deseja consultar: ");
                        id = entrada.nextLine();
                        entrada.nextLine();
                        Obra obra = consultarObraId(id);
                        titulo = obra.getTitulo();
                        quantidade = obra.getQuantidade();
                        disponibilidade = exemplarDisponivel(obra);
                        respostaValida = true;
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        titulo = entrada.nextLine();
                        entrada.nextLine();
                        Obra obra = consultarObraTitulo(titulo);
                        id = obra.getId();
                        quantidade = obra.getQuantidade();
                        imprimirResultadoConsulta(obra);
                        disponibilidade = exemplarDisponivel(obra);
                        respostaValida = true;
                    } else {
                        respostaValida = false;
                    }

                    if (!disponibilidade) {
                        System.out.println("Não há mais exemplares para realizar empréstimo.");
                    } else {
                        // String id, String titulo, String email,int quantidade
                        Emprestimos emprestimo = new Emprestimos(id, titulo, usuario.getEmail(), quantidade);
                        addEmprestimoHist(emprestimo);
                        addEmprestDados(emprestimo);
                        // alterar a quantidade de livros no acervo
                        alteraQuantAcervo(id);

                    }

                }

            }
        }

    }

    public static void alteraQuantAcervo(String id) {

        List<String> linhaAtualizada = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(acervo))) {
            String linha;

            while ((linha = buffer.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados[0].equals(id)) {
                    int quantidadeAtual = Integer.parseInt(dados[2]);
                    dados[2] = String.valueOf(quantidadeAtual - 1);
                }

                linhaAtualizada.add(String.join(",", dados));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(acervo))) {
            for (String linha : linhaAtualizada) {
                escritor.write(linha);
                escritor.newLine();
            }
            System.out.println("Quantidade de exemplares atualizada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo");
        }
    }

    public static ArrayList<Obra> lerAcervo() {
        ArrayList<Obra> resultado = new ArrayList<>();
    
        try (BufferedReader buffer = new BufferedReader(new FileReader(acervo))) {
            String linha;
            while ((linha = buffer.readLine()) != null) {
                String[] separado = linha.split(",");
                
                if (separado.length >= 3) {
                    Obra formataObra = new Obra(separado[0], separado[1], Integer.parseInt(separado[2]));
                    resultado.add(formataObra);
                    
                } else {
                    System.out.println("Erro ao processar linha: " + linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static ArrayList<String[]> lerUsuariosBloqueados() {

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

    public static Obra consultarObraId(String id) {
        for (Obra obra : lerAcervo()) {
            
            if (obra.getId().trim().equals(id.trim())) { 
                System.out.println("Encontrado: " + obra.getTitulo());
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

    public static ArrayList<String[]> lerDados() {
        ArrayList<String[]> valores = new ArrayList<>();
        try {
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

    public static void gerarUsuariosBloqueados() {
        ArrayList<String[]> linhas = lerDados();
        ArrayList<String[]> livros = new ArrayList<>();
        ArrayList<String[]> atrasados = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (String[] linha : linhas) {

            String[] valores = linha[0].split(",");

            for (int i = 2; i < Integer.parseInt(valores[1]) + 2; i++) {
                String[] resultado = valores[i].split("-");
                long diasDiferenca = ChronoUnit.DAYS.between(hoje, LocalDate.parse(resultado[1], formatter));
                if ((diasDiferenca * -1) > 7) {
                    String[] usuarioAtrasado = { valores[0], resultado[0] + "-" + resultado[1] };
                    atrasados.add(usuarioAtrasado);
                }
            }
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(usuarioBloqueado))) {

            for (String[] linha : atrasados) {
                // Junta os elementos do array com vírgula e espaço entre eles
                String linhaFormatada = String.join(",", linha);

                escritor.write(linhaFormatada); // Escreve a linha no arquivo
                escritor.newLine(); // Pula para a próxima linha
            }

            System.out.println("Arquivo escrito com sucesso!");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    public static boolean usuarioBloqueado(String email) {
        gerarUsuariosBloqueados();
        ArrayList<String[]> linhas = lerUsuariosBloqueados();

        for (String[] linha : linhas) {
            if (linha[0].equals(email)) {
                return true;
            }
        }
        return false;

    }

    public static ArrayList<String[]> lerLogin() {
        ArrayList<String[]> valores = new ArrayList<>();
        try {
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

    public static boolean  validaUsuario(String email, String senha) {
        ArrayList<String[]> usuarios = lerLogin();

        for (String[] usuario : usuarios) {
            String[] dado = usuario[0].split(",");
            if ((dado[0].equals(email)) && (dado[1].equals(senha))) {
                return true;
            }
        }
        return false;

    }

    public static String[] retornaDados(String email) {
        ArrayList<String[]> usuarios = lerLogin();

        for (String[] usuario : usuarios) {
            String[] dado = usuario[0].split(",");
            if (dado[0].equals(email)) {
                return dado;
            }
        }
        return null;
    }

    public static void addLoginAluno(Aluno usuario) {
        // todo: escrever usuario no txt
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(login, true))) {
            // Junta os elementos do array com vírgula e espaço entre eles

            String[] login = { usuario.getEmail(), usuario.getSenha(), usuario.getNome(), "2", usuario.getMatricula(),
                    usuario.getCurso() };
            String linhaFormatada = String.join(",", login);
            escritor.write(linhaFormatada);
            escritor.newLine(); // Pula para a próxima linha

            System.out.println("Arquivo escrito com sucesso!");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    public static void addLoginProfessor(Professor usuario) {
        // todo: escrever usuario no txt
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(login, true))) {
            // Junta os elementos do array com vírgula e espaço entre eles

            String[] login = { usuario.getEmail(), usuario.getSenha(), usuario.getNome(), "1",
                    usuario.getDepartamento() };
            String linhaFormatada = String.join(",", login);
            escritor.write(linhaFormatada);
            escritor.newLine(); // Pula para a próxima linha

            System.out.println("Arquivo escrito com sucesso!");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    public static void addLoginBibliotecario(Bibliotecario usuario) {
        // todo: escrever usuario no txt
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(login, true))) {
            // Junta os elementos do array com vírgula e espaço entre eles

            String[] login = {usuario.getEmail(),usuario.getSenha(),usuario.getNome(),"3",usuario.getTelefone(), String.valueOf(usuario.getQntDevolucoes())};
            String linhaFormatada = String.join(",", login);
            System.out.println(linhaFormatada);
            escritor.write(linhaFormatada);
            escritor.newLine(); // Pula para a próxima linha

            System.out.println("Arquivo escrito com sucesso!");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    public static void addEmprestimoHist(Emprestimos emprestimo) {

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(historicoEmprestimo, true))) {

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = emprestimo.getData();
            String dataFormatada = data.format(formato);

            String[] historico = { emprestimo.getId(), emprestimo.getTitulo(), dataFormatada,
                    emprestimo.getEmail(), emprestimo.getStatus() };
            String linhaFormatada = String.join(",", historico);
            escritor.write(linhaFormatada);
            escritor.newLine();

            System.out.println("Arquivo escrito com sucesso!");

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

    public static void addEmprestDados(Emprestimos emprestimo){
        //email,2,livro(nome)-01/01/2025,livro-02/01/2025

        List<String> linhasAtualizadas = new ArrayList<>();
        boolean usuarioEncontrado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(dados))) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados[0].equals(emprestimo.getEmail())) {
                    usuarioEncontrado = true;

                    int quantidadeLivros = Integer.parseInt(dados[1]) + 1;
                    dados[1] = String.valueOf(quantidadeLivros);

                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String novoLivro = emprestimo.getTitulo() + "-" + LocalDate.now().format(formato);
                    List<String> novaLinha = new ArrayList<>(Arrays.asList(dados));
                    novaLinha.add(novoLivro);

                    linhasAtualizadas.add(String.join(",", novaLinha));
                } else {
                    linhasAtualizadas.add(linha);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
            return;
        }

        if (!usuarioEncontrado) {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataDevolucao = LocalDate.now().format(formato);
            String novaLinha = emprestimo.getEmail() + ",1," + emprestimo.getTitulo() + "-" + dataDevolucao;
            linhasAtualizadas.add(novaLinha);
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(dados))) {
            for (String linha : linhasAtualizadas) {
                escritor.write(linha);
                escritor.newLine();
            }
            System.out.println("Empréstimo registrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo");
        }
    }

    public static void atualizaStatus(String email, String nomeLivro){
    List<String> linhaAtualizada = new ArrayList<>();
    boolean usuarioEncontrado = false;

    try (BufferedReader leitor = new BufferedReader(new FileReader(historicoEmprestimo))) {
        String linha;

        while ((linha = leitor.readLine()) != null) {
            String[] dados = linha.split(",");

            if (dados[3].equals(email) && dados[1].equals(nomeLivro)) {
                usuarioEncontrado = true;

                dados[4] = "Entregue";

                linhaAtualizada.add(String.join(",", dados));
            } else {
                linhaAtualizada.add(linha);
            }
        }


        if (!usuarioEncontrado) {
            System.out.println("Empréstimo não encontrado para o usuário e livro informados.");
            return;
        }

    } catch (IOException e) {
        System.out.println("Erro ao ler o arquivo");
        return;
    }


    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(historicoEmprestimo))) {
        for (String linha : linhaAtualizada) {
            escritor.write(linha);
            escritor.newLine();
        }
        System.out.println("Status do empréstimo atualizado para 'entregue'!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo");
        }
    }

    public static void registrarDevolucao(String email, String nomeLivro){

    ArrayList<String> linhasAtualizadas = new ArrayList<>();

    try (BufferedReader leitor = new BufferedReader(new FileReader(dados))) {
        String linha;

        while ((linha = leitor.readLine()) != null) {
            String[] dados = linha.split(",");

            if (dados[0].equals(email)) {

                String[] livros = Arrays.copyOfRange(dados, 2, dados.length);

                boolean livroEncontrado = false;

                ArrayList<String> livrosAtualizados = new ArrayList<>();

                for (String livro : livros) {
                    if (livro.equals(nomeLivro)) {
                        livroEncontrado = true;
                    } else {
                        livrosAtualizados.add(livro);
                    }
                }


                if (livroEncontrado) {

                    int quantidadeLivros = livrosAtualizados.size();
                    dados[1] = String.valueOf(quantidadeLivros);

                    dados[2] = String.join(",", livrosAtualizados);

                    if (quantidadeLivros == 0) {
                        dados[1] = "0";
                        dados[2] = "";
                    }
                }else{
                    System.out.println("Livro não encontrado");
                }
            }

            linhasAtualizadas.add(String.join(",", dados));
        }

    } catch (IOException e) {
        System.out.println("Erro ao ler o arquivo");
        return;
    }

    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(dados))) {
        for (String linha : linhasAtualizadas) {
            escritor.write(linha);
            escritor.newLine();
        }
        System.out.println("Devolução realizada com sucesso!");
    } catch (IOException e) {
        System.out.println("Erro ao atualizar o arquivo");
    }
}

    public static boolean limiteEmprestimos(String email, int limiteEmprestimo) {
        // dados
        ArrayList<String[]> dados = lerDados();

        for (String[] dado : dados) {
            if (dado[0].equals(email)) {
                if (Integer.parseInt(dado[1]) <= limiteEmprestimo) {
                    return true;
                }

            }
        }

        return false;
    }

}
