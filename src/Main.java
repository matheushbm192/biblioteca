public class Main {
    public static void main(String[] args) {
        Biblioteca.gerarUsuariosBloqueados();
        Usuario usuarioVez = TelaInicio.login();

        if(usuarioVez instanceof Professor){
            System.out.println("Professor");
        }
        if(usuarioVez instanceof Aluno){
            System.out.println("Aluno");
        }
        if(usuarioVez instanceof Bibliotecario){
            System.out.println("Bibliotecário");
        }
        
            usuarioVez.menu();
        }


    }
