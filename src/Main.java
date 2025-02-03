public class Main {
    public static void main(String[] args) {
        Biblioteca.gerarUsuariosBloqueados();
        Usuario usuarioVez = TelaInicio.login();
        
            usuarioVez.menu();
        


    }
}