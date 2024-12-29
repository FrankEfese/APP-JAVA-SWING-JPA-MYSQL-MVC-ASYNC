package PackageEmpleados;

import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Empleados_Controlador {

    // MODELO-EMPLEADOS
    private final Empleados_JPAC_Modelo modeloEmpleados = new Empleados_JPAC_Modelo();

    // CONSTRUCTOR
    public Empleados_Controlador() {
    }

    // METODO PARA OBTENER TODOS LOS EMPLEADOS (CONTROLADOR)
    public CompletableFuture<List<Empleados_Object>> obtenerTodosEmpleados_C() {
        return CompletableFuture.supplyAsync(() -> this.modeloEmpleados.findEmpleados_ObjectEntities());
    }
    
    // METODO PARA OBTENER UN EMPLEADO (CONTROLADOR)
    public CompletableFuture<Empleados_Object> obtenerEmpleado_C(int idEmpleado){
        return CompletableFuture.supplyAsync(() -> this.modeloEmpleados.findEmpleados_Object(idEmpleado));
    }
    
    // METODO PARA GUARDAR EL EMPLEADO (CONTROLADOR)
    public CompletableFuture<Void> guardarEmpleado_C(Empleados_Object empleado){
        return CompletableFuture.runAsync(() -> this.modeloEmpleados.create(empleado));
    }

    // METODO PARA ELIMINAR UN EMPLEADO (CONTROLADOR)
    public CompletableFuture<Void> eliminarEmpleado_C(int idEmpleado) {
        return CompletableFuture.runAsync(() -> {
            try {
                this.modeloEmpleados.destroy(idEmpleado);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(Empleados_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    // METODO PARA ACTUALIZAR AL EMPLEADO (CONTROLADOR)
    public CompletableFuture<Void> actualizarEmpleado_C(Empleados_Object empleado){
        return CompletableFuture.runAsync(() -> {
            try {
                this.modeloEmpleados.edit(empleado);
            } catch (Exception ex) {
                Logger.getLogger(Empleados_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    // METODO PARA OBTENER EL TOTAL DE EMPLEADOS (CONTROLADOR)
    public CompletableFuture<Integer> totalEmpleados(){
        return CompletableFuture.supplyAsync(() -> this.modeloEmpleados.getEmpleados_ObjectCount());
    }    
    
    // METODO PARA COMPROBAR LA EXISTENCIA DE DNI Y TELEFONO
    public CompletableFuture<boolean[]> verificarDniYTelefono(String dni, String telefono) {
        return obtenerTodosEmpleados_C().thenApply(empleados -> {
            boolean dniExiste = false;
            boolean telefonoExiste = false;

            for (Empleados_Object aux : empleados) {
                if (aux.getDni().equals(dni)) {
                    dniExiste = true;
                }
                if (String.valueOf(aux.getTelefono()).equals(telefono)) {
                    telefonoExiste = true;
                }

                if (dniExiste && telefonoExiste) {
                    break;
                }
            }
            return new boolean[]{dniExiste, telefonoExiste};
        }).exceptionally(ex -> {
            return new boolean[]{false, false};
        });
    }


    
}
