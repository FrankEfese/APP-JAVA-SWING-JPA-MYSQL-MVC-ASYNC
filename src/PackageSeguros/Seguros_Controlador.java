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
    public void guardarSeguro_C(Seguros_Object seguro) {
        CompletableFuture.runAsync(() -> this.modeloSeguros.create(seguro));
    }

    // METODO PARA OBTENER UN SEGURO (CONTROLADOR)
    public CompletableFuture<Seguros_Object> obtenerSeguro_C(int idSeguro) {
        return CompletableFuture.supplyAsync(() -> this.modeloSeguros.findSeguros_Object(idSeguro));
    }

    // METODO PARA ACTUALIZAR EL SEGURO (CONTROLADOR)
    public void actualizarSeguro_C(Seguros_Object seguro) {
        CompletableFuture.runAsync(() -> {
            try {
                this.modeloSeguros.edit(seguro);
            } catch (Exception ex) {
                Logger.getLogger(Seguros_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // METODO PARA ELIMINAR UN SEGURO (CONTROLADOR)
    public void eliminarSeguro_C(int idSeguro) {
        CompletableFuture.runAsync(() -> {
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
    public int obtenerFilaTabla(Seguros_Object seguro) {
        int indice = -1;
        if (seguro != null) {
            List<Seguros_Object> lista = obtenerTodosSeguros_C();
            for (Seguros_Object aux : lista) {
                if (aux.getId_seguro() == seguro.getId_seguro()) {
                    indice = lista.indexOf(aux);
                    break;
                }
            }
            return indice;
        } else {
            return indice;
        }

    }

}
