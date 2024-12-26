package PackageSeguros;

import PackageEmpresas.Empresas_Object;
import PackageExceptions.NonexistentEntityException;
import java.awt.HeadlessException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;


public class Seguros_JPAC_Modelo implements Serializable {
    
    private EntityManagerFactory emf = null;
    
    // CONSTRUCTOR
    public Seguros_JPAC_Modelo() {
        this.emf = Persistence.createEntityManagerFactory("GESTOR-JPA-PU");
    }
    
    // GETTER
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    
    // METODO PARA CREAR UN SEGURO (MODELO)
    public void create(Seguros_Object seguros_Object) {
        if (seguros_Object.getEmpresas() == null) {
            seguros_Object.setEmpresas(new ArrayList<Empresas_Object>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empresas_Object> attachedEmpresas = new ArrayList<Empresas_Object>();
            for (Empresas_Object empresasEmpresas_ObjectToAttach : seguros_Object.getEmpresas()) {
                empresasEmpresas_ObjectToAttach = em.getReference(empresasEmpresas_ObjectToAttach.getClass(), empresasEmpresas_ObjectToAttach.getId_empresa());
                attachedEmpresas.add(empresasEmpresas_ObjectToAttach);
            }
            seguros_Object.setEmpresas(attachedEmpresas);
            em.persist(seguros_Object);
            for (Empresas_Object empresasEmpresas_Object : seguros_Object.getEmpresas()) {
                Seguros_Object oldSeguros_id_seguroOfEmpresasEmpresas_Object = empresasEmpresas_Object.getSeguros_id_seguro();
                empresasEmpresas_Object.setSeguros_id_seguro(seguros_Object);
                empresasEmpresas_Object = em.merge(empresasEmpresas_Object);
                if (oldSeguros_id_seguroOfEmpresasEmpresas_Object != null) {
                    oldSeguros_id_seguroOfEmpresasEmpresas_Object.getEmpresas().remove(empresasEmpresas_Object);
                    oldSeguros_id_seguroOfEmpresasEmpresas_Object = em.merge(oldSeguros_id_seguroOfEmpresasEmpresas_Object);
                }
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "SEGURO AGREGADO", "SEGUROS-JPAC", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR EL SEGURO", "SEGUROS-JPAC", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // METODO PARA EDITAR UN SEGURO (MODELO)
    public void edit(Seguros_Object seguros_Object) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguros_Object persistentSeguros_Object = em.find(Seguros_Object.class, seguros_Object.getId_seguro());
            List<Empresas_Object> empresasOld = persistentSeguros_Object.getEmpresas();
            List<Empresas_Object> empresasNew = seguros_Object.getEmpresas();
            List<Empresas_Object> attachedEmpresasNew = new ArrayList<Empresas_Object>();
            for (Empresas_Object empresasNewEmpresas_ObjectToAttach : empresasNew) {
                empresasNewEmpresas_ObjectToAttach = em.getReference(empresasNewEmpresas_ObjectToAttach.getClass(), empresasNewEmpresas_ObjectToAttach.getId_empresa());
                attachedEmpresasNew.add(empresasNewEmpresas_ObjectToAttach);
            }
            empresasNew = attachedEmpresasNew;
            seguros_Object.setEmpresas(empresasNew);
            seguros_Object = em.merge(seguros_Object);
            for (Empresas_Object empresasOldEmpresas_Object : empresasOld) {
                if (!empresasNew.contains(empresasOldEmpresas_Object)) {
                    empresasOldEmpresas_Object.setSeguros_id_seguro(null);
                    empresasOldEmpresas_Object = em.merge(empresasOldEmpresas_Object);
                }
            }
            for (Empresas_Object empresasNewEmpresas_Object : empresasNew) {
                if (!empresasOld.contains(empresasNewEmpresas_Object)) {
                    Seguros_Object oldSeguros_id_seguroOfEmpresasNewEmpresas_Object = empresasNewEmpresas_Object.getSeguros_id_seguro();
                    empresasNewEmpresas_Object.setSeguros_id_seguro(seguros_Object);
                    empresasNewEmpresas_Object = em.merge(empresasNewEmpresas_Object);
                    if (oldSeguros_id_seguroOfEmpresasNewEmpresas_Object != null && !oldSeguros_id_seguroOfEmpresasNewEmpresas_Object.equals(seguros_Object)) {
                        oldSeguros_id_seguroOfEmpresasNewEmpresas_Object.getEmpresas().remove(empresasNewEmpresas_Object);
                        oldSeguros_id_seguroOfEmpresasNewEmpresas_Object = em.merge(oldSeguros_id_seguroOfEmpresasNewEmpresas_Object);
                    }
                }
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "SEGURO ACTUALIZADO", "SEGUROS-JPAC", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = seguros_Object.getId_seguro();
                if (findSeguros_Object(id) == null) {
                    throw new NonexistentEntityException("The seguros_Object with id " + id + " no longer exists.");
                }
            }
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR EL SEGURO", "SEGUROS-JPAC", JOptionPane.ERROR_MESSAGE);
            throw ex;
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    // METODO PARA ELIMINAR UN SEGURO (MODELO)
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguros_Object seguros_Object;
            try {
                seguros_Object = em.getReference(Seguros_Object.class, id);
                seguros_Object.getId_seguro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seguros_Object with id " + id + " no longer exists.", enfe);
            }
            List<Empresas_Object> empresas = seguros_Object.getEmpresas();
            for (Empresas_Object empresasEmpresas_Object : empresas) {
                empresasEmpresas_Object.setSeguros_id_seguro(null);
                empresasEmpresas_Object = em.merge(empresasEmpresas_Object);
            }
            em.remove(seguros_Object);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "SEGURO ELIMINADO CORRECTAMENTE", "SEGUROS-JPAC", JOptionPane.INFORMATION_MESSAGE);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR EL SEGURO", "SEGUROS-JPAC", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    // METODO PARA OBTENER LOS SEGUROS (MODELO)
    public List<Seguros_Object> findSeguros_ObjectEntities() {
        try{
            return findSeguros_ObjectEntities(true, -1, -1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER LOS SEGUROS", "SEGUROS-JPAC", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    // METODO PARA OBTENER LOS SEGUROS (MODELO)
    public List<Seguros_Object> findSeguros_ObjectEntities(int maxResults, int firstResult) {
        return findSeguros_ObjectEntities(false, maxResults, firstResult);
    }

    // METODO PARA OBTENER LOS SEGUROS (MODELO)
    private List<Seguros_Object> findSeguros_ObjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Seguros_Object.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    
    // METODO PARA OBTENER UN SEGURO (MODELO)
    public Seguros_Object findSeguros_Object(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Seguros_Object.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL SEGURO", "SEGUROS-JPAC", JOptionPane.ERROR_MESSAGE);
            return null;
        } finally {
            em.close();
        }
    }

    
    // METODO PARA OBTENER EL TOTAL DE SEGUROS (MODELO)
    public int getSeguros_ObjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Seguros_Object> rt = cq.from(Seguros_Object.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL TOTAL DE SEGUROS", "SEGUROS-JPAC", JOptionPane.ERROR_MESSAGE);
            return 0;
        } finally {
            em.close();
        }
    }
    
}
