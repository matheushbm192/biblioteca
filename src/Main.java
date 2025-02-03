public class Main {
    public static void main(String[] args) {
        //atualiza lista de usuários bloqueados 
        Biblioteca.gerarUsuariosBloqueados();
        //chama função login e armazena usuário retornado em usuarioVez
        Usuario usuarioVez = TelaInicio.login();
            //chama menu de acordo com o usuário retornado
            usuarioVez.menu();
    }


}
