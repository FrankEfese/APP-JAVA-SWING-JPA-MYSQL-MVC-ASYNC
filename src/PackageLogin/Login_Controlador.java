package PackageLogin;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Login_Controlador {

    // MODELO-LOGIN
    private final Login_JPAC_Modelo modeloLogin = new Login_JPAC_Modelo();

    // CONSTRUCTOR
    public Login_Controlador() {
    }

    // METODO PARA OBTENER TODOS LOS LOGIN (CONTROLADOR)
    public CompletableFuture<List<Login_Object>> obtenerTodosLogin_C() {
        return CompletableFuture.supplyAsync(() -> this.modeloLogin.findLogin_ObjectEntities());
    }

    // METODO PARA COMPROBAR QUE EL USUARIO EXISTE (CONTROLADOR)
    public CompletableFuture<Boolean> comprobarUsuario_C(String correo, String contraseña) {
                
        return obtenerTodosLogin_C().thenApply(logins -> {
            for (Login_Object aux : logins) {
                if (aux.getCorreo().equals(correo) && aux.getContraseña().equals(contraseña)) {
                    return true;
                }
            }
            return false;
        }).exceptionally(ex -> {
            return false;          
        });
        
    }

}
