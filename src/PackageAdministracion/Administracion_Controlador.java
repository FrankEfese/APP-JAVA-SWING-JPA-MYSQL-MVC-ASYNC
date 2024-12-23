package PackageAdministracion;

import PackageLogin.Login_JPAC_Modelo;
import PackageLogin.Login_Object;
import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Administracion_Controlador {

    //OBJETO MODELO DE ADMINISTRACION
    private final Login_JPAC_Modelo modeloAdmin = new Login_JPAC_Modelo();

    //CONSTRUCTOR
    public Administracion_Controlador() {
    }
    
    
    //METODO PARA OBTENER TODOS LOS ADMIN (CONTROLADOR)
    public List<Login_Object> obtenerTodosLogin_C() {
        return modeloAdmin.findLogin_ObjectEntities();
    }
    
    
    //METODO PARA OBTENER UN ADMIN (CONTROLADOR)
    public Login_Object obtenerAdmin_C(int idAdmin){
        return modeloAdmin.findLogin_Object(idAdmin);
    }
    
    
    //METODO PARA GUARDAR EL ADMIN (CONTROLADOR)
    public void guardarAdmin_C(Login_Object admin){
        modeloAdmin.create(admin);
    }
    
    
    //METODO PARA ACTUALIZAR EL ADMIN (CONTROLADOR)
    public void actualizarAdmin_C(Login_Object admin){
        try {
            modeloAdmin.edit(admin);
        } catch (Exception ex) {
            Logger.getLogger(Administracion_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //METODO PARA ELIMINAR UN ADMIN (CONTROLADOR)
    public void eliminarAdmin_C(int idAdmin){
        try {
            modeloAdmin.destroy(idAdmin);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Administracion_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //METODO PARA OBTENER EL TOTAL DE ADMINS (CONTROLADOR)
    public int totalAdmin(){
        return modeloAdmin.getLogin_ObjectCount();
    }

    //METODO PARA COMPROBAR LA EXISTENCIA DEL CORREO DEL ADMIN
    public boolean correoExistente(String correo) {
        boolean existe = false;
        for (Login_Object aux : obtenerTodosLogin_C()) {
            if (aux.getCorreo().equals(correo)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    //METODO PARA COMPROBAR LOS CAMPOS DEL CORREO Y CONTRASEÑA 
    public boolean comprobarCamposAdmin_C(String correo, String contra) {
        return (!correo.isEmpty() && correo.length() < 26) && (!contra.isEmpty() && correo.length() < 26);
    }

}
