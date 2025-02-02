public class Aluno extends Usuario {
    String matricula;
    String curso;
    int limiteEmprestimo; 

    public Aluno(String nome, String email, String senha, String matricula, String curso) {
        super(nome, email, senha);
        this.matricula = matricula;
        this.curso = curso;
        this.limiteEmprestimo = 2; 
    }

    public Aluno(String nome, String email, String senha, String matricula){
        super(nome, email, senha);
        this.matricula = matricula;
        this.limiteEmprestimo = 2; 
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }
}
