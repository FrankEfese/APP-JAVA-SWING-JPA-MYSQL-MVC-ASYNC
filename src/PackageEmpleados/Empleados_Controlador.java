package PackageEmpleados;

import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Empleados_Controlador {

    //OBJETO MODELO DE EMPLEADOS
    private final Empleados_JPAC_Modelo modeloEmpleados = new Empleados_JPAC_Modelo();

    //CONSTRUCTOR
    public Empleados_Controlador() {
    }

    //METODO PARA OBTENER TODOS LOS EMPLEADOS (CONTROLADOR)
    public List<Empleados_Object> obtenerTodosEmpleados_C() {
        return modeloEmpleados.findEmpleados_ObjectEntities();
    }
    
    //METODO PARA OBTENER UN EMPLEADO (CONTROLADOR)
    public Empleados_Object obtenerEmpleado_C(int idEmpleado){
        return modeloEmpleados.findEmpleados_Object(idEmpleado);
    }
    
    //METODO PARA GUARDAR EL EMPLEADO (CONTROLADOR)
    public void guardarEmpleado_C(Empleados_Object empleado){
        modeloEmpleados.create(empleado);
    }

    //METODO PARA ELIMINAR UN EMPLEADO (CONTROLADOR)
    public void eliminarEmpleado_C(int idEmpleado) {
        try {
            modeloEmpleados.destroy(idEmpleado);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Empleados_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //METODO PARA ACTUALIZAR AL EMPLEADO (CONTROLADOR)
    public void actualizarEmpleado_C(Empleados_Object empleado){
        try {
            modeloEmpleados.edit(empleado);
        } catch (Exception ex) {
            Logger.getLogger(Empleados_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    //METODO PARA OBTENER EL TOTAL DE EMPLEADOS (CONTROLADOR)
    public int totalEmpleados(){
        return modeloEmpleados.getEmpleados_ObjectCount();
    }

    //METODO PARA COMPROBAR LA EXISTENCIA DEL DNI
    public boolean dniExistente(String dni) {
        boolean existe = false;
        for (Empleados_Object aux : obtenerTodosEmpleados_C()) {
            if (aux.getDni().equals(dni)) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    
    //METODO PARA COMPROBAR LA EXISTENCIA DEL TELEFONO
    public boolean telefonoExistente(String telefono) {
        boolean existe = false;
        for (Empleados_Object aux : obtenerTodosEmpleados_C()) {
            if (aux.getTelefono() == Integer.parseInt(telefono)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    //METODO PARA COMPROBAR LOS CAMPOS DEL DNI , NOMBRE , TELEFONO 
    public boolean comprobarCamposEmpleado_C(String dni, String nombre, String telefono) {
        return (!nombre.isEmpty() && nombre.length() < 26) && (comprobarDNI(dni))
                && (comprobarTelefono(telefono));
    }

    
    //METODO PARA COMPROBAR EL TELEFONO
    public boolean comprobarTelefono(String telefono) {
        if (telefono.isEmpty() || telefono.length() != 9) {
            return false;
        }
        for (int x = 0; x < telefono.length(); x++) {
            char c = telefono.charAt(x);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    
    //METODO PARA COMPROBAR EL DNI
    public boolean comprobarDNI(String dni) {
        if (dni == null || dni.length() != 9) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            char c = dni.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        char letra = dni.charAt(8);
        return Character.isLetter(letra);
    }

    
}
