import java.util.Scanner;

public abstract class  Usuario {
    private String nome;
    private String email;
    private String senha;
    private int limiteEmprestimo;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }


    public String getSenha() {
        return senha;
    }
    public int getLimiteEmprestimo() {
        return this.limiteEmprestimo;
    }
    public void menu(){

    }

    public void salvarDados(){

    }

}
