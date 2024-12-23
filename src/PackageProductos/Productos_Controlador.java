package PackageProductos;

import PackageExceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Productos_Controlador {

    //OBJETO MODELO DE PRODUCTOS
    private final Productos_JPAC_Modelo modeloProducto = new Productos_JPAC_Modelo();

    //CONSTRUCTOR
    public Productos_Controlador() {
    }

    //METODO PARA OBTENER TODOS LOS PRODUCTOS (CONTROLADOR)
    public List<Productos_Object> obtenerTodosProductos_C() {
        return modeloProducto.findProductos_ObjectEntities();
    }

    //METODO PARA GUARDAR EL PRODUCTO (CONTROLADOR)
    public void guardarProducto_C(Productos_Object producto) {
        modeloProducto.create(producto);
    }

    //METODO PARA ELIMINAR UN PRODUCTO (CONTROLADOR)
    public void eliminarProducto_C(int idProducto) {
        try {
            modeloProducto.destroy(idProducto);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Productos_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //METODO PARA ACTUALIZAR EL PRODUCTO (CONTROLADOR)
    public void actualizarProducto_C(Productos_Object producto) {
        try {
            modeloProducto.edit(producto);
        } catch (Exception ex) {
            Logger.getLogger(Productos_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //METODO PARA OBTENER UN PRODUCTO (CONTROLADOR)
    public Productos_Object obtenerProducto_C(int idProducto) {
        return modeloProducto.findProductos_Object(idProducto);
    }
    
    
    //METODO PARA OBTENER EL TOTAL DE PRODUCTOS (CONTROLADOR)
    public int totalProductos(){
        return modeloProducto.getProductos_ObjectCount();
    }

    //METODO PARA COMPROBAR LOS CAMPOS DEL IDENTIFICADOR , NOMBRE , CATEGORIA 
    public boolean comprobarCamposProducto_C(String identificador, String nombre, String categoria) {
        return (!identificador.isEmpty() && identificador.length() < 26) && (!nombre.isEmpty() && nombre.length() < 26)
                && (!categoria.equals("--- SELECCIONAR ---"));
    }

    //METODO PARA COMPROBAR LA EXISTENCIA DEL IDENTIFICADOR
    public boolean identificadorExistente(String identificador) {
        boolean existe = false;
        for (Productos_Object aux : obtenerTodosProductos_C()) {
            if (aux.getIdentificador().equals(identificador)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    //METODO PARA OBTENER INDICE SELECCIONADO DEL COMBO
    public int obtenerIndiceCombo(String categoria) {
        List<String> categorias = new ArrayList<>(Arrays.asList("-- SELECCIONAR --", "ALIMENTACION", "ROPA", "DEPORTES", "VIDEOJUEGOS" , "COSAS VARIAS"));
        return categorias.indexOf(categoria);
    }

}
