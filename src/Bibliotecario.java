public class Bibliotecario extends Usuario {
    String telefone;
    int qntDevolucoes;

    public Bibliotecario(String nome, String email, String senha, String telefone) {
        super(nome, email, senha);
        this.telefone = telefone;
    }

    //registra usuarios
    //registra devoluçoes
}
