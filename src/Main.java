public class Main {
    public static void main(String[] args) {
        while(true){
        Biblioteca.gerarUsuariosBloqueados();
        Usuario usuarioVez = TelaInicio.login();

            usuarioVez.menu();
        }


    }
}