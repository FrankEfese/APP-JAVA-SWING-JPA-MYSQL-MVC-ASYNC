package PackageEmpresas;

import PackageExceptions.NonexistentEntityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Empresas_Controlador {

    // MODELO-EMPRESA
    private final Empresas_JPAC_Modelo modeloEmpresas = new Empresas_JPAC_Modelo();

    // CONSTRUCTOR
    public Empresas_Controlador() {
    }

    // METODO PARA OBTENER TODOS LAS EMPRESAS (CONTROLADOR)
    public CompletableFuture<List<Empresas_Object>> obtenerTodasEmpresas_C() {
        return CompletableFuture.supplyAsync(() -> this.modeloEmpresas.findEmpresas_ObjectEntities());
    }

    // METODO PARA GUARDAR LA EMPRESA (CONTROLADOR)
    public void guardarEmpresa_C(Empresas_Object empresa) {
        CompletableFuture.runAsync(() -> this.modeloEmpresas.create(empresa));
    }

    // METODO PARA ELIMINAR UNA EMPRESA (CONTROLADOR)
    public void eliminarEmpresa_C(int idEmpresa) {
        CompletableFuture.runAsync(() -> {
            try {
                this.modeloEmpresas.destroy(idEmpresa);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(Empresas_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // METODO PARA OBTENER UNA EMPRESA (CONTROLADOR)
    public CompletableFuture<Empresas_Object> obtenerEmpresa_C(int idEmpresa) {
        return CompletableFuture.supplyAsync(() -> this.modeloEmpresas.findEmpresas_Object(idEmpresa));
    }

    // METODO PARA ACTUALIZAR LA EMPRESA (CONTROLADOR)
    public void actualizarEmpresa_C(Empresas_Object empresa) {
        CompletableFuture.runAsync(() -> {
            try {
                this.modeloEmpresas.edit(empresa);
            } catch (Exception ex) {
                Logger.getLogger(Empresas_Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }   
    
    // METODO PARA OBTENER EL TOTAL DE EMPRESAS (CONTROLADOR)
    public CompletableFuture<Integer> totalEmpresas(){
        return CompletableFuture.supplyAsync(() -> this.modeloEmpresas.getEmpresas_ObjectCount());
    }

    // METODO PARA COMPROBAR LA EXISTENCIA DEL ID-EMPRESARIAL
    public CompletableFuture<Boolean> idEmpresarialExistente(String idEmp) {
        
            return obtenerTodasEmpresas_C().thenApply(empresas -> {           
                for(Empresas_Object aux : empresas){              
                    if (aux.getId_empresarial().equals(idEmp)) {
                        return true;
                    }                   
                }               
                return false;                              
            }).exceptionally(ex -> {
                return false;
            });       
    }


    // METODO PARA OBTENER FILA SELECCIONADA DE LA TABLA
    public CompletableFuture<Integer> obtenerFilaTabla(Empresas_Object empresa) {
        if (empresa != null) {
            
            return obtenerTodasEmpresas_C().thenApply(empresas -> {           
                for (Empresas_Object aux : empresas) {
                    if (aux.getId_empresa() == empresa.getId_empresa()) {
                        return empresas.indexOf(aux);
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
