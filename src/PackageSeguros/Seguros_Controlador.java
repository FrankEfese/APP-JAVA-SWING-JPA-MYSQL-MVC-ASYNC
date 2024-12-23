package PackageSeguros;


import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Seguros_Controlador {

    //OBJETO DEL MODELO DEL SEGURO
    Seguros_JPAC_Modelo modeloSeguros = new Seguros_JPAC_Modelo();

    //CONSTRUCTOR
    public Seguros_Controlador() {
    }

    //METODO PARA OBTENER TODOS LOS SEGUROS (CONTROLADOR)
    public List<Seguros_Object> obtenerTodosSeguros_C() {
        return modeloSeguros.findSeguros_ObjectEntities();
    }

    //METODO PARA GUARDAR EL SEGURO (CONTROLADOR)
    public void guardarSeguro_C(Seguros_Object seguro) {
        modeloSeguros.create(seguro);
    }

    //METODO PARA OBTENER UN SEGURO (CONTROLADOR)
    public Seguros_Object obtenerSeguro_C(int idSeguro) {
        return modeloSeguros.findSeguros_Object(idSeguro);
    }

    //METODO PARA ACTUALIZAR EL SEGURO (CONTROLADOR)
    public void actualizarSeguro_C(Seguros_Object seguro) {
        try {
            modeloSeguros.edit(seguro);
        } catch (Exception ex) {
            Logger.getLogger(Seguros_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //METODO PARA ELIMINAR UN SEGURO (CONTROLADOR)
    public void eliminarSeguro_C(int idSeguro) {
        try {
            modeloSeguros.destroy(idSeguro);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Seguros_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //METODO PARA OBTENER EL TOTAL DE SEGUROS (CONTROLADOR)
    public int totalSeguros_C(){
        return modeloSeguros.getSeguros_ObjectCount();
    }
    

    //METODO PARA COMPROBAR LA EXISTENCIA DEL NOMBRE DEL SEGURO
    public boolean nombreExistente(String nombre) {
        boolean existe = false;
        for (Seguros_Object aux : obtenerTodosSeguros_C()) {
            if (aux.getNombre().equals(nombre)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    //METODO PARA COMPROBAR LOS CAMPOS DEL NOMBRE Y PRECIO 
    public boolean comprobarCamposSeguro_C(String nombre, String precio) {
        return (!nombre.isEmpty() && nombre.length() < 26) && (!precio.isEmpty());
    }

    //METODO PARA OBTENER FILA SELECCIONADA DE LA TABLA
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
