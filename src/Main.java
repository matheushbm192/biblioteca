public class Main {
    public static void main(String[] args) {
        Biblioteca.gerarUsuariosBloqueados();
        Usuario usuarioVez = TelaInicio.login();
        if (usuarioVez instanceof Professor){
            System.out.println("pprofessor");
        }
        if (usuarioVez instanceof Bibliotecario){
            System.out.println("Bibliotecario");
        }
        if (usuarioVez instanceof Aluno){
            System.out.println("Aluno");
        }
        while(true){
            usuarioVez.menu();
        }


    }
}