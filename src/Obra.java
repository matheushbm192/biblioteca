public class Obra {
    protected String id;
    protected String titulo;
    protected int quantidade;

    public Obra(String id, String titulo, int quantidade) {
        this.id = id;
        this.titulo = titulo;
        this.quantidade = quantidade;
    }

    public  String  getTitulo() {
        return this.titulo;
    }

    public int getQuantidade() {
        return this.quantidade;
    }
}
