package PackageProductos;

import PackageEmpresas.Empresas_Object;
import PackageExceptions.NonexistentEntityException;
import java.awt.HeadlessException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;


public class Productos_JPAC_Modelo implements Serializable {
    
    private EntityManagerFactory emf = null;

    //CONSTRUCTOR
    public Productos_JPAC_Modelo(EntityManagerFactory emf) {
        this.emf = emf;
    }

    //CONSTRUCTOR
    public Productos_JPAC_Modelo() {
        this.emf = Persistence.createEntityManagerFactory("GESTOR-JPA-PU");
    }
    
    //GETTER
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    
    //METODO PARA GUARDAR UN PRODUCTO (MODELO)
    public void create(Productos_Object productos_Object) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresas_Object empresas_id_empresa_p = productos_Object.getEmpresas_id_empresa_p();
            if (empresas_id_empresa_p != null) {
                empresas_id_empresa_p = em.getReference(empresas_id_empresa_p.getClass(), empresas_id_empresa_p.getId_empresa());
                productos_Object.setEmpresas_id_empresa_p(empresas_id_empresa_p);
            }
            em.persist(productos_Object);
            if (empresas_id_empresa_p != null) {
                empresas_id_empresa_p.getProductos().add(productos_Object);
                empresas_id_empresa_p = em.merge(empresas_id_empresa_p);
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "PRODUCTO AGREGADO", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR EL PRODUCTO", "INFORMACION", JOptionPane.ERROR_MESSAGE);
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    //METODO PARA ACTUALIZAR UN PRODUCTO (MODELO)
    public void edit(Productos_Object productos_Object) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos_Object persistentProductos_Object = em.find(Productos_Object.class, productos_Object.getId_producto());
            Empresas_Object empresas_id_empresa_pOld = persistentProductos_Object.getEmpresas_id_empresa_p();
            Empresas_Object empresas_id_empresa_pNew = productos_Object.getEmpresas_id_empresa_p();
            if (empresas_id_empresa_pNew != null) {
                empresas_id_empresa_pNew = em.getReference(empresas_id_empresa_pNew.getClass(), empresas_id_empresa_pNew.getId_empresa());
                productos_Object.setEmpresas_id_empresa_p(empresas_id_empresa_pNew);
            }
            productos_Object = em.merge(productos_Object);
            if (empresas_id_empresa_pOld != null && !empresas_id_empresa_pOld.equals(empresas_id_empresa_pNew)) {
                empresas_id_empresa_pOld.getProductos().remove(productos_Object);
                empresas_id_empresa_pOld = em.merge(empresas_id_empresa_pOld);
            }
            if (empresas_id_empresa_pNew != null && !empresas_id_empresa_pNew.equals(empresas_id_empresa_pOld)) {
                empresas_id_empresa_pNew.getProductos().add(productos_Object);
                empresas_id_empresa_pNew = em.merge(empresas_id_empresa_pNew);
            }
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "PRODUCTO ACTUALIZADO", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = productos_Object.getId_producto();
                if (findProductos_Object(id) == null) {
                    throw new NonexistentEntityException("The productos_Object with id " + id + " no longer exists.");
                }
            }
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR EL PRODUCTO", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    //METODO PARA ELIMINAR UN PRODUCTO (MODELO)
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos_Object productos_Object;
            try {
                productos_Object = em.getReference(Productos_Object.class, id);
                productos_Object.getId_producto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productos_Object with id " + id + " no longer exists.", enfe);
            }
            Empresas_Object empresas_id_empresa_p = productos_Object.getEmpresas_id_empresa_p();
            if (empresas_id_empresa_p != null) {
                empresas_id_empresa_p.getProductos().remove(productos_Object);
                empresas_id_empresa_p = em.merge(empresas_id_empresa_p);
            }
            em.remove(productos_Object);
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "PRODUCTO ELIMINADO CORRECTAMENTE", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        } catch (NonexistentEntityException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR EL PRODUCTO", "INFORMACION", JOptionPane.ERROR_MESSAGE);
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
    //METODO PARA OBTENER LOS PRODUCTOS (MODELO)
    public List<Productos_Object> findProductos_ObjectEntities() {
        try{
            return findProductos_ObjectEntities(true, -1, -1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER LOS PRODUCTOS", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
        
    }

    
    //METODO PARA OBTENER LOS PRODUCTOS (MODELO)
    public List<Productos_Object> findProductos_ObjectEntities(int maxResults, int firstResult) {
        return findProductos_ObjectEntities(false, maxResults, firstResult);
    }

    
    //METODO PARA OBTENER LOS PRODUCTOS (MODELO)
    private List<Productos_Object> findProductos_ObjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos_Object.class));
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

    
    //METODO PARA OBTENER UN PRODUCTO (MODELO)
    public Productos_Object findProductos_Object(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos_Object.class, id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL PRODUCTO", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            return null;
        }finally {
            em.close();
        }
    }

    
    //METODO PARA OBTENER EL TOTAL DE PRODUCTOS (MODELO)
    public int getProductos_ObjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos_Object> rt = cq.from(Productos_Object.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR AL OBTENER EL TOTAL DE PRODUCTOS", "INFORMACION", JOptionPane.ERROR_MESSAGE);
            return 0;
        } finally {
            em.close();
        }
    }
    
}
