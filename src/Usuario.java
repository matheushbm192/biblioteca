import java.util.Scanner;

public abstract class  Usuario {
    //declaração de variáveis 
    private String nome;
    private String email;
    private String senha;
    private int limiteEmprestimo;

    //construtor 
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    //métodos get
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
    //função que cada usuário irá sobrescrever
    public void menu(){

    }
    //função que cada usuário irá sobrescrever
    public void salvarDados(){

    }

}
