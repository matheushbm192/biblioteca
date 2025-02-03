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


    /*public static String acervo = "C:\\Users\\paola\\biblioteca\\Persistencia\\acervo.csv";
    public static  String historicoEmprestimo = "C:\\Users\\paola\\biblioteca\\Persistencia\\historicoEmprestimos.txt";
    public static  String usuarioBloqueado = "C:\\Users\\paola\\biblioteca\\Persistencia\\usariosBloqueados.txt";
    public static String dados = "C:\\Users\\paola\\biblioteca\\Persistencia\\dados.txt";
    public static String login = "C:\\Users\\paola\\biblioteca\\Persistencia\\login.txt";
    */

    //Método que realiza emprestimo de um livro
    public static <T extends Usuario> void realizarEmprestimo(T usuario) {
        // Inicialização de variáveis para armazenar informações do livro
        String id = " ";
        String titulo = " ";
        int quantidade = 0;
        Scanner entrada = new Scanner(System.in);

        // Verifica se o usuário está bloqueado devido a atrasos em devoluções
        boolean bloqueado = usuarioBloqueado(usuario.getEmail());
        if (bloqueado) {
            System.out.println(
                    "Você está com atrasos na devolução, portanto, está bloqueado para pegar novos exemplares."
            );
        } else {
            // Verifica se o limite de empréstimos do usuário foi atingido
            boolean limite = limiteEmprestimos(usuario.getEmail(), usuario.getLimiteEmprestimo());
            if (!limite) {
                System.out.println(
                        "Seu limite de empréstimos já foi alcançado. Realize devolução para pegar novos livros."
                );
            } else {
                // Inicialização de variáveis de controle para o processo de empréstimo
                boolean respostaValida = false;
                boolean disponibilidade = false;

                // Loop para garantir que o usuário forneça uma resposta válida
                while (!respostaValida) {
                    // Solicita ao usuário o método de busca do livro (por ID ou Título)
                    System.out.println("Para realizar um empréstimo através do Id, digite 1.");
                    System.out.println("Para realizar um empréstimo através do título, digite 2.");
                    int resposta = entrada.nextInt();
                    entrada.nextLine(); // Limpa o buffer do Scanner

                    // Empréstimo por ID do livro
                    if (resposta == 1) {
                        System.out.println("Informe o Id do livro: ");
                        id = entrada.nextLine(); // Recebe o ID do usuário
                        Obra obra = consultarObraId(id); // Consulta o livro pelo ID
                        titulo = obra.getTitulo(); // Obtém o título do livro
                        quantidade = obra.getQuantidade(); // Obtém a quantidade disponível
                        disponibilidade = exemplarDisponivel(obra); // Verifica se o exemplar está disponível
                        respostaValida = true; // Resposta válida para encerrar o loop

                        // Empréstimo por Título do livro
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        titulo = entrada.nextLine(); // Recebe o título do usuário
                        Obra obra = consultarObraTitulo(titulo); // Consulta o livro pelo título
                        id = obra.getId(); // Obtém o ID do livro
                        quantidade = obra.getQuantidade(); // Obtém a quantidade disponível
                        disponibilidade = exemplarDisponivel(obra); // Verifica se o exemplar está disponível
                        respostaValida = true; // Resposta válida para encerrar o loop
                    } else {
                        respostaValida = false; // Resposta inválida, o loop continuará
                    }

                    // Se não houver exemplares disponíveis, informa o usuário
                    if (!disponibilidade) {
                        System.out.println("Não há mais exemplares para realizar empréstimo.");
                    } else {
                        // Criação de um novo registro de empréstimo
                        Emprestimos emprestimo = new Emprestimos(id, titulo, usuario.getEmail(), quantidade);
                        addEmprestimoHist(emprestimo); // Adiciona o empréstimo ao histórico
                        addEmprestDados(emprestimo); // Adiciona o empréstimo aos dados ativos
                        alteraQuantAcervo(id); // Atualiza a quantidade de exemplares disponíveis no acervo
                    }
                }
            }
        }
    }


    public static void alteraQuantAcervo(String id) {

        // Criação de uma lista para armazenar as linhas atualizadas do arquivo do acervo
        List<String> linhaAtualizada = new ArrayList<>();

        // Leitura do arquivo do acervo para encontrar o livro com o ID correspondente
        try (BufferedReader buffer = new BufferedReader(new FileReader(acervo))) {
            String linha;

            // Leitura linha por linha do arquivo
            while ((linha = buffer.readLine()) != null) {
                // Divide a linha em partes separadas por vírgula (ID, título, quantidade, etc.)
                String[] dados = linha.split(",");

                // Verifica se o ID da linha atual é o mesmo do livro que será atualizado
                if (dados[0].equals(id)) {
                    // Converte a quantidade disponível de String para int
                    int quantidadeAtual = Integer.parseInt(dados[2]);

                    // Diminui 1 da quantidade de exemplares disponíveis (empréstimo realizado)
                    dados[2] = String.valueOf(quantidadeAtual - 1);
                }

                // Junta novamente os dados atualizados em uma única string e adiciona na lista
                linhaAtualizada.add(String.join(",", dados));
            }
        } catch (IOException e) {
            // Caso ocorra algum erro na leitura do arquivo, exibe uma mensagem de erro
            System.out.println("Erro ao ler o arquivo");
            return; // Encerra a execução do método em caso de erro
        }

        // Escrita das linhas atualizadas de volta no arquivo do acervo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(acervo))) {
            for (String linha : linhaAtualizada) {
                escritor.write(linha); // Escreve cada linha atualizada no arquivo
                escritor.newLine();    // Adiciona uma nova linha após cada registro
            }
        } catch (IOException e) {
            // Caso ocorra algum erro ao escrever no arquivo, exibe uma mensagem de erro
            System.out.println("Erro ao atualizar o arquivo");
        }
    }


    public static ArrayList<Obra> lerAcervo() {
        // Criação de uma lista para armazenar as obras lidas do acervo
        ArrayList<Obra> resultado = new ArrayList<>();

        // Bloco try-with-resources para garantir que o BufferedReader será fechado automaticamente
        try (BufferedReader buffer = new BufferedReader(new FileReader(acervo))) {
            String linha;

            // Leitura do arquivo linha por linha
            while ((linha = buffer.readLine()) != null) {
                // Divide a linha em partes separadas por vírgula (ID, título, quantidade)
                String[] separado = linha.split(",");

                // Verifica se a linha possui pelo menos 3 elementos (evitando erros de formato)
                if (separado.length >= 3) {
                    // Criação de um objeto 'Obra' com os dados lidos (ID, título e quantidade)
                    Obra formataObra = new Obra(separado[0], separado[1], Integer.parseInt(separado[2]));

                    // Adiciona o objeto 'Obra' à lista de resultados
                    resultado.add(formataObra);
                } else {
                    // Caso a linha esteja incompleta ou com formato inválido, exibe uma mensagem de erro
                    System.out.println("Erro ao processar linha: " + linha);
                }
            }
        } catch (IOException e) {
            // Caso ocorra algum erro na leitura do arquivo, imprime a pilha de erros para depuração
            e.printStackTrace();
        }

        // Retorna a lista de obras lidas do arquivo
        return resultado;
    }


    // Método para ler os usuários bloqueados a partir de um arquivo
    public static ArrayList<String[]> lerUsuariosBloqueados() {
        // Cria uma lista para armazenar os dados
        ArrayList<String[]> valores = new ArrayList<String[]>();
        try {
            // Cria um buffer para ler o arquivo de usuários bloqueados
            BufferedReader buffer = new BufferedReader(new FileReader(usuarioBloqueado));
            String linha;
            // Lê o arquivo linha por linha
            while ((linha = buffer.readLine()) != null) {
                // Divide cada linha pela quebra de linha e adiciona na lista
                valores.add(linha.split("\n"));
            }

        } catch (IOException e) {
            // Caso ocorra erro na leitura, imprime o erro
            e.printStackTrace();
        }
        // Retorna a lista de usuários bloqueados
        return valores;
    }

    // Método que retorna uma lista de obras consultadas
    public static ArrayList<Obra> consultarObras() {
        // Retorna o resultado da leitura do acervo
        return lerAcervo();
    }

    // Método para imprimir o resultado de uma consulta de obra
    public static void imprimirResultadoConsulta(Obra obra) {
        if (obra == null) {
            // Caso a obra não seja encontrada, exibe a mensagem
            System.out.println("Obra não encontrada.");
        }
        // Exibe os detalhes da obra
        System.out.println("Resultado da pesquisa: ");
        System.out.println("ID: " + obra.getId());
        System.out.println("Título: " + obra.getTitulo());
        System.out.println("Quantidade: " + obra.getQuantidade());
    }

    // Método que consulta uma obra por ID
    public static Obra consultarObraId(String id) {
        // Itera sobre as obras do acervo
        for (Obra obra : lerAcervo()) {
            // Se o ID da obra for igual ao passado como parâmetro, retorna a obra
            if (obra.getId().trim().equals(id.trim())) {
                return obra;
            }
        }
        // Se não encontrar a obra, retorna null
        return null;
    }

    // Método que consulta uma obra por título
    public static Obra consultarObraTitulo(String nomeObra) {
        // Itera sobre as obras do acervo
        for (Obra obra : lerAcervo()) {
            // Se o título da obra for igual ao título passado como parâmetro, retorna a obra
            if (obra.getTitulo().equals(nomeObra)) {
                return obra;
            }
        }
        // Se não encontrar a obra, retorna null
        return null;
    }

    // Método que verifica se um exemplar de uma obra está disponível para empréstimo
    public static boolean exemplarDisponivel(Obra obra) {
        // Verifica se a quantidade de exemplares da obra é maior que 0
        return obra.getQuantidade() > 0;
    }

    // Método que lê o histórico de empréstimos a partir de um arquivo
    public static ArrayList<Emprestimos> lerHistoricoEmprestimos() {
        // Cria uma lista para armazenar os dados
        ArrayList<String[]> valores = new ArrayList<>();
        ArrayList<Emprestimos> resultado = new ArrayList<>();

        try {
            // Cria um buffer para ler o arquivo de histórico de empréstimos
            BufferedReader buffer = new BufferedReader(new FileReader(historicoEmprestimo));
            String linha;
            // Lê o arquivo linha por linha
            while ((linha = buffer.readLine()) != null) {
                // Divide a linha por quebras de linha e adiciona na lista de valores
                valores.add(linha.split("\n"));
            }
            // Converte os dados lidos em objetos do tipo Emprestimos
            for (String[] valor : valores) {
                Emprestimos formataEmprestimo = new Emprestimos(valor[0], valor[1], LocalDate.parse(valor[2]), valor[3],
                        Integer.parseInt(valor[4]), valor[5]);
                // Adiciona o empréstimo na lista de resultados
                resultado.add(formataEmprestimo);
            }
        } catch (IOException e) {
            // Caso ocorra erro na leitura, imprime o erro
            e.printStackTrace();
        }
        // Retorna o histórico de empréstimos
        return resultado;
    }
    // Método que lê dados gerais de um arquivo
    public static ArrayList<String[]> lerDados() {
        ArrayList<String[]> valores = new ArrayList<>();
        try {
            // Cria um buffer para ler o arquivo de dados
            BufferedReader buffer = new BufferedReader(new FileReader(dados));
            String linha;
            // Lê o arquivo linha por linha
            while ((linha = buffer.readLine()) != null) {
                // Divide a linha por quebras de linha e adiciona na lista de valores
                valores.add(linha.split("\n"));
            }

        } catch (IOException e) {
            // Caso ocorra erro na leitura, imprime o erro
            e.printStackTrace();
        }
        // Retorna os dados lidos
        return valores;
    }

    // Método que gera a lista de usuários bloqueados com base nos dados de empréstimos atrasados
    public static void gerarUsuariosBloqueados() {
        ArrayList<String[]> linhas = lerDados();
        ArrayList<String[]> atrasados = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Itera sobre cada linha de dados
        for (String[] linha : linhas) {
            String[] valores = linha[0].split(",");

            // Itera sobre os livros emprestados
            for (int i = 2; i < Integer.parseInt(valores[1]) + 2; i++) {
                String[] resultado = valores[i].split("-");
                long diasDiferenca = ChronoUnit.DAYS.between(hoje, LocalDate.parse(resultado[1], formatter));

                // Se o livro está atrasado por mais de 7 dias, adiciona o usuário na lista de bloqueados
                if ((diasDiferenca * -1) > 7) {
                    String[] usuarioAtrasado = { valores[0], resultado[0] + "-" + resultado[1] };
                    atrasados.add(usuarioAtrasado);
                }
            }
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(usuarioBloqueado))) {
            // Escreve a lista de usuários bloqueados no arquivo
            for (String[] linha : atrasados) {
                String linhaFormatada = String.join(",", linha);
                escritor.write(linhaFormatada);
                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }


    // Método que verifica se um usuário está bloqueado
    public static boolean usuarioBloqueado(String email) {
        gerarUsuariosBloqueados();
        ArrayList<String[]> linhas = lerUsuariosBloqueados();

        // Itera sobre os usuários bloqueados
        for (String[] linha : linhas) {
            // Se o email do usuário corresponder ao registrado, retorna verdadeiro (usuário bloqueado)
            if (linha[0].equals(email)) {
                return true;
            }
        }
        // Se o usuário não estiver bloqueado, retorna falso
        return false;
    }

    // Método para ler os dados de login dos usuários a partir de um arquivo
    public static ArrayList<String[]> lerLogin() {
        // Cria uma lista para armazenar os dados de login
        ArrayList<String[]> valores = new ArrayList<>();
        try {
            // Cria um buffer para ler o arquivo de login
            BufferedReader buffer = new BufferedReader(new FileReader(login));
            String linha;
            // Lê o arquivo linha por linha
            while ((linha = buffer.readLine()) != null) {
                // Divide cada linha pela quebra de linha e adiciona na lista de valores
                valores.add(linha.split("\n"));
            }

        } catch (IOException e) {
            // Caso ocorra erro na leitura, imprime o erro
            e.printStackTrace();
        }
        // Retorna os dados lidos do arquivo
        return valores;
    }

    // Método que valida o usuário com base no email e senha
    public static boolean validaUsuario(String email, String senha) {
        // Lê os dados de login dos usuários
        ArrayList<String[]> usuarios = lerLogin();

        // Itera sobre cada usuário na lista de login
        for (String[] usuario : usuarios) {
            // Divide os dados do usuário, separando email e senha
            String[] dado = usuario[0].split(",");
            // Se o email e a senha corresponderem aos dados, retorna verdadeiro
            if ((dado[0].equals(email)) && (dado[1].equals(senha))) {
                return true;
            }
        }
        // Caso não encontre correspondência, retorna falso
        return false;
    }

    // Método que retorna os dados de um usuário com base no email
    public static String[] retornaDados(String email) {
        // Lê os dados de login dos usuários
        ArrayList<String[]> usuarios = lerLogin();

        // Itera sobre cada usuário na lista de login
        for (String[] usuario : usuarios) {
            // Divide os dados do usuário, separando o email e outras informações
            String[] dado = usuario[0].split(",");
            // Se o email corresponder ao dado, retorna o array com as informações do usuário
            if (dado[0].equals(email)) {
                return dado;
            }
        }
        // Se o email não for encontrado, retorna null
        return null;
    }

    // Método que adiciona um novo aluno no arquivo de login
    public static void addLoginAluno(Aluno usuario) {
        // Tenta escrever no arquivo de login
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(login, true))) {
            // Cria um array com os dados do aluno
            String[] login = { usuario.getEmail(), usuario.getSenha(), usuario.getNome(), "2", usuario.getMatricula(),
                    usuario.getCurso() };
            // Converte o array em uma linha de texto separada por vírgulas
            String linhaFormatada = String.join(",", login);
            // Escreve a linha no arquivo
            escritor.write(linhaFormatada);
            escritor.newLine(); // Pula para a próxima linha

        } catch (IOException e) {
            // Caso ocorra erro na escrita, imprime o erro
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }


    public static void addLoginProfessor(Professor usuario) {
        // Tenta escrever no arquivo de login
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(login, true))) {
            // Cria um array com os dados do professor
            String[] login = { usuario.getEmail(), usuario.getSenha(), usuario.getNome(), "1", usuario.getDepartamento() };
            // Converte o array em uma linha de texto separada por vírgulas
            String linhaFormatada = String.join(",", login);
            // Escreve a linha no arquivo
            escritor.write(linhaFormatada);
            escritor.newLine(); // Pula para a próxima linha

        } catch (IOException e) {
            // Caso ocorra erro na escrita, imprime o erro
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }


    public static void addLoginBibliotecario(Bibliotecario usuario) {
        // Tenta escrever no arquivo de login
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(login, true))) {
            // Cria um array com os dados do bibliotecário
            String[] login = {usuario.getEmail(), usuario.getSenha(), usuario.getNome(), "3", usuario.getTelefone(), String.valueOf(usuario.getQntDevolucoes())};
            // Converte o array em uma linha de texto separada por vírgulas
            String linhaFormatada = String.join(",", login);
            System.out.println(linhaFormatada);  // Exibe a linha formatada no console para depuração
            // Escreve a linha no arquivo
            escritor.write(linhaFormatada);
            escritor.newLine(); // Pula para a próxima linha

        } catch (IOException e) {
            // Caso ocorra erro na escrita, imprime o erro
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }


    public static void addEmprestimoHist(Emprestimos emprestimo) {
        // Tenta escrever no arquivo de histórico de empréstimos
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(historicoEmprestimo, true))) {
            // Define o formato de data como "dd/MM/yyyy"
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Obtém a data do empréstimo
            LocalDate data = emprestimo.getData();
            // Formata a data de acordo com o formato definido
            String dataFormatada = data.format(formato);

            // Cria um array com os dados do histórico de empréstimo
            String[] historico = { emprestimo.getId(), emprestimo.getTitulo(), dataFormatada,
                    emprestimo.getEmail(), emprestimo.getStatus() };
            // Converte o array em uma linha de texto separada por vírgulas
            String linhaFormatada = String.join(",", historico);
            // Escreve a linha no arquivo
            escritor.write(linhaFormatada);
            escritor.newLine(); // Pula para a próxima linha

        } catch (IOException e) {
            // Caso ocorra erro na escrita, imprime o erro
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }


    public static void addEmprestDados(Emprestimos emprestimo){
        // A ideia é atualizar o arquivo com os dados do empréstimo, ou criar um novo registro se o usuário não existir.
        // A estrutura do arquivo é: email, quantidade de livros emprestados, livro(nome)-dataDevolucao

        // Lista que vai armazenar as linhas atualizadas do arquivo
        List<String> linhasAtualizadas = new ArrayList<>();
        // Variável para verificar se o usuário foi encontrado no arquivo
        boolean usuarioEncontrado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(dados))) {
            String linha;

            // Leitura de cada linha do arquivo
            while ((linha = leitor.readLine()) != null) {
                // Separa os dados de cada linha por vírgula
                String[] dados = linha.split(",");

                // Verifica se o email do usuário corresponde ao do empréstimo
                if (dados[0].equals(emprestimo.getEmail())) {
                    usuarioEncontrado = true; // Marca que o usuário foi encontrado

                    // Atualiza a quantidade de livros emprestados (incrementa em 1)
                    int quantidadeLivros = Integer.parseInt(dados[1]) + 1;
                    dados[1] = String.valueOf(quantidadeLivros);

                    // Formata a data atual para adicionar ao título do livro
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String novoLivro = emprestimo.getTitulo() + "-" + LocalDate.now().format(formato);

                    // Cria uma nova linha com os dados do usuário e o novo livro emprestado
                    List<String> novaLinha = new ArrayList<>(Arrays.asList(dados));
                    novaLinha.add(novoLivro); // Adiciona o novo livro à lista de dados

                    // Adiciona a linha atualizada na lista de linhas
                    linhasAtualizadas.add(String.join(",", novaLinha));
                } else {
                    // Se o usuário não for encontrado, adiciona a linha original sem alterações
                    linhasAtualizadas.add(linha);
                }
            }

        } catch (IOException e) {
            // Caso ocorra erro ao ler o arquivo
            System.out.println("Erro ao ler o arquivo");
            return; // Interrompe a execução do método
        }

        // Se o usuário não foi encontrado, cria um novo registro para ele
        if (!usuarioEncontrado) {
            // Formata a data atual para a devolução do livro
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataDevolucao = LocalDate.now().format(formato);

            // Cria uma nova linha no formato: email, quantidade de livros (1), título do livro com data de devolução
            String novaLinha = emprestimo.getEmail() + ",1," + emprestimo.getTitulo() + "-" + dataDevolucao;

            // Adiciona essa nova linha à lista de linhas atualizadas
            linhasAtualizadas.add(novaLinha);
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(dados))) {
            // Escreve as linhas atualizadas de volta no arquivo
            for (String linha : linhasAtualizadas) {
                escritor.write(linha);  // Escreve a linha no arquivo
                escritor.newLine();     // Adiciona uma quebra de linha
            }
            // Imprime mensagem de sucesso se o arquivo for atualizado corretamente
            System.out.println("Empréstimo registrado com sucesso!");
        } catch (IOException e) {
            // Caso ocorra erro ao escrever no arquivo
            System.out.println("Erro ao atualizar o arquivo");
        }
    }


    public static void atualizaStatus(String email, String nomeLivro) {
        // Lista que armazenará as linhas atualizadas do arquivo de histórico de empréstimos
        List<String> linhaAtualizada = new ArrayList<>();
        // Variável para verificar se o usuário foi encontrado no arquivo
        boolean usuarioEncontrado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(historicoEmprestimo))) {
            String linha;

            // Leitura de cada linha do arquivo de histórico de empréstimos
            while ((linha = leitor.readLine()) != null) {
                // Separa os dados de cada linha por vírgula
                String[] dados = linha.split(",");

                // Verifica se o email e o nome do livro correspondem ao que foi fornecido
                if (dados[3].equals(email) && dados[1].equals(nomeLivro)) {
                    usuarioEncontrado = true; // Marca que o usuário foi encontrado

                    // Atualiza o status do empréstimo para "Entregue"
                    dados[4] = "Entregue";

                    // Adiciona a linha atualizada à lista
                    linhaAtualizada.add(String.join(",", dados));
                } else {
                    // Se não for o empréstimo correspondente, mantém a linha original
                    linhaAtualizada.add(linha);
                }
            }

            // Se o empréstimo não foi encontrado, exibe uma mensagem
            if (!usuarioEncontrado) {
                System.out.println("Empréstimo não encontrado para o usuário e livro informados.");
                return;
            }

        } catch (IOException e) {
            // Se ocorrer erro ao ler o arquivo
            System.out.println("Erro ao ler o arquivo");
            return;
        }

        // Escreve as linhas atualizadas de volta ao arquivo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(historicoEmprestimo))) {
            for (String linha : linhaAtualizada) {
                escritor.write(linha);  // Escreve a linha no arquivo
                escritor.newLine();     // Adiciona uma quebra de linha
            }
            System.out.println("Status do empréstimo atualizado para 'entregue'!");
        } catch (IOException e) {
            // Se ocorrer erro ao escrever no arquivo
            System.out.println("Erro ao atualizar o arquivo");
        }
    }


    public static void registrarDevolucao(String email, String nomeLivro) {
        // Lista que armazenará as linhas atualizadas do arquivo de dados
        ArrayList<String> linhasAtualizadas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(dados))) {
            String linha;

            // Leitura de cada linha do arquivo de dados
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",");

                // Verifica se o email do usuário corresponde ao que foi fornecido
                if (dados[0].equals(email)) {
                    // Pega todos os livros do usuário (exceto os dois primeiros dados)
                    String[] livros = Arrays.copyOfRange(dados, 2, dados.length);

                    boolean livroEncontrado = false; // Flag para verificar se o livro foi encontrado
                    ArrayList<String> livrosAtualizados = new ArrayList<>();

                    // Procura o livro dentro dos dados do usuário
                    for (String livro : livros) {
                        String tituloLivro = livro.split("-")[0]; // Extrai o nome do livro

                        // Se o nome do livro for igual ao informado, marca como encontrado
                        if (tituloLivro.equals(nomeLivro)) {
                            livroEncontrado = true;
                        } else {
                            livrosAtualizados.add(livro); // Adiciona o livro não devolvido à lista
                        }
                    }

                    // Se o livro foi encontrado, atualiza a quantidade de livros emprestados
                    if (livroEncontrado) {
                        int quantidadeLivros = livrosAtualizados.size();
                        dados[1] = String.valueOf(quantidadeLivros);

                        // Cria uma nova linha para o usuário com a quantidade de livros atualizada
                        String novaLinha = dados[0] + "," + dados[1] +
                                (quantidadeLivros > 0 ? "," + String.join(",", livrosAtualizados) : "");
                        linhasAtualizadas.add(novaLinha); // Adiciona a linha atualizada
                    } else {
                        System.out.println("Livro não encontrado");
                        linhasAtualizadas.add(linha); // Mantém a linha original caso o livro não seja encontrado
                    }
                } else {
                    linhasAtualizadas.add(linha); // Caso o email não corresponda, mantém a linha original
                }
            }
        } catch (IOException e) {
            // Se ocorrer erro ao ler o arquivo
            System.out.println("Erro ao ler o arquivo");
            return;
        }

        // Escreve as linhas atualizadas de volta ao arquivo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(dados))) {
            for (String linha : linhasAtualizadas) {
                escritor.write(linha);  // Escreve a linha no arquivo
                escritor.newLine();     // Adiciona uma quebra de linha
            }
            System.out.println("Devolução realizada com sucesso!");
        } catch (IOException e) {
            // Se ocorrer erro ao escrever no arquivo
            System.out.println("Erro ao atualizar o arquivo");
        }
    }


    public static boolean limiteEmprestimos(String email, int limiteEmprestimo) {
        // Chama o método para ler os dados dos empréstimos
        ArrayList<String[]> dados = lerDados();

        // Percorre todos os registros de empréstimos
        for (String[] dado : dados) {
            String[] linha = dado[0].split(",");

            // Verifica se o email corresponde ao fornecido
            if (linha[0].equals(email)) {
                // Se a quantidade de livros for menor que o limite, retorna true (empréstimo permitido)
                if (Integer.parseInt(linha[1]) < limiteEmprestimo) {
                    return true;
                } else {
                    // Se a quantidade de livros for maior ou igual ao limite, retorna false (empréstimo não permitido)
                    return false;
                }
            }
        }
        // Caso o email não seja encontrado, assume que o empréstimo é permitido
        return true;
    }


    public static void atualizarDadosLogin(Bibliotecario usuario) {
        // Chama o método para ler os dados de login
        ArrayList<String[]> logins = lerLogin();

        // Percorre a lista de logins e encontra o correspondente ao email do bibliotecário
        for (int i = 0; i < logins.size(); i++) {
            String[] resultado = logins.get(i)[0].split(",");

            // Verifica se o email corresponde ao do bibliotecário
            if (resultado[0].equals(usuario.getEmail())) {
                // Cria um novo array com os dados atualizados do bibliotecário
                String[] login = {usuario.getEmail(), usuario.getSenha(), usuario.getNome(), "3", usuario.getTelefone(), String.valueOf(usuario.getQntDevolucoes())};

                // Formata a nova linha
                String[] linhaFormatada = new String[1];
                linhaFormatada[0] = String.join(",", login);
                logins.set(i, linhaFormatada); // Substitui a linha original pelo novo dado
                break;
            }
        }

        // Escreve as alterações de volta ao arquivo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(login))) {
            for (String[] login : logins) {
                escritor.write(login[0]);  // Escreve a linha no arquivo
                escritor.newLine();        // Adiciona uma quebra de linha
            }
        } catch (IOException e) {
            // Se ocorrer erro ao escrever no arquivo
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }


    public static void atualizarAcervo(String nomeLivro) {
        // Chama o método para ler os dados do acervo de livros
        ArrayList<Obra> acervos = lerAcervo();

        // Percorre a lista de livros no acervo
        for (int i = 0; i < acervos.size(); i++) {
            // Se o título do livro for encontrado, incrementa a quantidade do livro
            if (acervos.get(i).getTitulo().equals(nomeLivro)) {
                Obra livro = new Obra(acervos.get(i).getId(), acervos.get(i).getTitulo(), acervos.get(i).acrescentarQuantidade());
                acervos.set(i, livro); // Atualiza a quantidade no acervo
                break;
            }
        }

        // Escreve as alterações de volta ao arquivo do acervo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(acervo))) {
            for (Obra obra : acervos) {
                // Cria uma linha com os dados do livro atualizado
                String[] linha = {obra.getId(), obra.getTitulo(), String.valueOf(obra.getQuantidade())};
                String[] linhaFormatada = new String[1];
                linhaFormatada[0] = String.join(",", linha);
                escritor.write(linhaFormatada[0]); // Escreve a linha no arquivo
                escritor.newLine(); // Adiciona uma quebra de linha
            }
        } catch (IOException e) {
            // Se ocorrer erro ao escrever no arquivo
            System.out.println("Ocorreu um erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }

}
