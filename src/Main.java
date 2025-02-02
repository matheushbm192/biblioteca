
public class Main {
    public static void main(String[] args) {
        Biblioteca.gerarUsuariosBloqueados();
        Object usuarioVez = TelaInicio.login();
        if((usuarioVez instanceof Aluno) || (usuarioVez instanceof Professor)){
            Biblioteca.menu(usuarioVez);
        }else{
            Bibliotecario.menu(usuarioVez);
        }
    }
}