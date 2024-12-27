package PackageAdministracion;

import PackageLogin.Login_JPAC_Modelo;
import PackageLogin.Login_Object;
import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Administracion_Controlador {

    // MODELO-ADMINISTRACION
    private final Login_JPAC_Modelo modeloAdmin = new Login_JPAC_Modelo();

    // CONSTRUCTOR
    public Administracion_Controlador() {
    }    
    
    // METODO PARA OBTENER TODOS LOS ADMIN (CONTROLADOR)
    public CompletableFuture<List<Login_Object>> obtenerTodosLogin_C() {
        return CompletableFuture.supplyAsync(() -> this.modeloAdmin.findLogin_ObjectEntities());
    }    
    
    // METODO PARA OBTENER UN ADMIN (CONTROLADOR)
    public CompletableFuture<Login_Object> obtenerAdmin_C(int idAdmin){
        return CompletableFuture.supplyAsync(() -> this.modeloAdmin.findLogin_Object(idAdmin));
    }  
    
    // METODO PARA GUARDAR EL ADMIN (CONTROLADOR)
    public void guardarAdmin_C(Login_Object admin){
        CompletableFuture.runAsync(() -> this.modeloAdmin.create(admin));
    }   
    
    // METODO PARA ACTUALIZAR EL ADMIN (CONTROLADOR)
    public void actualizarAdmin_C(Login_Object admin){
        CompletableFuture.runAsync(() -> {
            try {
                this.modeloAdmin.edit(admin);
            } catch (Exception ex) {
                Logger.getLogger(Administracion_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }   
    
    // METODO PARA ELIMINAR UN ADMIN (CONTROLADOR)
    public void eliminarAdmin_C(int idAdmin){
        CompletableFuture.runAsync(() -> {
            try {
                this.modeloAdmin.destroy(idAdmin);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(Administracion_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    // METODO PARA OBTENER EL TOTAL DE ADMINS (CONTROLADOR)
    public CompletableFuture<Integer> totalAdmin(){
        return CompletableFuture.supplyAsync(() -> this.modeloAdmin.getLogin_ObjectCount());
    }

    // METODO PARA COMPROBAR LA EXISTENCIA DEL CORREO DEL ADMIN
    public CompletableFuture<Boolean> correoExistente(String correo) {
        
        return obtenerTodosLogin_C().thenApply(administradores -> {       
            for(Login_Object aux : administradores){
                if(aux.getCorreo().equals(correo)){
                    return true;
                }
            }
            return false;
        }).exceptionally(ex -> {
            return false;
        });
    }

}
