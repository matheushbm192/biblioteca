import java.time.LocalDate;

//declaração de variáveis 
//classe filha de Obra 
public class Emprestimos extends Obra{
    private String email;
    private LocalDate data;
    private String status;

    //construtor 
    public Emprestimos(String id, String titulo,String email,int quantidade) {
        super(id, titulo, quantidade);
        this.data = LocalDate.now();
        this.email = email;
        this.status = "Emprestado";
    }
    //construtor reduzido
    public Emprestimos(String id, String titulo, LocalDate data, String email,int quantidade,String status) {
        super(id, titulo, quantidade);
        this.data = data;
        this.email = email;
        this.status = status;
    }

    //métodos get e set 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
}
