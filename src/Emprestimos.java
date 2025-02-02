import java.time.LocalDate;

public class Emprestimos extends Obra{
    private String email;
    private LocalDate data;
    private String status;

    public Emprestimos(String id, String titulo,String email,int quantidade) {
        super(id, titulo, quantidade);
        this.data = LocalDate.now();
        this.email = email;
        this.status = "pendente";
    }

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
