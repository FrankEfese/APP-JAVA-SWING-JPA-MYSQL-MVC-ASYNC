package PackageProductos;

import PackageExceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Productos_Controlador {

    // MODELO-PRODUCTOS
    private final Productos_JPAC_Modelo modeloProducto = new Productos_JPAC_Modelo();

    // CONSTRUCTOR
    public Productos_Controlador() {
    }

    // METODO PARA OBTENER TODOS LOS PRODUCTOS (CONTROLADOR)
    public CompletableFuture<List<Productos_Object>> obtenerTodosProductos_C() {
        return CompletableFuture.supplyAsync(() -> this.modeloProducto.findProductos_ObjectEntities());
    }

    // METODO PARA GUARDAR EL PRODUCTO (CONTROLADOR)
    public void guardarProducto_C(Productos_Object producto) {
        CompletableFuture.runAsync(() -> this.modeloProducto.create(producto));
    }

    // METODO PARA ELIMINAR UN PRODUCTO (CONTROLADOR)
    public void eliminarProducto_C(int idProducto) {
        CompletableFuture.runAsync(() -> {
            try {
                this.modeloProducto.destroy(idProducto);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(Productos_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // METODO PARA ACTUALIZAR EL PRODUCTO (CONTROLADOR)
    public void actualizarProducto_C(Productos_Object producto) {
        CompletableFuture.runAsync(() -> {
            try {
                this.modeloProducto.edit(producto);
            } catch (Exception ex) {
                Logger.getLogger(Productos_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // METODO PARA OBTENER UN PRODUCTO (CONTROLADOR)
    public CompletableFuture<Productos_Object> obtenerProducto_C(int idProducto) {
        return CompletableFuture.supplyAsync(() -> this.modeloProducto.findProductos_Object(idProducto));
    }
    
    
    // METODO PARA OBTENER EL TOTAL DE PRODUCTOS (CONTROLADOR)
    public CompletableFuture<Integer> totalProductos(){
        return CompletableFuture.supplyAsync(() -> this.modeloProducto.getProductos_ObjectCount());
    }

    // METODO PARA COMPROBAR LA EXISTENCIA DEL IDENTIFICADOR
    public CompletableFuture<Boolean> identificadorExistente(String identificador) {
        
        return obtenerTodosProductos_C().thenApply(productos -> {      
            for(Productos_Object aux : productos){
                if(aux.getIdentificador().equals(identificador)){
                    return true;
                }
            }
            return false;            
        }).exceptionally(ex -> {
            return false;
        });
        
    }

    // METODO PARA OBTENER INDICE SELECCIONADO DEL COMBO
    public int obtenerIndiceCombo(String categoria) {
        List<String> categorias = new ArrayList<>(Arrays.asList("-- SELECCIONAR --", "ALIMENTACION", "ROPA", "DEPORTES", "VIDEOJUEGOS" , "COSAS VARIAS"));
        return categorias.indexOf(categoria);
    }

}
