package PackageEmpresas;

import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Empresas_Controlador {

    //OBJETO DEL MODELO DE LA EMPRESA
    private final Empresas_JPAC_Modelo modeloEmpresas = new Empresas_JPAC_Modelo();

    //CONSTRUCTOR
    public Empresas_Controlador() {
    }

    //METODO PARA OBTENER TODOS LAS EMPRESAS (CONTROLADOR)
    public List<Empresas_Object> obtenerTodasEmpresas_C() {
        return modeloEmpresas.findEmpresas_ObjectEntities();
    }

    //METODO PARA GUARDAR LA EMPRESA (CONTROLADOR)
    public void guardarEmpresa_C(Empresas_Object empresa) {
        modeloEmpresas.create(empresa);
    }

    //METODO PARA ELIMINAR UNA EMPRESA (CONTROLADOR)
    public void eliminarEmpresa_C(int idEmpresa) {
        try {
            modeloEmpresas.destroy(idEmpresa);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Empresas_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //METODO PARA OBTENER UNA EMPRESA (CONTROLADOR)
    public Empresas_Object obtenerEmpresa_C(int idEmpresa) {
        return modeloEmpresas.findEmpresas_Object(idEmpresa);
    }

    //METODO PARA ACTUALIZAR LA EMPRESA (CONTROLADOR)
    public void actualizarEmpresa_C(Empresas_Object empresa) {
        try {
            modeloEmpresas.edit(empresa);
        } catch (Exception ex) {
            Logger.getLogger(Empresas_Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //METODO PARA OBTENER EL TOTAL DE EMPRESAS (CONTROLADOR)
    public int totalEmpresas(){
        return modeloEmpresas.getEmpresas_ObjectCount();
    }
    

    //METODO PARA COMPROBAR LOS CAMPOS DEL NOMBRE Y CIUDAD 
    public boolean comprobarCamposEmpresas_C(String idEmp, String nombre, String ciudad) {
        return (!nombre.isEmpty() && nombre.length() < 26) && (!ciudad.isEmpty() && ciudad.length() < 26)
                && (!idEmp.isEmpty() && idEmp.length() < 26);
    }

    //METODO PARA COMPROBAR LA EXISTENCIA DEL ID-EMPRESARIAL
    public boolean idEmpresarialExistente(String idEmp) {
        boolean existe = false;
        for (Empresas_Object aux : obtenerTodasEmpresas_C()) {
            if (aux.getId_empresarial().equals(idEmp)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    //METODO PARA OBTENER FILA SELECCIONADA DE LA TABLA
    public int obtenerFilaTabla(Empresas_Object empresa) {
        int indice = -1;
        if (empresa != null) {
            List<Empresas_Object> lista = obtenerTodasEmpresas_C();
            for (Empresas_Object aux : lista) {
                if (aux.getId_empresa() == empresa.getId_empresa()) {
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
