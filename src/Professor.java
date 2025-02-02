import java.util.Scanner;

public class Professor extends Usuario {

    int limiteEmprestimo; 
    String departamento; 

    public Professor(String nome, String email, String senha, String departamento) {
        super(nome, email, senha);
        this.limiteEmprestimo = 10; 
        this.departamento = departamento; 
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }
    
    
}
