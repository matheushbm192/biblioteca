public class Obra {
    //declaração de variáveis 
    protected String id;
    protected String titulo;
    protected int quantidade;

    //construtor
    public Obra(String id, String titulo, int quantidade) {
        this.id = id;
        this.titulo = titulo;
        this.quantidade = quantidade;
    }

    //métodos get
    public  String  getTitulo() {
        return this.titulo;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public int acrescentarQuantidade() {
        this.quantidade += 1;
        return this.quantidade;
    }

    public String getId() {
        return this.id;
    }

}
