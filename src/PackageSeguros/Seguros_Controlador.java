package PackageSeguros;

import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Seguros_Controlador {

    // MODELO-SEGURO
    Seguros_JPAC_Modelo modeloSeguros = new Seguros_JPAC_Modelo();

    // CONSTRUCTOR
    public Seguros_Controlador() {
    }

    // METODO PARA OBTENER TODOS LOS SEGUROS (CONTROLADOR)
    public CompletableFuture<List<Seguros_Object>> obtenerTodosSeguros_C() {
        return CompletableFuture.supplyAsync(() -> this.modeloSeguros.findSeguros_ObjectEntities());
    }

    // METODO PARA GUARDAR EL SEGURO (CONTROLADOR)
    public CompletableFuture<Void> guardarSeguro_C(Seguros_Object seguro) {
        return CompletableFuture.runAsync(() -> this.modeloSeguros.create(seguro));
    }

    // METODO PARA OBTENER UN SEGURO (CONTROLADOR)
    public CompletableFuture<Seguros_Object> obtenerSeguro_C(int idSeguro) {
        return CompletableFuture.supplyAsync(() -> this.modeloSeguros.findSeguros_Object(idSeguro));
    }

    // METODO PARA ACTUALIZAR EL SEGURO (CONTROLADOR)
    public CompletableFuture<Void> actualizarSeguro_C(Seguros_Object seguro) {
        return CompletableFuture.runAsync(() -> {
            try {
                this.modeloSeguros.edit(seguro);
            } catch (Exception ex) {
                Logger.getLogger(Seguros_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // METODO PARA ELIMINAR UN SEGURO (CONTROLADOR)
    public CompletableFuture<Void> eliminarSeguro_C(int idSeguro) {
        return CompletableFuture.runAsync(() -> {
            try {
                this.modeloSeguros.destroy(idSeguro);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(Seguros_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    // METODO PARA OBTENER EL TOTAL DE SEGUROS (CONTROLADOR)
    public CompletableFuture<Integer> totalSeguros_C(){
        return CompletableFuture.supplyAsync(() -> this.modeloSeguros.getSeguros_ObjectCount());
    }    

    // METODO PARA COMPROBAR LA EXISTENCIA DEL NOMBRE DEL SEGURO
    public CompletableFuture<Boolean> nombreExistente(String nombre) {
        
        return obtenerTodosSeguros_C().thenApply(seguros -> {
            for (Seguros_Object aux : seguros) {
                if (aux.getNombre().equals(nombre)) {
                    return true;
                }
            }
            return false;
        }).exceptionally(ex -> {
            return false;          
        });
    }

    // METODO PARA OBTENER FILA SELECCIONADA DE LA TABLA
    public CompletableFuture<Integer> obtenerFilaTabla(Seguros_Object seguro) {
        if (seguro != null) {
            
            return obtenerTodosSeguros_C().thenApply(seguros -> {           
                for (Seguros_Object aux : seguros) {
                    if (aux.getId_seguro() == seguro.getId_seguro()) {
                        return seguros.indexOf(aux);
                    }
                }               
                return -1;            
            }).exceptionally(ex -> {
                return -1;
            });
            
        } else {
            return CompletableFuture.completedFuture(-1);
        }
    }

}
