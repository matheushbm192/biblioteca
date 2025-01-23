public class Aluno extends Usuario {
    String matricula;
    String curso;

    public Aluno(String nome, String email, String senha, String matricula, String curso) {
        super(nome, email, senha);
        this.matricula = matricula;
        this.curso = curso;
    }
}
