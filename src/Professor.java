import java.util.Scanner;

public class Professor extends Usuario {

   private final int limiteEmprestimo = 10;
   private String departamento;

    public Professor(String nome, String email, String senha, String departamento) {
        super(nome, email, senha);
        this.departamento = departamento; 
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }

    public String getDepartamento() {
        return departamento;
    }
}
