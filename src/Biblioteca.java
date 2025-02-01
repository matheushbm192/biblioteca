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
    public static void menu(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Realizar Empréstimo");
        System.out.println("2- Consultar Informações da obra");
        System.out.println("3 - Sair");

        int escolha = entrada.nextInt();

        switch (escolha) {
            case 1:
                realizarEmprestimo();
                break;
            case 2:

                boolean respostaValida = false;
                while (respostaValida = false) {

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
                menu();
        }
    }

    public static void sair() {
        // atualizarInformacoes();
        TelaInicio.inicio();
    }

    // passar id do usuario
    // alterar arquivo acervo;
    public static void realizarEmprestimo() {

        Scanner entrada = new Scanner(System.in);
        boolean bloqueado = usuarioBloqueado();

        if (bloqueado = true) {
            System.out.println(
                    "Você está com atrasos na devolução, portanto, está bloqueado para pegar novos exemplares.");
        } else {

            boolean limite = limiteEmprestimos();
            if (limite = true) {
                System.out.println(
                        "Seu limite de empréstimos já foi alcançado. Realize devolução para pegar novos livros.");
            } else {

                boolean respostaValida = false;
                boolean disponibilidade = false;
                while (respostaValida = false) {

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

                    if (disponibilidade = false) {
                        System.out.println("Não há mais exemplares para realizar empréstimo.");
                    } else {
                        // realizar emprestimo;
                        atualizarInformacoes();
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

    // passar como parametro a obra (id ou nome)
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

    public ArrayList<Emprestimos> lerHistoricoEmprestimos() {
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

    public static boolean usuarioBloqueado() {
        
    }

    public static void atualizarInformacoes() {
        // não entendi esse!
    }

    public static boolean limiteEmprestimos() {
        // verifica qual é o tipo de usuario e se seu limite de emprestimos foi
        // alcançado
        // ( professor - 10; aluno - 2; )
        return false;
    }

}
